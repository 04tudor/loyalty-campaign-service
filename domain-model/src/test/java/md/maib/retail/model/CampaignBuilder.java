package md.maib.retail.model;

import md.maib.retail.model.campaign.CampaignId;

import java.util.UUID;

public class CampaignBuilder {

    private CampaignId campaignId = CampaignId.newIdentity();

    public CampaignBuilder(UUID campaignId) {
        this.campaignId = CampaignId.valueOf(campaignId);
    }

    public CampaignId build() {
        return new CampaignId(campaignId.campaignId());
    }
}
