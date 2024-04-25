package md.maib.retail.usecase;

import io.vavr.control.Either;
import md.maib.retail.RegisterCampaign;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.ports.Campaigns;
import md.maib.retail.services.RegisterCampaignService;


public interface RegistrationCampaignUseCase {
    Either<UseCaseProblemConflict, CampaignId> registerCampaign(RegisterCampaign command);


    static RegistrationCampaignUseCase defaultService(Campaigns campaigns) {
        return new RegisterCampaignService(campaigns);
    }

}
