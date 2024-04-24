package md.maib.retail.usecase;

import md.maib.retail.CampaignInfo;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.ports.Campaigns;
import md.maib.retail.services.FindCampaignService;

import java.util.Optional;

public interface FindCampaignUseCase {
    static FindCampaignUseCase defaultService(Campaigns campaigns) {
        return new FindCampaignService(campaigns);
    }

    Optional<CampaignInfo> findByMetaInfo(String key,String value);

    Optional<CampaignInfo> findById(CampaignId campaignId);
}
