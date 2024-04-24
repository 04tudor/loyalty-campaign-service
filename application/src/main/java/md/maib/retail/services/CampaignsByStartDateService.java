package md.maib.retail.services;

import md.maib.retail.CampaignInfo;
import md.maib.retail.model.ports.Campaigns;
import md.maib.retail.usecase.CampaignsByStartDateUseCase;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public class CampaignsByStartDateService implements CampaignsByStartDateUseCase {
    private final Campaigns campaigns;

    public CampaignsByStartDateService(Campaigns campaigns) {
        this.campaigns = Objects.requireNonNull(campaigns,"Campaigns must not be null");
    }

    @Override
    public Optional<CampaignInfo> CampaignsByStartDate(LocalDate date) {
        return campaigns.listByDate(date)
                .map(campaign -> new CampaignInfo(
                        campaign.getId(),
                        campaign.getMetaInfo(),
                        campaign.getActivityInterval(),
                        campaign.getState(),
                        campaign.getLoyaltyEventType(),
                        campaign.getRules()
                                .stream().
                                toList()
                ));    }
}
