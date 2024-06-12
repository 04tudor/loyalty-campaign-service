package md.maib.retail.infrastructure.rest;

import au.com.dius.pact.provider.junitsupport.State;
import io.vavr.control.Either;
import md.maib.retail.application.CampaignAllInfo;
import md.maib.retail.application.CampaignSomeInfo;
import md.maib.retail.application.activate_campaign.ActivateCampaignUseCase;
import md.maib.retail.application.campaigns_list_by_date.CampaignsListByDateUseCase;
import md.maib.retail.application.delete_campaign.DeleteCampaign;
import md.maib.retail.application.delete_campaign.DeleteCampaignUseCase;
import md.maib.retail.application.find_campaign_by_id.FindCampaignByIdUseCase;
import md.maib.retail.application.find_campaign_by_metainfo.FindCampaignByMetaInfoUseCase;
import md.maib.retail.application.find_effect_type_by_id.EffectTypeRecord;
import md.maib.retail.application.find_effect_type_by_id.FindByIdLoyaltyEffectTypeUseCase;
import md.maib.retail.application.find_event_type_by_id.EventTypeRecord;
import md.maib.retail.application.find_event_type_by_id.FindByIdLoyaltyEventTypeUseCase;
import md.maib.retail.application.list_all_campaigns.ListAllCampaignsUseCase;
import md.maib.retail.application.register_newcampaign.RegisterCampaign;
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

import java.time.Instant;
import java.util.*;

import static java.time.Instant.parse;
import static java.util.UUID.fromString;
import static org.mockito.ArgumentMatchers.any;
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
    @Autowired
    FindByIdLoyaltyEventTypeUseCase findByIdLoyaltyEventTypeUseCase;
    @Autowired
    FindByIdLoyaltyEffectTypeUseCase findByIdLoyaltyEffectTypeUseCase;
    @Autowired
    ActivateCampaignUseCase activateCampaignUseCase;

    @Autowired
    ListAllCampaignsUseCase listAllCampaignsUseCase;

    @State("List All campaigns")
    void listAll() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("key", "value");
        CampaignMetaInfo metaInfo = new CampaignMetaInfo(properties);

        var id = UUID.fromString("d2015c09-a251-4463-9a0d-710f92559c2a");
        Interval interval = Interval.of(parse("2018-11-30T18:35:24Z"), parse("2023-12-31T18:35:24Z"));
        CampaignState state = CampaignState.ACTIVE;

        LoyaltyEventField loyaltyEventField = new LoyaltyEventField(fromString("6fb2fcfd-b836-4102-8c29-f0c38c97965e"), "TestField", FieldType.STRING);
        LoyaltyEventType loyaltyEventType = new LoyaltyEventType(fromString("41862fa9-2054-435d-8068-c9b31725de9f"), "TestEvent", List.of(loyaltyEventField));

        RuleId ruleId = RuleId.valueOf("ce888298-f1e6-41dc-ab7e-2344bf70617c");

        Collection<Rule> rules = List.of(
                new Rule(
                        ruleId,
                        List.of(new Condition(FieldType.DECIMAL, Operator.EQUALS, "5")),
                        List.of(new Effect(
                                new LoyaltyEffectType(fromString("4ec0b56f-ff4c-4e7e-b257-68ce9f133a45"), "TestEffect", fromString("41862fa9-2054-435d-8068-c9b31725de9f")),
                                "10")
                        )
                )
        );

        CampaignAllInfo campaignAllInfo = new CampaignAllInfo(id.toString(), metaInfo, interval, state, loyaltyEventType, List.copyOf(rules));

        when(listAllCampaignsUseCase.listAll())
                .thenReturn(List.of(campaignAllInfo));
    }
    @State("register campaign")
    public void aCampaignToBeRegister() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("key", "value");
        CampaignMetaInfo metaInfo = new CampaignMetaInfo(properties);

        Instant startInclusive = Instant.parse("2018-11-30T18:35:24Z");
        Instant endExclusive = Instant.parse("2023-12-31T18:35:24Z");
        CampaignState state = CampaignState.DRAFT;

        EventTypeRecord eventTypeRecord = new EventTypeRecord("57b2516a-fd15-4057-a04a-c725a0a80e1e");
        EffectTypeRecord effectTypeRecord = new EffectTypeRecord("1414d3f4-7978-4f4b-a532-3ece801e253c");


        LoyaltyEventType loyaltyEventType=new LoyaltyEventType(
                fromString(eventTypeRecord.id()),
                "TestEventType",
                    List.of(new LoyaltyEventField(
                        fromString("2cf5b5af-1986-4e0e-abe4-21be30580dd3"),"TestField",FieldType.STRING )));

        LoyaltyEffectType loyaltyEffectType=new LoyaltyEffectType(
                fromString(effectTypeRecord.id()),
                "TestEffectType",
                loyaltyEventType.getId()
                );
        when(findByIdLoyaltyEventTypeUseCase.findById(eventTypeRecord.id())).thenReturn(Optional.of(loyaltyEventType));
        when(findByIdLoyaltyEffectTypeUseCase.findById(effectTypeRecord.id())).thenReturn(Optional.of(loyaltyEffectType));

        List<Rule> rules = List.of(
                new Rule(
                        RuleId.newIdentity(),
                        List.of(new Condition(FieldType.DECIMAL, Operator.EQUALS, "5")),
                        List.of(new Effect( loyaltyEffectType,"10"))
                )
        );


        CampaignId campaignId = CampaignId.valueOf(UUID.fromString("d2015c09-a251-4463-9a0d-710f92559c2a"));
        var registerCampaign = RegisterCampaign.create(
                metaInfo,
                startInclusive,
                endExclusive,
                state,
               eventTypeRecord,
                rules,
                effectTypeRecord
        ).get();

        when(registrationCampaignUseCase.registerCampaign(any()))
                .thenReturn(Either.right(campaignId));
    }


    @State("find campaign by date")
    void findCampaignByDate() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("key", "value");
        CampaignMetaInfo metaInfo = new CampaignMetaInfo(properties);

        UUID id = UUID.fromString("d2015c09-a251-4463-9a0d-710f92559c2a");
        Interval interval = Interval.of(Instant.parse("2018-11-30T18:35:24Z"), Instant.parse("2023-12-31T18:35:24Z"));
        CampaignState state = CampaignState.ACTIVE;

        LoyaltyEventField loyaltyEventField = new LoyaltyEventField(UUID.fromString("6fb2fcfd-b836-4102-8c29-f0c38c97965e"), "TestField", FieldType.STRING);
        LoyaltyEventType loyaltyEventType = new LoyaltyEventType(UUID.fromString("41862fa9-2054-435d-8068-c9b31725de9f"), "TestEvent", List.of(loyaltyEventField));

        CampaignSomeInfo campaignSomeInfo = new CampaignSomeInfo(id.toString(), metaInfo, interval, state, loyaltyEventType);
        Instant dateBetweenInterval = Instant.parse("2022-07-15T00:00:00Z");

        when(campaignsListByDateUseCase.activeCampaignsByDate(dateBetweenInterval))
                .thenReturn(List.of(campaignSomeInfo));
    }


    @State("find campaign by metaInfo")
    void findCampaignByMetaInfo() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("key", "value");
        CampaignMetaInfo metaInfo = new CampaignMetaInfo(properties);

        var id = UUID.fromString("d2015c09-a251-4463-9a0d-710f92559c2a");
        Interval interval = Interval.of(parse("2018-11-30T18:35:24Z"), parse("2023-12-31T18:35:24Z"));
        CampaignState state = CampaignState.ACTIVE;

        LoyaltyEventField loyaltyEventField = new LoyaltyEventField(fromString("6fb2fcfd-b836-4102-8c29-f0c38c97965e"), "TestField", FieldType.STRING);
        LoyaltyEventType loyaltyEventType = new LoyaltyEventType(fromString("41862fa9-2054-435d-8068-c9b31725de9f"), "TestEvent", List.of(loyaltyEventField));

        RuleId ruleId = RuleId.valueOf("ce888298-f1e6-41dc-ab7e-2344bf70617c");

        Collection<Rule> rules = List.of(
                new Rule(
                        ruleId,
                        List.of(new Condition(FieldType.DECIMAL, Operator.EQUALS, "5")),
                        List.of(new Effect(
                                new LoyaltyEffectType(fromString("4ec0b56f-ff4c-4e7e-b257-68ce9f133a45"), "TestEffect", fromString("41862fa9-2054-435d-8068-c9b31725de9f")),
                                "10")
                        )
                )
        );

        CampaignAllInfo campaignAllInfo = new CampaignAllInfo(id.toString(), metaInfo, interval, state, loyaltyEventType, List.copyOf(rules));

        when(findCampaignByMetaInfoUseCase.findByMetaInfo("key", "value"))
                .thenReturn(List.of(campaignAllInfo));
    }

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

        RuleId ruleId = RuleId.valueOf("ce888298-f1e6-41dc-ab7e-2344bf70617c");

        Collection<Rule> rules = List.of(
                new Rule(
                        ruleId,
                        List.of(new Condition(FieldType.DECIMAL, Operator.EQUALS, "5")),
                        List.of(new Effect(
                                new LoyaltyEffectType(fromString("4ec0b56f-ff4c-4e7e-b257-68ce9f133a45"), "TestEffect", fromString("41862fa9-2054-435d-8068-c9b31725de9f")),
                                "10")
                        )
                )
        );

        CampaignAllInfo campaignAllInfo = new CampaignAllInfo(id.toString(), metaInfo, interval, state, loyaltyEventType, List.copyOf(rules));

        when(findCampaignByIdUseCase.findById(campaignId)).thenReturn(Optional.of(campaignAllInfo));
    }


    @State("delete a campaign by id")
    public void aCampaignToBeDeleted() {
        var id =fromString("d2015c09-a251-4463-9a0d-710f92559c2a");
        CampaignId campaignId = CampaignId.valueOf(id);
        DeleteCampaign deleteCampaign = new DeleteCampaign(campaignId);

        when(deleteCampaignUseCase.deleteCampaign(deleteCampaign)).thenReturn(Either.right(campaignId));
    }


    @State("activate a campaign by id")
    public void aCampaignToBeActivated() {
        var campaignId = CampaignId.valueOf(UUID.fromString("d2015c09-a251-4463-9a0d-710f92559c2a"));

        Campaign campaign = new Campaign(
                campaignId,
                new CampaignMetaInfo(Collections.singletonMap("key", "value")),
                Interval.of(Instant.parse("2018-11-30T18:35:24Z"), Instant.parse("2023-12-31T18:35:24Z")),
                CampaignState.DRAFT,
                new LoyaltyEventType(UUID.fromString("41862fa9-2054-435d-8068-c9b31725de9f"), "TestEvent", List.of(
                        new LoyaltyEventField(UUID.fromString("6fb2fcfd-b836-4102-8c29-f0c38c97965e"), "TestField", FieldType.STRING))),
                Collections.emptyList()
        );

        when(findCampaignByIdUseCase.findById(campaignId)).thenReturn(Optional.of(new CampaignAllInfo(
                campaignId.toString(),
                campaign.getMetaInfo(),
                campaign.getActivityInterval(),
                campaign.getState(),
                campaign.getLoyaltyEventType(),
                List.copyOf(campaign.getRules())
        )));

        when(activateCampaignUseCase.activate(campaignId)).thenReturn(true);
    }

}
