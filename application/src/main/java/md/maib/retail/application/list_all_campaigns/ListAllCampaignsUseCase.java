package md.maib.retail.application.list_all_campaigns;

import md.maib.retail.application.CampaignAllInfo;
import md.maib.retail.model.ports.Campaigns;

import java.util.List;

public interface ListAllCampaignsUseCase {

    static ListAllCampaignsService defaultService(Campaigns campaigns) {
        return new ListAllCampaignsService(campaigns);
    }

    List<CampaignAllInfo> listAll();
}
