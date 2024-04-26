package md.maib.retail.application.services;

import md.maib.retail.application.CampaignAllInfo;
import md.maib.retail.application.find_campaign_by_metainfo.FindCampaignByMetaInfoUseCase;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindCampaignByMetaInfoServiceTest {
    @Mock
    Campaigns campaigns;

    FindCampaignByMetaInfoUseCase target;

    @BeforeEach
    void setup() {
        target =  FindCampaignByMetaInfoUseCase.defaultService(campaigns);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(campaigns);
    }

    @Test
    void findByMetaInfo_ReturnsCampaignAllInfo() {
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
        when(campaigns.findByMetaInfo(key, value)).thenReturn(List.of(campaign));

        List<CampaignAllInfo> result = target.findByMetaInfo(key, value);

        assertThat(result).isNotNull();
        assertThat(result.get(0).id()).isEqualTo(campaignId);
    }

    @Test
    void findByMetaInfo_NotFound_ReturnsEmptyList() {
        String key = "key";
        String value = "value";
        when(campaigns.findByMetaInfo(key, value)).thenReturn(new ArrayList<>());

        List<CampaignAllInfo> result = target.findByMetaInfo(key, value);

        assertThat(result).isEmpty();
    }
}
