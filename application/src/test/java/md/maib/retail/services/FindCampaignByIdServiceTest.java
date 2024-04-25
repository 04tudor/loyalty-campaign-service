package md.maib.retail.services;

import md.maib.retail.CampaignAllInfo;
import md.maib.retail.find_campaign_by_id.FindCampaignByIdService;
import md.maib.retail.find_campaign_by_id.FindCampaignByIdUseCase;
import md.maib.retail.junit.UnitTest;
import md.maib.retail.model.campaign.Campaign;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.campaign.CampaignState;
import md.maib.retail.model.ports.Campaigns;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@UnitTest
public class FindCampaignByIdServiceTest {
    Campaigns campaigns;
    @Test
    void findById_WhenCampaignFound_ReturnsCampaignAllInfo() {
        CampaignId campaignId = new CampaignId(CampaignId.newIdentity().campaignId());
        Campaign campaign = new Campaign(campaignId, null, null, CampaignState.DRAFT, null, null);
        FindCampaignByIdService findCampaignByIdService = new FindCampaignByIdService(campaigns);

        CampaignAllInfo result = findCampaignByIdService.findById(campaignId).orElse(null);

        assertThat(result)
                .as("Campaign should not be null when found")
                .isNotNull();
        assertThat(result.getId())
                .as("Campaign ID should match the searched ID")
                .isEqualTo(campaignId);
    }

    @Test
    void findById_WhenCampaignNotFound_ReturnsEmptyOptional() {
        CampaignId campaignId = new CampaignId(CampaignId.newIdentity().campaignId());
        FindCampaignByIdService findCampaignByIdService = new FindCampaignByIdService(campaigns);

        CampaignAllInfo result = findCampaignByIdService.findById(campaignId).orElse(null);

        assertThat(result)
                .as("Result should be null when campaign not found")
                .isNull();
    }
}
