package md.maib.retail.infrastructure.rest;


import md.maib.retail.application.register_newcampaign.RegisterCampaign;
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
     public static RegisterCampaignRequest valueOf(RegisterCampaign registerCampaign) {
        return new RegisterCampaignRequest( registerCampaign.metaInfo(),
                registerCampaign.startInclusive(),
                registerCampaign.endExclusive(),
                registerCampaign.state(),
                registerCampaign.loyaltyEventType(),
                registerCampaign.rules());

    }
}
