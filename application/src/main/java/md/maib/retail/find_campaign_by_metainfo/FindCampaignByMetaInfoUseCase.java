package md.maib.retail.find_campaign_by_metainfo;

import md.maib.retail.CampaignAllInfo;
import md.maib.retail.model.ports.Campaigns;

import java.util.Optional;


public interface FindCampaignByMetaInfoUseCase {
    static FindCampaignByMetaInfoService defaultService(Campaigns campaigns) {
        return new FindCampaignByMetaInfoService(campaigns);
    }

    Optional<CampaignAllInfo> findByMetaInfo(String key, String value);


}
