package md.maib.retail.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import md.maib.retail.model.campaign.*;
import org.threeten.extra.Interval;
@Getter
@RequiredArgsConstructor
public class CampaignSomeInfo {
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


    public static CampaignSomeInfo valueOf(Campaign campaign) {
        return new CampaignSomeInfo(campaign.getId().toString(), campaign.getMetaInfo(),
                campaign.getActivityInterval(), campaign.getState(),
                campaign.getLoyaltyEventType());
    }
}
