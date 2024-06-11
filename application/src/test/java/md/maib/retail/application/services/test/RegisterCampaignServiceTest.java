package md.maib.retail.application.services.test;

import am.ik.yavi.core.ConstraintViolations;
import io.vavr.control.Either;
import md.maib.retail.application.find_effect_type_by_id.EffectTypeRecord;
import md.maib.retail.application.find_event_type_by_id.EventTypeRecord;
import md.maib.retail.application.register_newcampaign.RegisterCampaign;
import md.maib.retail.application.register_newcampaign.RegisterCampaignService;
import md.maib.retail.application.register_newcampaign.RegistrationCampaignUseCase;
import md.maib.retail.application.register_newcampaign.UseCaseProblemConflict;
import md.maib.retail.model.campaign.*;
import md.maib.retail.model.conditions.Condition;
import md.maib.retail.model.conditions.Operator;
import md.maib.retail.model.conditions.Rule;
import md.maib.retail.model.conditions.RuleId;
import md.maib.retail.model.effects.Effect;
import md.maib.retail.model.effects.LoyaltyEffectType;
import md.maib.retail.model.ports.Campaigns;
import md.maib.retail.model.ports.LoyaltyEffectTypes;
import md.maib.retail.model.ports.LoyaltyEventTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.fromString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterCampaignServiceTest {

    @Mock
    private Campaigns campaigns;

    @Mock
    private LoyaltyEventTypes loyaltyEventTypes;

    @Mock
    private LoyaltyEffectTypes loyaltyEffectTypes;

    private RegistrationCampaignUseCase target;

    @BeforeEach
    void setup() {
        target = new RegisterCampaignService(campaigns, loyaltyEventTypes, loyaltyEffectTypes);
    }

    @Test
    void registerNewCampaign_Success() {
        when(campaigns.add(any(Campaign.class))).thenReturn(true);
        when(loyaltyEventTypes.findById(anyString())).thenReturn(Optional.of(new LoyaltyEventType(UUID.randomUUID(), "EventType", List.of(new LoyaltyEventField(UUID.randomUUID(), "Field", FieldType.STRING)))));
        when(loyaltyEffectTypes.findById(anyString())).thenReturn(Optional.of(new LoyaltyEffectType(UUID.randomUUID(), "EffectType", fromString("cd9c30db-88d8-4fa0-9299-7b9bf63d1b15"))));

        Optional<LoyaltyEventType> loyaltyEventType = loyaltyEventTypes.findById("6fb2fcfd-b836-4102-8c29-f0c38c97965e");

        assertThat(loyaltyEventType).isPresent();

        RegisterCampaign command = new RegisterCampaign(
                new CampaignMetaInfo(Map.of("key", "value")),
                LocalDate.of(2024, 5, 1),
                LocalDate.of(2024, 6, 1),
                CampaignState.ACTIVE,
                new EventTypeRecord("57b2516a-fd15-4057-a04a-c725a0a80e1e"),
                List.of(
                        new Rule(
                                RuleId.newIdentity(),
                                List.of(new Condition(FieldType.DECIMAL, Operator.EQUALS, "5")),
                                List.of(new Effect(
                                        new LoyaltyEffectType(UUID.fromString("4ec0b56f-ff4c-4e7e-b257-68ce9f133a45"), "TestEffect", fromString("cd9c30db-88d8-4fa0-9299-7b9bf63d1b15")),
                                        "10")
                                )
                        )
                ),
                new EffectTypeRecord("1414d3f4-7978-4f4b-a532-3ece801e253c")
        );

        Either<UseCaseProblemConflict, CampaignId> result = target.registerCampaign(command);

        assertThat(result.isRight()).isTrue();
        assertThat(result.get()).isInstanceOf(CampaignId.class);
    }

    @Test
    void registerNewCampaign_Fail_MissingEventTypeOrEffectType() {
        when(loyaltyEventTypes.findById(anyString())).thenReturn(Optional.empty());
        when(loyaltyEffectTypes.findById(anyString())).thenReturn(Optional.empty());

        Optional<LoyaltyEventType> loyaltyEventType = loyaltyEventTypes.findById("6fb2fcfd-b836-4102-8c29-f0c38c97965e");
        assertThat(loyaltyEventType).isNotPresent();

        Optional<LoyaltyEffectType> loyaltyEffectType = loyaltyEffectTypes.findById("1414d3f4-7978-4f4b-a532-3ece801e253c");
        assertThat(loyaltyEffectType).isNotPresent();

        RegisterCampaign command = new RegisterCampaign(
                new CampaignMetaInfo(Map.of("key", "value")),
                LocalDate.of(2024, 5, 1),
                LocalDate.of(2024, 6, 1),
                CampaignState.DRAFT,
                new EventTypeRecord("57b2516a-fd15-4057-a04a-c725a0a80e1e"),
                List.of(
                        new Rule(
                                RuleId.newIdentity(),
                                List.of(new Condition(FieldType.DECIMAL, Operator.EQUALS, "5")),
                                List.of(new Effect(
                                        new LoyaltyEffectType(UUID.fromString("1414d3f4-7978-4f4b-a532-3ece801e253c"), "TestEffect", fromString("cd9c30db-88d8-4fa0-9299-7b9bf63d1b15")),
                                        "10")
                                )
                        )
                ),
                new EffectTypeRecord("1414d3f4-7978-4f4b-a532-3ece801e253c")
        );

        Either<UseCaseProblemConflict, CampaignId> result = target.registerCampaign(command);

        assertThat(result.isLeft()).isTrue();
        assertThat(result.getLeft()).isInstanceOf(UseCaseProblemConflict.class);
        assertThat(result.getLeft().getMessage()).isEqualTo("Failed to retrieve LoyaltyEventType or LoyaltyEffectType");
    }
    @Test
    void registerNewCampaign_Fail_AlreadyExists() {
        when(campaigns.add(any(Campaign.class))).thenReturn(false);
        when(loyaltyEventTypes.findById(anyString())).thenReturn(Optional.of(
                new LoyaltyEventType(UUID.randomUUID(), "EventType", List.of(
                        new LoyaltyEventField(UUID.randomUUID(), "Field", FieldType.STRING)
                ))
        ));
        when(loyaltyEffectTypes.findById(anyString())).thenReturn(Optional.of(
                new LoyaltyEffectType(UUID.randomUUID(), "EffectType", fromString("cd9c30db-88d8-4fa0-9299-7b9bf63d1b15"))
        ));

        RegisterCampaign command = new RegisterCampaign(
                new CampaignMetaInfo(Map.of("key", "value")),
                LocalDate.of(2024, 5, 1),
                LocalDate.of(2024, 6, 1),
                CampaignState.DRAFT,
                new EventTypeRecord("57b2516a-fd15-4057-a04a-c725a0a80e1e"),
                List.of(
                        new Rule(
                                RuleId.newIdentity(),
                                List.of(new Condition(FieldType.DECIMAL, Operator.EQUALS, "5")),
                                List.of(new Effect(
                                        new LoyaltyEffectType(UUID.fromString("4ec0b56f-ff4c-4e7e-b257-68ce9f133a45"), "TestEffect", fromString("cd9c30db-88d8-4fa0-9299-7b9bf63d1b15")
                                        ),
                                        "10")
                                )
                        )
                ),
                new EffectTypeRecord("1414d3f4-7978-4f4b-a532-3ece801e253c")
        );

        Either<UseCaseProblemConflict, CampaignId> result = target.registerCampaign(command);

        assertThat(result.isLeft()).isTrue();
        assertThat(result.getLeft()).isInstanceOf(UseCaseProblemConflict.class);
        assertThat(result.getLeft().getMessage()).isEqualTo("CampaignWithSameIdAlreadyExists");
    }
    @Test
    void registerNewCampaign_Fail_InvalidCommand() {
        RegisterCampaign command = new RegisterCampaign(
                new CampaignMetaInfo(Map.of("key", "value")),
                LocalDate.of(2024, 5, 1),
                LocalDate.of(2024, 6, 1),
                CampaignState.ACTIVE,  //invalid state for Campaign State
                new EventTypeRecord("57b2516a-fd15-4057-a04a-c725a0a80e1e"),
                List.of(
                        new Rule(
                                RuleId.newIdentity(),
                                List.of(new Condition(FieldType.DECIMAL, Operator.EQUALS, "5")),
                                List.of(new Effect(
                                        new LoyaltyEffectType(UUID.fromString("1414d3f4-7978-4f4b-a532-3ece801e253c"), "TestEffect", fromString("cd9c30db-88d8-4fa0-9299-7b9bf63d1b15")),
                                        "10")
                                )
                        )
                ),
                new EffectTypeRecord("1414d3f4-7978-4f4b-a532-3ece801e253c")
        );
        Either<ConstraintViolations, RegisterCampaign> result = RegisterCampaign.create(
                command.metaInfo(),
                command.startInclusive(),
                command.endExclusive(),
                command.state(),
                command.loyaltyEventType(),
                command.rules(),
                command.loyaltyEffectType()
        );

        assertThat(result.isLeft()).isTrue();
        assertThat(result.getLeft().violations()).isNotEmpty();
    }



}
