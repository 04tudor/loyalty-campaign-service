package md.maib.retail.infrastructure.rest;


import com.fasterxml.jackson.annotation.JsonProperty;
import md.maib.retail.model.campaign.CampaignMetaInfo;
import md.maib.retail.model.campaign.CampaignState;
import md.maib.retail.model.campaign.LoyaltyEventType;
import md.maib.retail.model.conditions.Rule;

import java.time.LocalDate;
import java.util.List;


public record RegisterCampaignRequest (
        @JsonProperty("metaInfo")
        CampaignMetaInfo metaInfo,
        @JsonProperty("startInclusive")
        LocalDate startInclusive,
        @JsonProperty("endInclusive")
        LocalDate endExclusive,
        @JsonProperty("state")
        CampaignState state,
        @JsonProperty("loyaltyEventType")

        LoyaltyEventType loyaltyEventType,
        @JsonProperty("rules")
         List<Rule> rules
)
{

}
