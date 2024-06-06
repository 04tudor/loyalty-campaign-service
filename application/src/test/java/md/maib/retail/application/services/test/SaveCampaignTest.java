package md.maib.retail.application.services.test;


import md.maib.retail.application.save_campaign.SaveCampaignUseCase;
import md.maib.retail.junit.UnitTest;
import md.maib.retail.model.campaign.Campaign;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.campaign.CampaignState;
import md.maib.retail.model.ports.Campaigns;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@UnitTest
@ExtendWith(MockitoExtension.class)
class SaveCampaignTest {
    @Mock
    Campaigns campaigns;

    SaveCampaignUseCase target;

    @BeforeEach
    void setup() {
        target = SaveCampaignUseCase.defaultService(campaigns);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(campaigns);
    }

    @Test
    void saveActiveCampaign() {
        CampaignId campaignId = CampaignId.valueOf(CampaignId.newIdentity().campaignId());
        Campaign campaign = new Campaign(campaignId, null, null, CampaignState.ACTIVE, null, null);
        when(campaigns.save(campaign)).thenReturn(true);

        boolean result = target.save(campaign);

        assertTrue(result);
        verify(campaigns).save(campaign);
    }

    @Test
    void saveInactiveCampaign() {
        CampaignId campaignId = CampaignId.valueOf(CampaignId.newIdentity().campaignId());
        Campaign campaign = new Campaign(campaignId, null, null, CampaignState.DRAFT, null, null);

        boolean result = target.save(campaign);

        assertFalse(result);
        verify(campaigns, never()).save(campaign);
    }
}
