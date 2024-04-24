package md.maib.retail.services;

import md.maib.retail.CampaignInfo;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.ports.Campaigns;
import md.maib.retail.usecase.FindCampaignUseCase;

import java.util.Objects;
import java.util.Optional;

public class FindCampaignService implements FindCampaignUseCase {
    private final Campaigns campaigns;

    public FindCampaignService(Campaigns campaigns) {
        this.campaigns = Objects.requireNonNull(campaigns,"Campaigns must not be null");
    }

    @Override
    public Optional<CampaignInfo> findByMetaInfo(String key,String value) {
        return campaigns.findByMetaInfo(key, value)
                .map(campaign -> new CampaignInfo(
                        campaign.getId(),
                        campaign.getMetaInfo(),
                        campaign.getActivityInterval(),
                        campaign.getState(),
                        campaign.getLoyaltyEventType(),
                        campaign.getRules()
                ));    }

    @Override
    public Optional<CampaignInfo> findById(CampaignId campaignId) {
        return campaigns.findById(CampaignId.valueOf(campaignId.campaignId()))
                .map(campaign -> new CampaignInfo(
                        campaign.getId(),
                        campaign.getMetaInfo(),
                        campaign.getActivityInterval(),
                        campaign.getState(),
                        campaign.getLoyaltyEventType(),
                        campaign.getRules()
                ));
    }
}
