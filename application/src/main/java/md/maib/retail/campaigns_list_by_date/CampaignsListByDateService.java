package md.maib.retail.campaigns_list_by_date;

import md.maib.retail.CampaignMapper;
import md.maib.retail.CampaignSomeInfo;
import md.maib.retail.model.ports.Campaigns;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

 class CampaignsListByDateService implements CampaignsListByDateUseCase{
    private final Campaigns campaigns;

    public CampaignsListByDateService(Campaigns campaigns) {
        this.campaigns = Objects.requireNonNull(campaigns,"Campaigns must not be null");
    }

    @Override
    public List<CampaignSomeInfo> activeCampaignsByDate(LocalDate date) {

        return campaigns.listByDate(date)
                .stream()
                .map(CampaignMapper::mapToCampaignSomeInfo
                ).toList();

    }
}
