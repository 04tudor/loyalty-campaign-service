package md.maib.retail.infrastructure.rest;

import md.maib.retail.application.find_effect_type_by_id.EffectTypeRecord;
import md.maib.retail.application.find_event_type_by_id.EventTypeRecord;
import md.maib.retail.model.campaign.CampaignMetaInfo;
import md.maib.retail.model.campaign.CampaignState;
import md.maib.retail.model.conditions.Rule;

import java.time.Instant;
import java.util.List;

public record RegisterCampaignRequest(
        CampaignMetaInfo metaInfo,
        Instant startInclusive,
        Instant endExclusive,
        CampaignState state,
        EventTypeRecord eventTypeRecord,
        List<Rule> rules,
        EffectTypeRecord effectTypeRecord
) {

}
