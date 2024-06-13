package md.maib.retail.application;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import md.maib.retail.model.campaign.*;
import md.maib.retail.model.conditions.Rule;
import org.threeten.extra.Interval;

import java.util.Collection;

@Getter
@RequiredArgsConstructor
public class CampaignEntity {
    private final CampaignId id;

    private final CampaignMetaInfo metaInfo;

    private final Interval interval;

    private final CampaignState state;

    private final LoyaltyEventType loyaltyEventType;

    private final Collection<Rule> rules;

    public static Campaign valueOf(CampaignEntity campaign) {
        return new Campaign(campaign.id(), campaign.metaInfo(),
                campaign.interval(), campaign.state(),
                campaign.loyaltyEventType(), campaign.rules());
    }

}
