package md.maib.retail.dto;

import md.maib.retail.model.campaign.CampaignState;
import md.maib.retail.model.conditions.Rule;
import md.maib.retail.model.campaign.LoyaltyEventType;
import org.threeten.extra.Interval;

import java.util.Collection;
import java.util.Map;

public record NewCampaignDto(
        Map<String ,String> metaInfo,

        Interval activityInterval,

        CampaignState state,
        LoyaltyEventType loyaltyEventType,

         Collection<Rule>rules

) {
}
