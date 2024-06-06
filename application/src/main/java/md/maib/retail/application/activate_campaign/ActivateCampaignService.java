package md.maib.retail.application.activate_campaign;

import md.maib.retail.application.find_campaign_by_id.FindCampaignByIdUseCase;
import md.maib.retail.application.save_campaign.SaveCampaignUseCase;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.ports.Campaigns;

import java.util.Objects;

public class ActivateCampaignService implements ActivateCampaignUseCase {
    private final Campaigns campaigns;
    private final FindCampaignByIdUseCase findCampaignByIdUseCase;
    private final SaveCampaignUseCase saveCampaignUseCase;

    public ActivateCampaignService(Campaigns campaigns, FindCampaignByIdUseCase findCampaignByIdUseCase, SaveCampaignUseCase saveCampaignUseCase) {
        this.campaigns = Objects.requireNonNull(campaigns, "Campaigns must not be null");
        this.findCampaignByIdUseCase = findCampaignByIdUseCase;
        this.saveCampaignUseCase = saveCampaignUseCase;
    }

    @Override
    public boolean activate(CampaignId campaignId) {
        return findCampaignByIdUseCase.findById(campaignId)
                .map(campaignAllInfo -> {
                    var campaign = campaignAllInfo.toCampaign();
                    if (campaign.activate()) {
                        return saveCampaignUseCase.save(campaign);
                    }

                    return false;
                })
                .orElse(false);
    }
}
