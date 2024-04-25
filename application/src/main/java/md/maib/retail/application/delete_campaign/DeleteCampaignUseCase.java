package md.maib.retail.application.delete_campaign;

import io.vavr.control.Either;

import md.maib.retail.application.register_newcampaign.UseCaseProblemConflict;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.ports.Campaigns;

public interface DeleteCampaignUseCase {
    Either<UseCaseProblemConflict, CampaignId> deleteCampaign(DeleteCampaign command);

    static DeleteCampaignUseCase defaultService(Campaigns campaigns) {
        return new DeleteCampaignService(campaigns);
    }


}
