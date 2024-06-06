package md.maib.retail.application.save_campaign;

import md.maib.retail.model.campaign.Campaign;
import md.maib.retail.model.campaign.CampaignState;
import md.maib.retail.model.ports.Campaigns;

import java.util.Objects;

public class SaveCampaignService implements SaveCampaignUseCase {
    private final Campaigns campaigns;

    public SaveCampaignService(Campaigns campaigns) {
        this.campaigns = Objects.requireNonNull(campaigns, "Campaigns must not be null");
    }

    @Override
    public boolean save(Campaign campaign) {
        if (campaign.getState() == CampaignState.ACTIVE) {
            return campaigns.save(campaign);
        }
        return false;
    }
}
