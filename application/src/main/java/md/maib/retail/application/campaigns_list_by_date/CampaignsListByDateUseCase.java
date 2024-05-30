package md.maib.retail.application.campaigns_list_by_date;

import md.maib.retail.application.CampaignSomeInfo;
import md.maib.retail.model.ports.Campaigns;

import java.time.Instant;
import java.util.List;

public interface CampaignsListByDateUseCase {
    static CampaignsListByDateUseCase defaultService(Campaigns campaigns) {
        return new CampaignsListByDateService(campaigns);
    }
    List<CampaignSomeInfo> activeCampaignsByDate(Instant date);
}
