package md.maib.retail.application.services;

import md.maib.retail.application.register_newcampaign.RegisterCampaignValidate;
import md.maib.retail.application.register_newcampaign.RegistrationCampaignUseCase;
import md.maib.retail.model.campaign.*;
import md.maib.retail.model.ports.Campaigns;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterCampaignServiceTest {
    @Mock
    Campaigns campaigns;

    RegistrationCampaignUseCase registrationCampaignUseCase;

    @BeforeEach
    void setup() {
        registrationCampaignUseCase = RegistrationCampaignUseCase.defaultService(campaigns);
    }

    @Test
    void registerNewCampaign_Success() {
        when(campaigns.add(Mockito.any(Campaign.class))).thenReturn(true);

        List<LoyaltyEventField> loyaltyEventField = List.of(new LoyaltyEventField(UUID.randomUUID(), "Field", FieldType.STRING));
        LoyaltyEventType loyaltyEventType = new LoyaltyEventType(UUID.randomUUID(), "Event", loyaltyEventField);

        RegisterCampaignValidate command = new RegisterCampaignValidate(
                new CampaignMetaInfo(Collections.emptyMap()),
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                CampaignState.DRAFT,
                loyaltyEventType,
                Collections.emptyList()
        );

        Optional<CampaignId> result = registrationCampaignUseCase.registerCampaign(command).toJavaOptional();
        CampaignId checkCampaignId= CampaignId.valueOf(result.get().campaignId());
        assertThat(result).isPresent().contains(checkCampaignId);
    }

    @Test
    void registerNewCampaign_Fail() {
        when(campaigns.add(Mockito.any(Campaign.class))).thenReturn(false);

        RegisterCampaignValidate command = new RegisterCampaignValidate(
                new CampaignMetaInfo(Collections.emptyMap()),
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                CampaignState.DRAFT,
                null,
                Collections.emptyList()
        );

        Optional<CampaignId> result = registrationCampaignUseCase.registerCampaign(command).toJavaOptional();

        assertThat(result).isEmpty();
    }
}
