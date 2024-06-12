package md.maib.retail.application.delete_campaign;

import io.vavr.control.Either;
import md.maib.retail.application.find_campaign_by_id.FindCampaignByIdUseCase;
import md.maib.retail.application.register_newcampaign.UseCaseProblemConflict;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.campaign.CampaignState;
import md.maib.retail.model.ports.Campaigns;

import java.util.Objects;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public class DeleteCampaignService implements DeleteCampaignUseCase {
    private final Campaigns campaigns;
    private final FindCampaignByIdUseCase findCampaignByIdUseCase;

    public DeleteCampaignService(Campaigns campaigns, FindCampaignByIdUseCase findCampaignByIdUseCase) {
        this.campaigns = Objects.requireNonNull(campaigns, "Campaigns must not be null");
        this.findCampaignByIdUseCase = Objects.requireNonNull(findCampaignByIdUseCase, "FindCampaignByIdUseCase must not be null");
    }

    private boolean isDraftState(CampaignId id) {
        return findCampaignByIdUseCase.findById(id)
                .map(campaignAllInfo -> campaignAllInfo.state().equals(CampaignState.DRAFT))
                .orElse(false);
    }

    @Override
    public Either<UseCaseProblemConflict, CampaignId> deleteCampaign(DeleteCampaign command) {
        CampaignId id = command.id();
        if (isDraftState(id)) {
            boolean deleted = campaigns.delete(command.id());
            if (deleted) {
                return right(id);
            }
        }
        return left(new UseCaseProblemConflict("CampaignWithThisIdDoesntExistsOrActiveCampaign"));
    }
}