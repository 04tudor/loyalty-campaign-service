package md.maib.retail.application.campaigns_list_by_date;

import md.maib.retail.application.CampaignSomeInfo;
import md.maib.retail.model.ports.Campaigns;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class CampaignsListByDateService implements CampaignsListByDateUseCase {
    private final Campaigns campaigns;

    public CampaignsListByDateService(Campaigns campaigns) {
        this.campaigns = Objects.requireNonNull(campaigns, "Campaigns must not be null");
    }

    @Override
    public List<CampaignSomeInfo> activeCampaignsByDate(Instant date) {
        return campaigns.listByDate(date)
                .stream()
                .map(CampaignSomeInfo::valueOf)
                .toList();
    }

}
