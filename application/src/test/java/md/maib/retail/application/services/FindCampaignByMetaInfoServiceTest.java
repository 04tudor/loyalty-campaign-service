package md.maib.retail.application.services;

import md.maib.retail.application.CampaignAllInfo;
import md.maib.retail.application.find_campaign_by_metainfo.FindCampaignByMetaInfoService;
import md.maib.retail.model.campaign.Campaign;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.campaign.CampaignState;
import md.maib.retail.model.ports.Campaigns;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
 class FindCampaignByMetaInfoServiceTest {
    @Mock
    Campaigns campaigns;

    @InjectMocks
    FindCampaignByMetaInfoService findCampaignByMetaInfoService;

    @BeforeEach
    void setup() {
        findCampaignByMetaInfoService = new FindCampaignByMetaInfoService(campaigns);
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
        when(campaigns.findByMetaInfo(key, value)).thenReturn(Optional.of(campaign));

        Optional<CampaignAllInfo> result = findCampaignByMetaInfoService.findByMetaInfo(key, value);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(campaignId);
    }

    @Test
    void findByMetaInfo_NotFound_ReturnsEmptyOptional() {
        String key = "key";
        String value = "value";
        when(campaigns.findByMetaInfo(key, value)).thenReturn(Optional.empty());

        Optional<CampaignAllInfo> result = findCampaignByMetaInfoService.findByMetaInfo(key, value);

        assertThat(result).isEmpty();
    }
}
