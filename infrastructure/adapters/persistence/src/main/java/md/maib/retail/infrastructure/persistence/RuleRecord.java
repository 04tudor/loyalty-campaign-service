package md.maib.retail.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import md.maib.retail.infrastructure.persistence.json_converters.ConditionJsonConverter;
import md.maib.retail.infrastructure.persistence.json_converters.EffectJsonConverter;
import md.maib.retail.model.conditions.Condition;
import md.maib.retail.model.effects.Effect;
import org.hibernate.annotations.ColumnTransformer;
import org.springframework.data.domain.Persistable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "rule", schema = "campaigns")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class RuleRecord implements Persistable<UUID> {
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "campaign_id", nullable = false)
    private CampaignRecord campaignId;

    @Column(name = "conditions", nullable = false, columnDefinition = "jsonb")
    @Convert(converter = ConditionJsonConverter.class)
    @ColumnTransformer(write = "?::jsonb")
    private Collection<Condition> conditions;

    @Column(name = "effects", nullable = false, columnDefinition = "jsonb")
    @Convert(converter = EffectJsonConverter.class)
    @ColumnTransformer(write = "?::jsonb")
    private List<Effect> effects;

    @Transient
    private boolean isNew;

    @Override
    public boolean isNew() {
        return isNew;
    }

}


