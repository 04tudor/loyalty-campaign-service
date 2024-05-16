package md.maib.retail.infrastructure.rest.test.integration;

import au.com.dius.pact.provider.junitsupport.State;
import md.maib.retail.application.CampaignAllInfo;
import md.maib.retail.application.campaigns_list_by_date.CampaignsListByDateUseCase;
import md.maib.retail.application.delete_campaign.DeleteCampaignUseCase;
import md.maib.retail.application.find_campaign_by_id.FindCampaignByIdUseCase;
import md.maib.retail.application.find_campaign_by_metainfo.FindCampaignByMetaInfoUseCase;
import md.maib.retail.application.register_newcampaign.RegistrationCampaignUseCase;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.campaign.CampaignMetaInfo;
import md.maib.retail.model.campaign.CampaignState;
import md.maib.retail.model.campaign.LoyaltyEventType;
import md.maib.retail.model.conditions.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.threeten.extra.Interval;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@Component
public class CampaignStateHandler {

    @Autowired
    Environment environment;

    @Autowired
    FindCampaignByIdUseCase findCampaignByIdUseCase;

    @Autowired
    FindCampaignByMetaInfoUseCase findCampaignByMetaInfoUseCase;

    @Autowired
    RegistrationCampaignUseCase registrationCampaignUseCase;

    @Autowired
    DeleteCampaignUseCase deleteCampaignUseCase;

    @Autowired
    CampaignsListByDateUseCase campaignsListByDateUseCase;


    @State("a campaign")
    Map<String, Object> findCampaignById() {
        CampaignId campaignId=CampaignId.newIdentity();

        CampaignMetaInfo metaInfo = new CampaignMetaInfo(Map.of());

        Instant start = Instant.parse("2024-05-16T00:00:00Z");
        Instant end = Instant.parse("2024-05-17T00:00:00Z");
        Interval interval = Interval.of(start, end);
        CampaignState state= CampaignState.ACTIVE;
        LoyaltyEventType loyaltyEventType= (LoyaltyEventType) Collections.emptyList();
        Collection<Rule> rules=Collections.emptyList();
        when(findCampaignByIdUseCase.findById(campaignId))
                .thenReturn(Optional.of(new CampaignAllInfo(campaignId, metaInfo,interval,state,loyaltyEventType,rules)));

        return Map.ofEntries(
                entry("id", campaignId),
                entry("metaInfo", metaInfo),
                entry("activityInterval", interval),
                entry("state", state),
                entry("loyaltyEventType", loyaltyEventType),
                entry("rules", rules)

        );
    }


    @State("a campaign")
    public Map<String, Object> aCampaignToBeDeleted() {
        CampaignId campaignId=CampaignId.newIdentity();

        doNothing().when(deleteCampaignUseCase).deleteCampaign(any());

        return Map.of("campaignId", campaignId);
    }
}
