package md.maib.retail.application;

import md.maib.retail.model.campaign.*;
import md.maib.retail.model.conditions.Rule;
import org.threeten.extra.Interval;

import java.util.Collection;

public class CampaignMapper {
    public static CampaignSomeInfo mapToCampaignSomeInfo(Campaign campaign) {
        CampaignId id = campaign.getId();
        CampaignMetaInfo metaInfo = campaign.getMetaInfo();
        Interval activityInterval = campaign.getActivityInterval();
        CampaignState state = campaign.getState();
        LoyaltyEventType loyaltyEventType = campaign.getLoyaltyEventType();

        return CampaignSomeInfo.valueOf(id, metaInfo, activityInterval, state, loyaltyEventType);
    }

    public static CampaignAllInfo mapToCampaignAllInfo(Campaign campaign) {
        CampaignId id = campaign.getId();
        CampaignMetaInfo metaInfo = campaign.getMetaInfo();
        Interval activityInterval = campaign.getActivityInterval();
        CampaignState state = campaign.getState();
        LoyaltyEventType loyaltyEventType = campaign.getLoyaltyEventType();
        Collection<Rule> rules=campaign.getRules();
        return CampaignAllInfo.valueOf(id, metaInfo, activityInterval, state, loyaltyEventType, rules);
    }
}
