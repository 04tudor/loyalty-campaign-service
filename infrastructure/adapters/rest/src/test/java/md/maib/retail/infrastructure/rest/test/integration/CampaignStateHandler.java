package md.maib.retail.infrastructure.rest.test.integration;

import au.com.dius.pact.provider.junitsupport.State;
import md.maib.retail.application.CampaignAllInfo;
import md.maib.retail.application.campaigns_list_by_date.CampaignsListByDateUseCase;
import md.maib.retail.application.delete_campaign.DeleteCampaignUseCase;
import md.maib.retail.application.find_campaign_by_id.FindCampaignByIdUseCase;
import md.maib.retail.application.find_campaign_by_metainfo.FindCampaignByMetaInfoUseCase;
import md.maib.retail.application.register_newcampaign.RegistrationCampaignUseCase;
import md.maib.retail.model.campaign.*;
import md.maib.retail.model.conditions.Condition;
import md.maib.retail.model.conditions.Operator;
import md.maib.retail.model.conditions.Rule;
import md.maib.retail.model.conditions.RuleId;
import md.maib.retail.model.effects.Effect;
import md.maib.retail.model.effects.LoyaltyEffectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.threeten.extra.Interval;

import java.util.*;

import static java.time.Instant.parse;
import static java.util.UUID.fromString;
import static org.mockito.Mockito.when;

@Component
public class CampaignStateHandler implements StateHandler{



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


    @State("get campaign by id")
    void findCampaignById() {
        var id = fromString("d2015c09-a251-4463-9a0d-710f92559c2a");
        CampaignId campaignId = CampaignId.valueOf(id);
        Map<String, Object> properties = new HashMap<>();
        properties.put("key", "value");
        CampaignMetaInfo metaInfo = new CampaignMetaInfo(properties);
        Interval interval = Interval.of(parse("2018-11-30T18:35:24Z"), parse("2023-12-31T18:35:24Z"));
        CampaignState state = CampaignState.ACTIVE;

        LoyaltyEventField loyaltyEventField = new LoyaltyEventField(fromString("6fb2fcfd-b836-4102-8c29-f0c38c97965e"), "TestField", FieldType.STRING);
        LoyaltyEventType loyaltyEventType = new LoyaltyEventType(fromString("41862fa9-2054-435d-8068-c9b31725de9f"), "TestEvent", List.of(loyaltyEventField));

        RuleId ruleId = RuleId.valueOf(fromString("ce888298-f1e6-41dc-ab7e-2344bf70617c"));

        Collection<Rule> rules = List.of(
                new Rule(
                        ruleId,
                        List.of(
                                new Condition(FieldType.DECIMAL, Operator.EQUALS, "5")
                        ),
                        List.of(
                                new Effect(
                                        new LoyaltyEffectType(fromString("4ec0b56f-ff4c-4e7e-b257-68ce9f133a45"), "TestEffect", loyaltyEventType),
                                        "10")
                        )
                )
        );
        when(findCampaignByIdUseCase.findById(campaignId))
                .thenReturn(Optional.of(new CampaignAllInfo(campaignId, metaInfo, interval, state, loyaltyEventType, rules)));
    }


//    @State("a campaign")
//    public Map<String, Object> aCampaignToBeDeleted() {
//        var id = fromString("d2015c09-a251-4463-9a0d-710f92559c2a");
//        CampaignId campaignId=CampaignId.valueOf(id);
//        DeleteCampaign deleteCampaign=new DeleteCampaign(campaignId);
//
//        doNothing().when(deleteCampaignUseCase).deleteCampaign(deleteCampaign);
//
//        return Map.of("campaignId", campaignId);
//    }
}
