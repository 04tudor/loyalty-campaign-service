package md.maib.retail.infrastructure.rest.test.integration;

import md.maib.retail.application.campaigns_list_by_date.CampaignsListByDateUseCase;
import md.maib.retail.application.delete_campaign.DeleteCampaignUseCase;
import md.maib.retail.application.find_campaign_by_id.FindCampaignByIdUseCase;
import md.maib.retail.application.find_campaign_by_metainfo.FindCampaignByMetaInfoUseCase;
import md.maib.retail.application.register_newcampaign.RegistrationCampaignUseCase;
import org.mockito.Mock;
import org.springframework.context.annotation.Configuration;

@Configuration

public class TestRestConfig {
    @Mock
     FindCampaignByIdUseCase findCampaignByIdUseCase;

    @Mock
     FindCampaignByMetaInfoUseCase findCampaignByMetaInfoUseCase;

    @Mock
    RegistrationCampaignUseCase registrationCampaignUseCase;

    @Mock
    DeleteCampaignUseCase deleteCampaignUseCase;

    @Mock
    CampaignsListByDateUseCase campaignsListByDateUseCase;
}
