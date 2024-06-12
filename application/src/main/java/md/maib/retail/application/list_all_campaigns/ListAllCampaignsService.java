package md.maib.retail.application.list_all_campaigns;

import md.maib.retail.application.CampaignAllInfo;
import md.maib.retail.model.ports.Campaigns;

import java.util.List;
import java.util.Objects;

public class ListAllCampaignsService implements ListAllCampaignsUseCase {

    private final Campaigns campaigns;

    public ListAllCampaignsService(Campaigns campaigns) {
        this.campaigns = Objects.requireNonNull(campaigns, "Campaigns must not be null");
    }


    @Override
    public List<CampaignAllInfo> listAll() {
        return campaigns.listAll().stream().map(CampaignAllInfo::valueOf).toList();
    }
}
