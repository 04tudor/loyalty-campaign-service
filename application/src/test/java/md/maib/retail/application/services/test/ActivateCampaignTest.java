package md.maib.retail.application.services.test;

import md.maib.retail.application.activate_campaign.ActivateCampaignUseCase;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@UnitTest
@ExtendWith(MockitoExtension.class)
class ActivateCampaignTest {
    @Mock
    Campaigns campaigns;

    ActivateCampaignUseCase target;

    @BeforeEach
    void setup() {
        target = ActivateCampaignUseCase.defaultService(campaigns);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(campaigns);
    }

    @Test
    void activateExistingCampaign() {
        CampaignId campaignId = CampaignId.valueOf(CampaignId.newIdentity().campaignId());
        Campaign campaign = new Campaign(campaignId, null, null, CampaignState.DRAFT, null, null);
        when(campaigns.getById(campaignId)).thenReturn(Optional.of(campaign));
        when(campaigns.save(any(Campaign.class))).thenReturn(true);

        boolean result = target.activate(campaignId);

        assertTrue(result);
        verify(campaigns).getById(campaignId);
        verify(campaigns).save(any(Campaign.class));
    }

    @Test
    void activateNonExistentCampaign() {
        CampaignId campaignId = CampaignId.valueOf(CampaignId.newIdentity().campaignId());
        when(campaigns.getById(campaignId)).thenReturn(Optional.empty());

        boolean result = target.activate(campaignId);

        assertFalse(result);
        verify(campaigns).getById(campaignId);
    }

    @Test
    void activateAlreadyActiveCampaign() {
        CampaignId campaignId = CampaignId.valueOf(CampaignId.newIdentity().campaignId());
        Campaign campaign = new Campaign(campaignId, null, null, CampaignState.ACTIVE, null, null);
        when(campaigns.getById(campaignId)).thenReturn(Optional.of(campaign));

        boolean result = target.activate(campaignId);

        assertFalse(result);
        verify(campaigns).getById(campaignId);
    }
}
