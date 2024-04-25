package md.maib.retail.application;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import md.maib.retail.model.campaign.*;
import md.maib.retail.model.conditions.Rule;
import org.threeten.extra.Interval;

import java.util.Collection;
@Getter
@RequiredArgsConstructor
public class CampaignAllInfo {
    private final CampaignId id;

    private final CampaignMetaInfo metaInfo;

    private final Interval activityInterval;

    private final CampaignState state;

    private final LoyaltyEventType loyaltyEventType;

    private final Collection<Rule> rules;
    public static CampaignAllInfo valueOf(CampaignId id, CampaignMetaInfo metaInfo,
                                           Interval activityInterval, CampaignState state,
                                           LoyaltyEventType loyaltyEventType,Collection<Rule>rules) {
        return new CampaignAllInfo(id, metaInfo, activityInterval, state, loyaltyEventType,rules);
    }

    public static CampaignAllInfo valueOf(Campaign campaign) {
        return new CampaignAllInfo(campaign.getId(), campaign.getMetaInfo(),
                campaign.getActivityInterval(), campaign.getState(),
                campaign.getLoyaltyEventType(), campaign.getRules());
    }
}
