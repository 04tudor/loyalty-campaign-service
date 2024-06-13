package md.maib.retail.application.services.test;

import md.maib.retail.application.CampaignAllInfo;
import md.maib.retail.application.find_campaign_by_id.FindCampaignByIdUseCase;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@UnitTest
@ExtendWith(MockitoExtension.class)
class FindCampaignByIdServiceTest {
    @Mock
    Campaigns campaigns;

    FindCampaignByIdUseCase target;

    @BeforeEach
    void setup() {
        target = FindCampaignByIdUseCase.defaultService(campaigns);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(campaigns);
    }

    @Test
    void findById_CampaignAllInfo() {
        CampaignId campaignId = new CampaignId(CampaignId.newIdentity().campaignId());
        Campaign campaign = new Campaign(
                campaignId,
                null,
                null,
                CampaignState.DRAFT,
                null,
                null
        );
        when(campaigns.getById(campaignId)).thenReturn(Optional.of(campaign));

        Optional<CampaignAllInfo> result = target.findById(campaignId);

        assertThat(result).isPresent();
        assertThat(CampaignId.valueOf(UUID.fromString(result.get().id()))).isEqualTo(CampaignId.valueOf(campaignId.campaignId()));

    }

    @Test
    void findById_NotFound_ReturnsEmptyOptional() {
        CampaignId campaignId = new CampaignId(CampaignId.newIdentity().campaignId());
        when(campaigns.getById(campaignId)).thenReturn(Optional.empty());

        Optional<CampaignAllInfo> result = target.findById(campaignId);

        assertThat(result).isEmpty();
    }
}
