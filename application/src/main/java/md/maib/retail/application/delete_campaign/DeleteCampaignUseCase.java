package md.maib.retail.application.delete_campaign;

import io.vavr.control.Either;

import md.maib.retail.application.find_campaign_by_id.FindCampaignByIdUseCase;
import md.maib.retail.application.register_newcampaign.UseCaseProblemConflict;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.ports.Campaigns;

public interface DeleteCampaignUseCase {

    static DeleteCampaignUseCase defaultService(Campaigns campaigns) {
        return new DeleteCampaignService(campaigns,FindCampaignByIdUseCase.defaultService(campaigns));
    }
    Either<UseCaseProblemConflict, CampaignId> deleteCampaign(DeleteCampaign command);


}
