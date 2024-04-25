package md.maib.retail.find_campaign_by_id;

import md.maib.retail.CampaignAllInfo;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.ports.Campaigns;

import java.util.Optional;

public interface FindCampaignByIdUseCase {
    static FindCampaignByIdUseCase defaultService(Campaigns campaigns) {
        return new FindCampaignByIdService(campaigns);
    }

    Optional<CampaignAllInfo> findById(CampaignId campaignId);


}
