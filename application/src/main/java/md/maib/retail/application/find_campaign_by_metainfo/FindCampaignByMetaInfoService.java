package md.maib.retail.application.find_campaign_by_metainfo;

import md.maib.retail.application.CampaignAllInfo;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.ports.Campaigns;

import java.util.List;
import java.util.Objects;

public class FindCampaignByMetaInfoService implements FindCampaignByMetaInfoUseCase{
    private final Campaigns campaigns;

    public FindCampaignByMetaInfoService(Campaigns campaigns) {
        this.campaigns = Objects.requireNonNull(campaigns,"Campaigns must not be null");
    }

    @Override
    public List<CampaignAllInfo> findByMetaInfo(String key, String value) {
        return campaigns.findByMetaInfo(key, value).stream()
                .map(CampaignAllInfo::valueOf).toList();
    }    }


