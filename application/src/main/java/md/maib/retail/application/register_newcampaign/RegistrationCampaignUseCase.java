package md.maib.retail.application.register_newcampaign;

import io.vavr.control.Either;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.ports.Campaigns;


public interface RegistrationCampaignUseCase {
    Either<UseCaseProblemConflict, CampaignId> registerCampaign(RegisterCampaign command);


    static RegistrationCampaignUseCase defaultService(Campaigns campaigns) {
        return new RegisterCampaignService(campaigns);
    }

}
