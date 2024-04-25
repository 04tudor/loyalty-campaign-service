package md.maib.retail.application;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.campaign.CampaignState;
import md.maib.retail.model.campaign.CampaignMetaInfo;
import md.maib.retail.model.campaign.LoyaltyEventType;
import org.threeten.extra.Interval;
@Getter
@RequiredArgsConstructor
public class CampaignSomeInfo {
    private final CampaignId id;

    private final CampaignMetaInfo metaInfo;

    private final Interval activityInterval;

    private final CampaignState state;

    private final LoyaltyEventType loyaltyEventType;


}
