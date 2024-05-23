package md.maib.retail.infrastructure.persistence;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import md.maib.retail.model.campaign.*;
import md.maib.retail.model.conditions.Rule;
import org.springframework.data.domain.Persistable;
import org.threeten.extra.Interval;

import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "campaign_metadata", schema = "campaigns")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
final class CampaignRecord implements Persistable<UUID> {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    private CampaignMetaInfo metaInfo;

    private Interval activityInterval;

    private CampaignState state;

    private LoyaltyEventType loyaltyEventType;

    private Collection<Rule> rules;

    @Transient
    private boolean isNew;

    public CampaignRecord(UUID campaignId, CampaignMetaInfo metaInfo, Interval activityInterval, CampaignState state, LoyaltyEventType loyaltyEventType, Collection<Rule> rules) {
        this.id = campaignId;
        this.metaInfo = metaInfo;
        this.activityInterval = activityInterval;
        this.state = state;
        this.loyaltyEventType = loyaltyEventType;
        this.rules = rules;
        isNew = true;
    }

    static CampaignRecord valueOf(Campaign campaign) {
        return new CampaignRecord(
                campaign.getId().toUUID(),
                campaign.getMetaInfo(),
                campaign.getActivityInterval(),
                campaign.getState(),
                campaign.getLoyaltyEventType(),
                campaign.getRules()
        );
    }

    public Campaign toCampaign() {
        return new Campaign(
              CampaignId.valueOf(id),
              CampaignMetaInfo.valueOf(metaInfo.properties()),
              activityInterval,
              state,
              loyaltyEventType,
              rules
        );
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}