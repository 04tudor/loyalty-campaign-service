package md.maib.retail.application.campaigns_list_by_date;

import md.maib.retail.application.CampaignSomeInfo;
import md.maib.retail.model.campaign.CampaignState;
import md.maib.retail.model.ports.Campaigns;
import org.threeten.extra.Interval;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CampaignsListByDateService implements CampaignsListByDateUseCase{
    private final Campaigns campaigns;

    public CampaignsListByDateService(Campaigns campaigns) {
        this.campaigns = Objects.requireNonNull(campaigns,"Campaigns must not be null");
    }

    @Override
    public List<CampaignSomeInfo> activeCampaignsByDate(LocalDate date) {
        return campaigns.listByDate(date)
                .stream()
                .filter(campaign -> isDateWithinInterval(CampaignSomeInfo.valueOf(campaign), date) && campaign.getState() == CampaignState.ACTIVE)
                .map(CampaignSomeInfo::valueOf)
                .toList();
    }

    private boolean isDateWithinInterval(CampaignSomeInfo campaign, LocalDate date) {
        Interval interval = campaign.interval();
        LocalDate start = interval.getStart().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        LocalDate end = interval.getEnd().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        return !date.isBefore(start) && !date.isAfter(end);
    }
}
