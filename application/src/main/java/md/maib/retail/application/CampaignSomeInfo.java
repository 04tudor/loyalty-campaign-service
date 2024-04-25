package md.maib.retail.application;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import md.maib.retail.model.campaign.*;
import org.threeten.extra.Interval;
@Getter
@RequiredArgsConstructor
public class CampaignSomeInfo {
    private final CampaignId id;

    private final CampaignMetaInfo metaInfo;

    private final Interval activityInterval;

    private final CampaignState state;

    private final LoyaltyEventType loyaltyEventType;

    public static CampaignSomeInfo valueOf(CampaignId id, CampaignMetaInfo metaInfo,
                                           Interval activityInterval, CampaignState state,
                                           LoyaltyEventType loyaltyEventType) {
        return new CampaignSomeInfo(id, metaInfo, activityInterval, state, loyaltyEventType);
    }
    public static CampaignSomeInfo valueOf(Campaign campaign) {
        return new CampaignSomeInfo(campaign.getId(), campaign.getMetaInfo(),
                campaign.getActivityInterval(), campaign.getState(),
                campaign.getLoyaltyEventType());
    }
}
