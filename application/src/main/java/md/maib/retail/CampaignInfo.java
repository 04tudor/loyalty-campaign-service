package md.maib.retail;

import lombok.Getter;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.campaign.CampaignMetaInfo;
import md.maib.retail.model.campaign.CampaignState;
import md.maib.retail.model.campaign.LoyaltyEventType;
import md.maib.retail.model.conditions.Rule;
import org.threeten.extra.Interval;

import java.util.Collection;
@Getter
public class CampaignInfo {
    private final CampaignId id;

    private final CampaignMetaInfo metaInfo;

    private final Interval activityInterval;

    private final CampaignState state;

    private final LoyaltyEventType loyaltyEventType;

    private final Collection<Rule> rules;

    public CampaignInfo(CampaignId id, CampaignMetaInfo metaInfo, Interval activityInterval, CampaignState state, LoyaltyEventType loyaltyEventType, Collection<Rule> rules) {
        this.id = id;
        this.metaInfo = metaInfo;
        this.activityInterval = activityInterval;
        this.state = state;
        this.loyaltyEventType = loyaltyEventType;
        this.rules = rules;
    }
}
