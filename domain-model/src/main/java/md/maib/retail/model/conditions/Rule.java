package md.maib.retail.model.conditions;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import md.maib.retail.model.effects.Effect;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Getter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded=true)
public final class Rule {
    @EqualsAndHashCode.Include
    private final UUID id;
    private Collection<Condition>conditions;
    private List<Effect>efects;

    public List<Effect> getEffects() {
        return efects;
    }
    public static UUID newIdentity() {
        return UUID.randomUUID();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return id.equals(rule.id) &&
                conditions.equals(rule.conditions) &&
                efects.equals(rule.efects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, conditions, efects);
    }

}
