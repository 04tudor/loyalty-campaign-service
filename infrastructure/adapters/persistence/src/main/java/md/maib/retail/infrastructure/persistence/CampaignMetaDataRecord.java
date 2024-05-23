package md.maib.retail.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import md.maib.retail.model.campaign.*;
import md.maib.retail.model.conditions.Rule;
import org.hibernate.annotations.ColumnTransformer;
import org.springframework.data.domain.Persistable;
import org.threeten.extra.Interval;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
@Entity
@Table(name = "campaign_metadata", schema = "campaigns")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class CampaignMetaDataRecord implements Persistable<UUID>{

        @Id
        @EqualsAndHashCode.Include
        @Column(name = "campaign_id", nullable = false)
        private UUID id;

        @Column(name = "metainfo", nullable = false)
        @Convert(converter = EntityFieldJsonConverter.class)
        @ColumnTransformer(write = "?::jsonb")
        private Map<String, Object> metaInfo;

        @Column(name = "interval_start", nullable = false)
        private LocalDate startInclusive;

        @Column(name = "interval_end", nullable = false)
        private LocalDate endExclusive;

        @Column(name = "is_active", nullable = false)
        private boolean isActive;

        @Column(name = "loyalty_event_type_id", nullable = false)
        private UUID loyaltyEventTypeid;

        @Column(name = "metadata", nullable = false)
        @ColumnTransformer(write = "?::jsonb")
        private Collection<Rule> rules;

        @Transient
        private boolean isNew;

        public CampaignMetaDataRecord(UUID id, Map<String, Object> metaInfo, LocalDate startInusive, LocalDate endExclusive, boolean isActive, UUID loyaltyEventTypeid, Collection<Rule> rules) {
                this.id = id;
                this.metaInfo = metaInfo;
                this.startInclusive = startInusive;
                this.endExclusive = endExclusive;
                this.isActive = isActive;
                this.loyaltyEventTypeid = loyaltyEventTypeid;
                this.rules = rules;
        }

        @Override
        public boolean isNew() {
            return isNew;
        }

        static CampaignMetaDataRecord valueOf(Campaign campaign) {
                var isactive=false;
                if (campaign.getState().equals(CampaignState.ACTIVE))isactive=true;
                return new CampaignMetaDataRecord(
                        campaign.getId().toUUID(),
                        campaign.getMetaInfo().properties(),
                        campaign.getActivityInterval().getStart().atZone(ZoneOffset.UTC).toLocalDate(),
                        campaign.getActivityInterval().getEnd().atZone(ZoneOffset.UTC).toLocalDate(),
                        isactive,
                        campaign.getLoyaltyEventType().getId(),
                        campaign.getRules()
                );
        }

        public Campaign toCampaign() {
                Interval activityInterval = Interval.of(
                        startInclusive.atStartOfDay().toInstant(ZoneOffset.UTC),
                        endExclusive.atStartOfDay().toInstant(ZoneOffset.UTC)
                );
                var state=CampaignState.DRAFT;
                if (isActive)state=CampaignState.ACTIVE;
                return new Campaign(
                        CampaignId.valueOf(id),
                        CampaignMetaInfo.valueOf(metaInfo),
                        activityInterval,
                        state,
                        new LoyaltyEventType(loyaltyEventTypeid.toString()),
                        rules
                );
        }
    }

