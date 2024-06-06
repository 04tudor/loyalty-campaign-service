package md.maib.retail.application.save_campaign;

import md.maib.retail.model.campaign.Campaign;
import md.maib.retail.model.ports.Campaigns;

public interface SaveCampaignUseCase {
    static SaveCampaignUseCase defaultService(Campaigns campaigns) {
        return new SaveCampaignService(campaigns);
    }

    boolean save(Campaign campaign);
}
