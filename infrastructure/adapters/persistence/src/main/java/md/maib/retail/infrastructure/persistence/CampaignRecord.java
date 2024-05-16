package md.maib.retail.infrastructure.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import md.maib.retail.model.campaign.*;
import md.maib.retail.model.conditions.Rule;
import org.springframework.data.domain.Persistable;
import org.threeten.extra.Interval;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Collection;

@Entity
@Table(name = "campaigns")
@NoArgsConstructor
@Getter
public final class CampaignRecord implements Persistable<CampaignId> {
    @Id
    private  CampaignId id;

    private  CampaignMetaInfo metaInfo;

    private  Interval activityInterval;

    private  CampaignState state;

    private  LoyaltyEventType loyaltyEventType;

    private  Collection<Rule> rules;
    @Transient
    boolean isNew;

    public CampaignRecord(CampaignId id,CampaignMetaInfo metaInfo,Interval interval,CampaignState state,LoyaltyEventType loyaltyEventType,Collection<Rule> rules){
        this.id=id;
        this.metaInfo=metaInfo;
        this.activityInterval=interval;
        this.state=state;
        this.loyaltyEventType=loyaltyEventType;
        this.rules=rules;
        isNew = true;

    }

    public static CampaignRecord valueOf(Campaign campaign) {
        return new CampaignRecord(
                campaign.getId(),
                campaign.getMetaInfo(),
                campaign.getActivityInterval(),
                campaign.getState(),
                campaign.getLoyaltyEventType(),
                campaign.getRules()
        );
    }
    public Campaign toCampaign() {
        return new Campaign(
                CampaignId.valueOf(id.campaignId()),
                CampaignMetaInfo.valueOf(metaInfo.properties()),
                activityInterval,
                state,
                loyaltyEventType,
                rules
        );
    }
    @Override
    public CampaignId getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
