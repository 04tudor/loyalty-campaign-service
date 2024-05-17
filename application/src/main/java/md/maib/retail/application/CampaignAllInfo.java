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
    @JsonProperty("campaignId")
    private final String id;

    private final CampaignMetaInfo metaInfo;

    private final Interval activityInterval;

    private final CampaignState state;

    private final LoyaltyEventType loyaltyEventType;

    private final Collection<Rule> rules;


    public static CampaignAllInfo valueOf(Campaign campaign) {
        return new CampaignAllInfo(campaign.getId().campaignId().toString(), campaign.getMetaInfo(),
                campaign.getActivityInterval(), campaign.getState(),
                campaign.getLoyaltyEventType(), campaign.getRules());
    }
}
