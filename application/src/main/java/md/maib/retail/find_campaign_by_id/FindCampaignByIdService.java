package md.maib.retail.find_campaign_by_id;

import md.maib.retail.CampaignAllInfo;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.ports.Campaigns;

import java.util.Objects;
import java.util.Optional;

 public class FindCampaignByIdService implements FindCampaignByIdUseCase {
    private final Campaigns campaigns;

    public FindCampaignByIdService(Campaigns campaigns) {
        this.campaigns = Objects.requireNonNull(campaigns,"Campaigns must not be null");
    }

    @Override
    public Optional<CampaignAllInfo> findById(CampaignId campaignId) {
        return campaigns.findById(CampaignId.valueOf(campaignId.campaignId()))
                .map(campaign -> new CampaignAllInfo(
                        campaign.getId(),
                        campaign.getMetaInfo(),
                        campaign.getActivityInterval(),
                        campaign.getState(),
                        campaign.getLoyaltyEventType(),
                        campaign.getRules()
                ));
    }
}
