package md.maib.retail.application.services.test;

import md.maib.retail.application.CampaignAllInfo;
import md.maib.retail.application.list_all_campaigns.ListAllCampaignsUseCase;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListAllCampaignsTest {
    @Mock
    Campaigns campaigns;

    ListAllCampaignsUseCase target;

    @BeforeEach
    void setup() {
        target = ListAllCampaignsUseCase.defaultService(campaigns);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(campaigns);
    }


    @Test
    void ListAllCampaigns() {
        String key = "key";
        String value = "value";
        CampaignId campaignId = new CampaignId(CampaignId.newIdentity().campaignId());
        Campaign campaign = new Campaign(
                campaignId,
                null,
                null,
                CampaignState.DRAFT,
                null,
                new ArrayList<>()
        );
        when(campaigns.listAll()).thenReturn(List.of(campaign));

        List<CampaignAllInfo> result = target.listAll();

        assertThat(result).isNotNull();
        assertThat(CampaignId.valueOf(UUID.fromString(result.get(0).id()))).isEqualTo(CampaignId.valueOf(campaignId.campaignId()));
    }

    @Test
    void listAll_NotFound_ReturnsEmptyList() {

        when(campaigns.listAll()).thenReturn(new ArrayList<>());

        List<CampaignAllInfo> result = target.listAll();

        assertThat(result).isEmpty();
    }
}
