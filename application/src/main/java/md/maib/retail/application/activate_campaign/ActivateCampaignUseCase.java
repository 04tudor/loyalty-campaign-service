package md.maib.retail.application.activate_campaign;

import md.maib.retail.application.find_campaign_by_id.FindCampaignByIdUseCase;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.ports.Campaigns;

public interface ActivateCampaignUseCase {
    static ActivateCampaignUseCase defaultService(Campaigns campaigns) {
        return new ActivateCampaignService(
                campaigns,
                FindCampaignByIdUseCase.defaultService(campaigns)
        );
    }

    boolean activate(CampaignId campaignId);
}
