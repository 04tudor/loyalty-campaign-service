package md.maib.retail.application.register_newcampaign;

import io.vavr.control.Either;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.ports.Campaigns;
import md.maib.retail.model.ports.LoyaltyEffectTypes;
import md.maib.retail.model.ports.LoyaltyEventTypes;

public interface RegistrationCampaignUseCase {
    static RegistrationCampaignUseCase defaultService(Campaigns campaigns, LoyaltyEventTypes loyaltyEventTypes, LoyaltyEffectTypes loyaltyEffectTypes) {
        return new RegisterCampaignService(campaigns, loyaltyEventTypes, loyaltyEffectTypes);
    }

    Either<UseCaseProblemConflict, CampaignId> registerCampaign(RegisterCampaign command);
}
