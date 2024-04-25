package md.maib.retail.campaigns_list_by_date;

import md.maib.retail.CampaignSomeInfo;
import md.maib.retail.model.ports.Campaigns;

import java.time.LocalDate;
import java.util.List;

public interface CampaignsListByDateUseCase {
    static CampaignsListByDateUseCase defaultService(Campaigns campaigns) {
        return new CampaignsListByDateService(campaigns);
    }
    List<CampaignSomeInfo> activeCampaignsByDate(LocalDate date);
}
