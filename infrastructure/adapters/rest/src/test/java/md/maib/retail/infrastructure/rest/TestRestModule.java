package md.maib.retail.infrastructure.rest;

import md.maib.retail.application.activate_campaign.ActivateCampaignUseCase;
import md.maib.retail.application.campaigns_list_by_date.CampaignsListByDateUseCase;
import md.maib.retail.application.delete_campaign.DeleteCampaignUseCase;
import md.maib.retail.application.find_campaign_by_id.FindCampaignByIdUseCase;
import md.maib.retail.application.find_campaign_by_metainfo.FindCampaignByMetaInfoUseCase;
import md.maib.retail.application.find_effect_type_by_id.FindByIdLoyaltyEffectTypeUseCase;
import md.maib.retail.application.find_event_type_by_id.FindByIdLoyaltyEventTypeUseCase;
import md.maib.retail.application.register_newcampaign.RegistrationCampaignUseCase;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("md.maib.retail")
public class TestRestModule {

    @MockBean
    FindCampaignByIdUseCase findCampaignByIdUseCase;

    @MockBean
    FindCampaignByMetaInfoUseCase findCampaignByMetaInfoUseCase;

    @MockBean
    RegistrationCampaignUseCase registrationCampaignUseCase;

    @MockBean
    DeleteCampaignUseCase deleteCampaignUseCase;

    @MockBean
    CampaignsListByDateUseCase campaignsListByDateUseCase;

    @MockBean
    FindByIdLoyaltyEventTypeUseCase findByIdLoyaltyEventTypeUseCase;

    @MockBean
    FindByIdLoyaltyEffectTypeUseCase findByIdLoyaltyEffectTypeUseCase;

    @MockBean
    ActivateCampaignUseCase activateCampaignUseCase;
}
