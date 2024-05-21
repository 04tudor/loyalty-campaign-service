package md.maib.retail.application.register_newcampaign;

import io.vavr.control.Either;
import md.maib.retail.application.CampaignEntity;
import md.maib.retail.model.campaign.*;
import md.maib.retail.model.conditions.Condition;
import md.maib.retail.model.conditions.Rule;
import md.maib.retail.model.conditions.RuleId;
import md.maib.retail.model.effects.Effect;
import md.maib.retail.model.ports.Campaigns;
import org.threeten.extra.Interval;

import java.time.ZoneOffset;
import java.util.Objects;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public class RegisterCampaignService implements RegistrationCampaignUseCase {
   private final Campaigns campaigns;

    public RegisterCampaignService(Campaigns campaigns) {
        this.campaigns = Objects.requireNonNull(campaigns,"Campaigns must not be null");

    }


    @Override
    public Either<UseCaseProblemConflict, CampaignId> registerCampaign(RegisterCampaign command) {
        var id = CampaignId.newIdentity();
        var metaInfo = new CampaignMetaInfo(command.metaInfo().properties());
        var startInclusive = command.startInclusive().atStartOfDay(ZoneOffset.UTC).toInstant();
        var endExclusive = command.endExclusive().atStartOfDay(ZoneOffset.UTC).toInstant();
        var interval = Interval.of(startInclusive, endExclusive);
        var state = command.state();

        var fields = command.loyaltyEventType().getFields().stream()
                .map(loyaltyField -> new LoyaltyEventField(
                        loyaltyField.getId(),
                        loyaltyField.getName(),
                        loyaltyField.getFieldType()
                ))
                .toList();

        var loyaltyEventType = new LoyaltyEventType(
                command.loyaltyEventType().getId(),
                command.loyaltyEventType().getName(),
                fields
        );
        var rules = command.rules().stream()
                .map(rule -> {
                    var conditions = rule.getConditions().stream()
                            .map(condition -> new Condition(
                                    condition.getField(),
                                    condition.getOperator(),
                                    condition.getValue()
                            ))
                            .toList();
                    var effects = rule.getEffects().stream()
                            .map(effect -> new Effect(
                                    effect.effectType(),
                                    effect.value()
                            ))
                            .toList();
                    return new Rule(RuleId.newIdentity(), conditions, effects);
                })
                .toList();

        Campaign campaign=CampaignEntity.valueOf(new CampaignEntity(id, metaInfo, interval,state,loyaltyEventType, rules));

        if (campaigns.add(campaign)) {
           
            return right(id);
        }
        return left(new UseCaseProblemConflict("CampaignWithSameIdAlreadyExists"));
    }

}
