package md.maib.retail.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import md.maib.retail.infrastructure.persistence.json_converters.MetaInfoJsonConverter;
import md.maib.retail.model.campaign.*;
import md.maib.retail.model.conditions.Rule;
import org.hibernate.annotations.ColumnTransformer;
import org.springframework.data.domain.Persistable;
import org.threeten.extra.Interval;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "campaign", schema = "campaigns")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class CampaignRecord implements Persistable<UUID> {

        @Id
        @EqualsAndHashCode.Include
        @Column(name = "campaign_id", nullable = false)
        private UUID id;

        @Column(name = "metainfo", nullable = false)
        @Convert(converter = MetaInfoJsonConverter.class)
        @ColumnTransformer(write = "?::jsonb")
        private Map<String, Object> metaInfo;

        @Column(name = "interval_start", nullable = false)
        private Instant startInclusive;

        @Column(name = "interval_end", nullable = false)
        private Instant endExclusive;


        @Column(name = "is_active", nullable = false)
        private boolean isActive;

        @Column(name = "loyalty_event_type_id", nullable = false)
        private UUID loyaltyEventType;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "campaignId", cascade = CascadeType.ALL)
        private Set<RuleRecord> rules = new HashSet<>();

        @Transient
        private boolean isNew;

        public CampaignRecord(UUID id, Map<String, Object> metaInfo, Instant startInusive, Instant endExclusive, boolean isActive, UUID loyaltyEventTypeid) {

                this.id = id;
                this.metaInfo = metaInfo;
                this.startInclusive = startInusive;
                this.endExclusive = endExclusive;
                this.isActive = isActive;
                this.loyaltyEventType = loyaltyEventTypeid;
                isNew = true;
        }

        public static CampaignRecord valueOf(Campaign campaign) {

                boolean isActive = campaign.getState() == CampaignState.ACTIVE;
                CampaignRecord campaignRecord = new CampaignRecord(
                        campaign.getId().toUUID(),
                        campaign.getMetaInfo().properties(),
                        campaign.getActivityInterval().getStart(),
                        campaign.getActivityInterval().getEnd(),
                        isActive,
                        campaign.getLoyaltyEventType().getId()
                );

                campaign.getRules().forEach(rule -> campaignRecord.addRule(new RuleRecord(rule, campaignRecord)));

                return campaignRecord;
        }

        @Override
        public boolean isNew() {
                return isNew;
        }

        public void setNew(boolean isNew) {
                this.isNew = isNew;
        }

        public void addRule(RuleRecord r) {
                rules.add(r);
                r.setCampaignId(this);
        }

        public Campaign toCampaign(boolean withDetails) {
                Interval activityInterval = Interval.of(startInclusive, endExclusive);
                CampaignState state = isActive ? CampaignState.ACTIVE : CampaignState.DRAFT;
                Set<Rule> campaignRules = withDetails
                        ? rules.stream().map(RuleRecord::convertToRule).collect(Collectors.toSet())
                        : Collections.emptySet();
                return new Campaign(
                        CampaignId.valueOf(id),
                        CampaignMetaInfo.valueOf(metaInfo),
                        activityInterval,
                        state,
                        new LoyaltyEventType(loyaltyEventType.toString()),
                        campaignRules
                );
        }

        public boolean activate() {
                if (!isActive) {
                        this.isActive = true;
                        return true;
                }
                return false;
        }

}



