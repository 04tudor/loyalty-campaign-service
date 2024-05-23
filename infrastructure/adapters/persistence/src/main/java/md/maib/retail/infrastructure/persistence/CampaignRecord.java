package md.maib.retail.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import md.maib.retail.infrastructure.persistence.json_converters.MetaInfoJsonConverter;
import md.maib.retail.model.campaign.*;
import org.hibernate.annotations.ColumnTransformer;
import org.springframework.data.domain.Persistable;
import org.threeten.extra.Interval;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;

@Entity
@Table(name = "campaign_metadata", schema = "campaigns")
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
        private LocalDate startInclusive;

        @Column(name = "interval_end", nullable = false)
        private LocalDate endExclusive;

        @Column(name = "is_active", nullable = false)
        private boolean isActive;

        @ManyToOne
        @JoinColumn(name = "loyalty_event_type_id", nullable = false)
        private LoyaltyEventType loyaltyEventType;

        @OneToMany
        @JoinColumn(name = "campaign_id")
        private Set<RuleRecord> rules = new HashSet<>();

        @Transient
        private boolean isNew;

        public CampaignRecord(UUID id, Map<String, Object> metaInfo, LocalDate startInusive, LocalDate endExclusive, boolean isActive, LoyaltyEventType loyaltyEventTypeid) {
                this.id = id;
                this.metaInfo = metaInfo;
                this.startInclusive = startInusive;
                this.endExclusive = endExclusive;
                this.isActive = isActive;
                this.loyaltyEventType = loyaltyEventTypeid;
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
                        campaign.getActivityInterval().getStart().atZone(ZoneOffset.UTC).toLocalDate(),
                        campaign.getActivityInterval().getEnd().atZone(ZoneOffset.UTC).toLocalDate(),
                        isActive,
                        campaign.getLoyaltyEventType()
                );
        }

        public Campaign toCampaign() {
                Interval activityInterval = Interval.of(
                        startInclusive.atStartOfDay().toInstant(ZoneOffset.UTC),
                        endExclusive.atStartOfDay().toInstant(ZoneOffset.UTC)
                );
                var state = isActive ? CampaignState.ACTIVE : CampaignState.DRAFT;
                return new Campaign(
                        CampaignId.valueOf(id),
                        CampaignMetaInfo.valueOf(metaInfo),
                        activityInterval,
                        state,
                        loyaltyEventType,
                        Collections.emptyList()
                );
        }
    }

