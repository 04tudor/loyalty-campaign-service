package md.maib.retail.application.find_campaign_by_metainfo;

import md.maib.retail.application.CampaignAllInfo;
import md.maib.retail.model.ports.Campaigns;

import java.util.List;


public interface FindCampaignByMetaInfoUseCase {
    static FindCampaignByMetaInfoService defaultService(Campaigns campaigns) {
        return new FindCampaignByMetaInfoService(campaigns);
    }

    List<CampaignAllInfo> findByMetaInfo(String key, String value);


}
