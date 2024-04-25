package md.maib.retail;

import lombok.Getter;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.campaign.CampaignMetaInfo;
import md.maib.retail.model.campaign.CampaignState;
import md.maib.retail.model.campaign.LoyaltyEventType;
import org.threeten.extra.Interval;
@Getter
public class CampaignSomeInfo {
    private final CampaignId id;

    private final CampaignMetaInfo metaInfo;

    private final Interval activityInterval;

    private final CampaignState state;

    private final LoyaltyEventType loyaltyEventType;

    public CampaignSomeInfo(CampaignId id, CampaignMetaInfo metaInfo, Interval activityInterval, CampaignState state, LoyaltyEventType loyaltyEventType) {
        this.id = id;
        this.metaInfo = metaInfo;
        this.activityInterval = activityInterval;
        this.state = state;
        this.loyaltyEventType = loyaltyEventType;
    }
}
