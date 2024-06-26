package md.maib.retail.application.services.test;

import io.vavr.control.Either;
import md.maib.retail.application.delete_campaign.DeleteCampaign;
import md.maib.retail.application.delete_campaign.DeleteCampaignUseCase;
import md.maib.retail.application.register_newcampaign.UseCaseProblemConflict;
import md.maib.retail.junit.UnitTest;
import md.maib.retail.model.campaign.Campaign;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.campaign.CampaignState;
import md.maib.retail.model.ports.Campaigns;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@UnitTest
@ExtendWith(MockitoExtension.class)
class DeleteCampaignServiceTest {
    @Mock
    Campaigns campaigns;

    DeleteCampaignUseCase target;

    @BeforeEach
    void setup() {
        target = DeleteCampaignUseCase.defaultService(campaigns);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(campaigns);
    }

    @Test
    void deleteExistingCampaign() {
        CampaignId campaignId = CampaignId.valueOf(CampaignId.newIdentity().campaignId());
        Campaign campaign = new Campaign(campaignId, null, null, CampaignState.DRAFT, null, null);
        when(campaigns.getById(campaignId)).thenReturn(Optional.of(campaign));
        when(campaigns.delete(campaignId)).thenReturn(true);

        Either<UseCaseProblemConflict, CampaignId> result = target.deleteCampaign(DeleteCampaign.create(campaignId).get());

        assertThat(result).isNotNull();
        assertThat(result.get()).isEqualTo(campaignId);
        verify(campaigns).getById(campaignId);
        verify(campaigns).delete(campaignId);
    }

    @Test
    void deleteNoExistingCampaign() {
        CampaignId campaignId = CampaignId.valueOf(CampaignId.newIdentity().campaignId());
        when(campaigns.getById(campaignId)).thenReturn(Optional.empty());

        Either<UseCaseProblemConflict, CampaignId> result = target.deleteCampaign(DeleteCampaign.create(campaignId).get());

        assertThat(result.getLeft()).isNotNull();
        assertThat(result.getLeft().getMessage()).isEqualTo("CampaignWithThisIdDoesntExistsOrActiveCampaign");
        verify(campaigns).getById(campaignId);
        verifyNoMoreInteractions(campaigns);
    }

    @Test
    void deleteActiveCampaign() {
        CampaignId campaignId = CampaignId.valueOf(CampaignId.newIdentity().campaignId());
        Campaign campaign = new Campaign(campaignId, null, null, CampaignState.ACTIVE, null, null);
        when(campaigns.getById(campaignId)).thenReturn(Optional.of(campaign));

        Either<UseCaseProblemConflict, CampaignId> result = target.deleteCampaign(DeleteCampaign.create(campaignId).get());

        assertThat(result.getLeft()).isNotNull();
        assertThat(result.getLeft().getMessage()).isEqualTo("CampaignWithThisIdDoesntExistsOrActiveCampaign");
        verify(campaigns).getById(campaignId);
        verifyNoMoreInteractions(campaigns);
    }
}
