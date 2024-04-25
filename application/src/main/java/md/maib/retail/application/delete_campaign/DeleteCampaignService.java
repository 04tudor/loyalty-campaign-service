package md.maib.retail.application.delete_campaign;

import io.vavr.control.Either;
import md.maib.retail.application.CampaignAllInfo;
import md.maib.retail.application.find_campaign_by_id.FindCampaignByIdUseCase;
import md.maib.retail.application.register_newcampaign.UseCaseProblemConflict;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.campaign.CampaignState;
import md.maib.retail.model.ports.Campaigns;

import java.util.Objects;
import java.util.Optional;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public class DeleteCampaignService implements DeleteCampaignUseCase{
    private final Campaigns campaigns;
    FindCampaignByIdUseCase findCampaignByIdUseCase;

    public DeleteCampaignService(Campaigns campaigns) {
        this.campaigns = Objects.requireNonNull(campaigns,"Campaigns must not be null");
        this.findCampaignByIdUseCase = FindCampaignByIdUseCase.defaultService(campaigns);
    }

    private boolean checkIfExists(CampaignId id) {
        if (findCampaignByIdUseCase.findById(id).isPresent())
            return  true;

        return false;
    }
    private boolean checkIfDraft(CampaignId id) {
        Optional<CampaignAllInfo> campaignAllInfo=findCampaignByIdUseCase.findById(id);
        CampaignAllInfo campaign=campaignAllInfo.orElse(null);
        if (campaign.state().equals(CampaignState.DRAFT))return  true;
        return false;
    }


    @Override
    public Either<UseCaseProblemConflict, CampaignId> deleteCampaign(DeleteCampaign command) {
        CampaignId id=command.id();
        if (checkIfExists(id)&&checkIfDraft(id)) {
            if (campaigns.delete(command.id())) {

                return right(id);
            }
        }
            return left(new UseCaseProblemConflict("CampaignWithThisIdDoesntExists"));
    }
}
