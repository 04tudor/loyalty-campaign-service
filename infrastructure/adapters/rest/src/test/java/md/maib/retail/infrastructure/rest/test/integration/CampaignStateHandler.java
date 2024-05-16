package md.maib.retail.infrastructure.rest.test.integration;

import au.com.dius.pact.provider.junitsupport.State;
import md.maib.retail.application.CampaignAllInfo;
import md.maib.retail.application.campaigns_list_by_date.CampaignsListByDateUseCase;
import md.maib.retail.application.delete_campaign.DeleteCampaign;
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

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static java.time.Instant.parse;
import static java.util.UUID.fromString;
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


    @State("find campaign by id")
    void  findCampaignById() {
        var id = fromString("d2015c09-a251-4463-9a0d-710f92559c2a");
        CampaignId campaignId=CampaignId.valueOf(id);
        CampaignMetaInfo metaInfo = new CampaignMetaInfo(Map.of());
        Interval interval = Interval.of(parse("2018-11-30T18:35:24.00"),parse("2023-12-31T18:35:24.00"));
        CampaignState state= CampaignState.ACTIVE;
        LoyaltyEventType loyaltyEventType= (LoyaltyEventType) Collections.emptyList();
        Collection<Rule> rules=Collections.emptyList();

        when(findCampaignByIdUseCase.findById(campaignId))
                .thenReturn(Optional.of(new CampaignAllInfo(campaignId, metaInfo,interval,state,loyaltyEventType,rules)));
    }


    @State("a campaign")
    public Map<String, Object> aCampaignToBeDeleted() {
        var id = fromString("d2015c09-a251-4463-9a0d-710f92559c2a");
        CampaignId campaignId=CampaignId.valueOf(id);
        DeleteCampaign deleteCampaign=new DeleteCampaign(campaignId);

        doNothing().when(deleteCampaignUseCase).deleteCampaign(deleteCampaign);

        return Map.of("campaignId", campaignId);
    }
}
