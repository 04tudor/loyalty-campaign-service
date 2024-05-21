package md.maib.retail.infrastructure.rest.test.integration;

import au.com.dius.pact.provider.junitsupport.State;
import io.vavr.control.Either;
import md.maib.retail.application.CampaignAllInfo;
import md.maib.retail.application.CampaignSomeInfo;
import md.maib.retail.application.LoyaltyEventsFindById.LoyaltyEffectType.EffectRecord;
import md.maib.retail.application.LoyaltyEventsFindById.LoyaltyEventField.EventFieldRecord;
import md.maib.retail.application.find_event_type_by_id.EventTypeRecord;
import md.maib.retail.application.campaigns_list_by_date.CampaignsListByDateUseCase;
import md.maib.retail.application.delete_campaign.DeleteCampaign;
import md.maib.retail.application.delete_campaign.DeleteCampaignUseCase;
import md.maib.retail.application.find_campaign_by_id.FindCampaignByIdUseCase;
import md.maib.retail.application.find_campaign_by_metainfo.FindCampaignByMetaInfoUseCase;
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

import java.time.LocalDate;
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
    @State("register campaign")
    public void aCampaignToBeRegister() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("key", "value");
        CampaignMetaInfo metaInfo = new CampaignMetaInfo(properties);

        LocalDate startInclusive = LocalDate.parse("2024-05-01");
        LocalDate endExclusive = LocalDate.parse("2024-06-01");
        CampaignState state = CampaignState.ACTIVE;

        FieldType fieldType = FieldType.BOOLEAN;
        LoyaltyEventField loyaltyEventField = new LoyaltyEventField(
                UUID.fromString("6fb2fcfd-b836-4102-8c29-f0c38c97965e"),
                "TestField",
                fieldType);
        LoyaltyEventType loyaltyEventType = new LoyaltyEventType(
                UUID.fromString("41862fa9-2054-435d-8068-c9b31725de9f"),
                "TestEvent",
                List.of(loyaltyEventField));
        EventTypeRecord eventTypeRecord = new EventTypeRecord("41862fa9-2054-435d-8068-c9b31725de9f", "TestTypeRecord", List.of(new EventFieldRecord("c9b31725de9f")));

        EffectRecord effectRecord = new EffectRecord(UUID.fromString("4ec0b56f-ff4c-4e7e-b257-68ce9f133a45"), "TestEffect", "10");

        List<Rule> rules = List.of(
                new Rule(
                        RuleId.valueOf("ce888298-f1e6-41dc-ab7e-2344bf70617c"),
                        List.of(new Condition(FieldType.DECIMAL, Operator.EQUALS, "5")),
                        List.of(effectRecord)
                )
        );

        CampaignId campaignId = CampaignId.valueOf(UUID.fromString("d2015c09-a251-4463-9a0d-710f92559c2a"));
        var registerCampaign = RegisterCampaign.create(
                metaInfo,
                startInclusive,
                endExclusive,
                state,
                String.valueOf(eventTypeRecord),
                rules
        ).get();

        when(registrationCampaignUseCase.registerCampaign(any()))
                .thenReturn(Either.right(campaignId));
    }


    @State("find campaign by date")
    void findCampaignByDate() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("key", "value");
        CampaignMetaInfo metaInfo = new CampaignMetaInfo(properties);

        var id = UUID.fromString("d2015c09-a251-4463-9a0d-710f92559c2a");
        Interval interval = Interval.of(parse("2018-11-30T18:35:24Z"), parse("2023-12-31T18:35:24Z"));
        CampaignState state = CampaignState.ACTIVE;

        LoyaltyEventField loyaltyEventField = new LoyaltyEventField(fromString("6fb2fcfd-b836-4102-8c29-f0c38c97965e"), "TestField", FieldType.STRING);
        LoyaltyEventType loyaltyEventType = new LoyaltyEventType(fromString("41862fa9-2054-435d-8068-c9b31725de9f"), "TestEvent", List.of(loyaltyEventField));



        CampaignSomeInfo campaignSomeInfo = new CampaignSomeInfo(id.toString(), metaInfo, interval, state, loyaltyEventType);
        LocalDate dateBetweenInterval = LocalDate.of(2022, 7, 15);

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
                                new LoyaltyEffectType(fromString("4ec0b56f-ff4c-4e7e-b257-68ce9f133a45"), "TestEffect", loyaltyEventType),
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
                                new LoyaltyEffectType(fromString("4ec0b56f-ff4c-4e7e-b257-68ce9f133a45"), "TestEffect", loyaltyEventType),
                                "10")
                        )
                )
        );

        CampaignAllInfo campaignAllInfo = new CampaignAllInfo(id.toString(), metaInfo, interval, state, loyaltyEventType, List.copyOf(rules));

        when(findCampaignByIdUseCase.findById(campaignId))
                .thenReturn(Optional.of(campaignAllInfo));

    }


    @State("delete a campaign by id")
    public void aCampaignToBeDeleted() {
        var id =fromString("d2015c09-a251-4463-9a0d-710f92559c2a");
        CampaignId campaignId = CampaignId.valueOf(id);
        DeleteCampaign deleteCampaign = new DeleteCampaign(campaignId);

        when(deleteCampaignUseCase.deleteCampaign(deleteCampaign)).thenReturn(Either.right(campaignId));
    }


}