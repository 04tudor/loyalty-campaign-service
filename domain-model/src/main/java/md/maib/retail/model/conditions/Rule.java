package md.maib.retail.model.conditions;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import md.maib.retail.model.effects.Effect;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded=true)
public final class Rule {
    @EqualsAndHashCode.Include
    private final RuleId id;
    private Collection<Condition>conditions;
    @Getter
    private List<Effect> effects;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return id.equals(rule.id) &&
                conditions.equals(rule.conditions) &&
                effects.equals(rule.effects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, conditions, effects);
    }

}
