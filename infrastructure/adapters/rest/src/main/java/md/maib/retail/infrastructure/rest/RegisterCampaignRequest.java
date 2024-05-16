package md.maib.retail.infrastructure.rest;


import md.maib.retail.model.campaign.CampaignMetaInfo;
import md.maib.retail.model.campaign.CampaignState;
import md.maib.retail.model.campaign.LoyaltyEventType;
import md.maib.retail.model.conditions.Rule;

import java.time.LocalDate;
import java.util.List;


public record RegisterCampaignRequest (
     CampaignMetaInfo metaInfo,
     LocalDate startInclusive,
     LocalDate endExclusive,
     CampaignState state,
     LoyaltyEventType loyaltyEventType,
     List<Rule> rules)
{

}
