package md.maib.retail.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import md.maib.retail.model.campaign.*;
import md.maib.retail.model.conditions.Rule;
import org.threeten.extra.Interval;

import java.util.Collection;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class CampaignAllInfo {
    @JsonProperty("id")
    private final String id;

    @JsonProperty("metaInfo")
    private final CampaignMetaInfo metaInfo;

    @JsonProperty("interval")
    private final Interval interval;

    @JsonProperty("state")
    private final CampaignState state;

    @JsonProperty("loyaltyEventType")
    private final LoyaltyEventType loyaltyEventType;

    @JsonProperty("rules")
    private final Collection<Rule> rules;

    public static CampaignAllInfo valueOf(Campaign campaign) {
        return new CampaignAllInfo(campaign.getId().campaignId().toString(), campaign.getMetaInfo(),
                campaign.getActivityInterval(), campaign.getState(),
                campaign.getLoyaltyEventType(), campaign.getRules());
    }
}
