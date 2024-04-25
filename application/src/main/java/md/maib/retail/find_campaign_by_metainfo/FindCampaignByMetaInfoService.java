package md.maib.retail.find_campaign_by_metainfo;

import md.maib.retail.CampaignAllInfo;
import md.maib.retail.model.ports.Campaigns;

import java.util.Objects;
import java.util.Optional;

 class FindCampaignByMetaInfoService implements FindCampaignByMetaInfoUseCase{
    private final Campaigns campaigns;

    public FindCampaignByMetaInfoService(Campaigns campaigns) {
        this.campaigns = Objects.requireNonNull(campaigns,"Campaigns must not be null");
    }

    @Override
    public Optional<CampaignAllInfo> findByMetaInfo(String key, String value) {
        return campaigns.findByMetaInfo(key, value)
                .map(campaign -> new CampaignAllInfo(
                        campaign.getId(),
                        campaign.getMetaInfo(),
                        campaign.getActivityInterval(),
                        campaign.getState(),
                        campaign.getLoyaltyEventType(),
                        campaign.getRules()
                ));    }

}
