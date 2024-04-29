package md.maib.retail.infrastructure.rest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import md.maib.retail.application.register_newcampaign.RegisterCampaign;
import md.maib.retail.model.campaign.CampaignMetaInfo;
import md.maib.retail.model.campaign.CampaignState;
import md.maib.retail.model.campaign.LoyaltyEventType;
import md.maib.retail.model.conditions.Rule;

import java.time.LocalDate;
import java.util.List;
@Getter
@RequiredArgsConstructor

@ToString
public class RegisterCampaignRequest {
    private final CampaignMetaInfo metaInfo;
    private final LocalDate startInclusive;
    private final LocalDate endExclusive;
    private final CampaignState state;
    private final LoyaltyEventType loyaltyEventType;
    private final List<Rule> rules;
    public static RegisterCampaignRequest valueOf(RegisterCampaign registerCampaign) {
        return new RegisterCampaignRequest( registerCampaign.metaInfo(),
                registerCampaign.startInclusive(), registerCampaign.endExclusive(),
                registerCampaign.state(),registerCampaign.loyaltyEventType(),registerCampaign.rules());
    }
}
