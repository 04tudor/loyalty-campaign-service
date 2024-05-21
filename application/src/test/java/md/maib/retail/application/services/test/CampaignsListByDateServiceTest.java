package md.maib.retail.application.services.test;

import md.maib.retail.application.CampaignSomeInfo;
import md.maib.retail.application.campaigns_list_by_date.CampaignsListByDateUseCase;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
 class CampaignsListByDateServiceTest {
    @Mock
    Campaigns campaigns;


    CampaignsListByDateUseCase target;

    @BeforeEach
    void setup() {
        target =  CampaignsListByDateUseCase.defaultService(campaigns);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(campaigns);
    }

    @Test
    void activeCampaignsByDate_ListOfCampaign() {
        LocalDate date = LocalDate.of(2024, 4, 25);

        CampaignId campaignId = new CampaignId(CampaignId.newIdentity().campaignId());
        Campaign campaign = new Campaign(
                campaignId,
                null,
                null,
                CampaignState.ACTIVE,
                null,
                new ArrayList<>()
        );
        when(campaigns.listByDate(date)).thenReturn(List.of(campaign));

        List<CampaignSomeInfo> result = target.activeCampaignsByDate(date);

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).id()).isEqualTo(CampaignId.stringvalueOf(campaignId));
    }

    @Test
    void activeCampaignsByDate_NoCampaigns_ReturnsEmptyList() {
        LocalDate date = LocalDate.of(2024, 4, 25);
        when(campaigns.listByDate(date)).thenReturn(new ArrayList<>());

        List<CampaignSomeInfo> result = target.activeCampaignsByDate(date);

            assertThat(result).isEmpty();
    }
}
