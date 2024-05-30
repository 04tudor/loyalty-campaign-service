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

@Entity
@Table(name = "campaign", schema = "campaigns")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class CampaignRecord implements Persistable<UUID>{

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


        @OneToMany
        @JoinColumn(name = "campaign_id")
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
                isNew=true;
        }



        @Override
        public boolean isNew() {
            return isNew;
        }

        static CampaignRecord valueOf(Campaign campaign) {
                var isActive = campaign.getState().equals(CampaignState.ACTIVE);
                return new CampaignRecord(
                        campaign.getId().toUUID(),
                        campaign.getMetaInfo().properties(),
                        campaign.getActivityInterval().getStart(),
                        campaign.getActivityInterval().getEnd(),
                        isActive,
                        campaign.getLoyaltyEventType().getId()

                );
        }

        public Campaign toCampaign() {
                return toCampaign(false);
        }

        public Campaign toCampaign(boolean withDetails) {
                Interval activityInterval = Interval.of(startInclusive, endExclusive);
                var state = isActive ? CampaignState.ACTIVE : CampaignState.DRAFT;
                var rr = withDetails ? rules.stream().map(RuleRecord::convertToRule).toList() : Collections.<Rule>emptyList();
                return new Campaign(
                        CampaignId.valueOf(id),
                        CampaignMetaInfo.valueOf(metaInfo),
                        activityInterval,
                        state,
                        new LoyaltyEventType(loyaltyEventType.toString()),
                        rr
                );
        }

    }

