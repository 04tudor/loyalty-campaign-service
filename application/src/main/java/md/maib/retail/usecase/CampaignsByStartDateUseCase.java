package md.maib.retail.usecase;

import md.maib.retail.CampaignInfo;
import md.maib.retail.model.ports.Campaigns;
import md.maib.retail.services.CampaignsByStartDateService;

import java.time.LocalDate;
import java.util.Optional;

public interface CampaignsByStartDateUseCase {
    static CampaignsByStartDateUseCase defaultService(Campaigns campaigns) {
        return new CampaignsByStartDateService(campaigns);
    }
    Optional<CampaignInfo> CampaignsByStartDate(LocalDate date);

}
