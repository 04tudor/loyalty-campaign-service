package md.maib.retail.model.junit;

import md.maib.retail.Model.Campaign.CampaignId;

import java.util.UUID;

public class CampaignBuilder {

    CampaignId campaignId=CampaignId.newIdentity();


    public CampaignId withCampaignId(UUID campaignId) {
        this.campaignId = CampaignId.newIdentity();
        return CampaignId.valueOf(campaignId);
    }


}
