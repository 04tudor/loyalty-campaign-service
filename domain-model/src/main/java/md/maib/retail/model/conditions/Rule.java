package md.maib.retail.model.conditions;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import md.maib.retail.model.effects.Effect;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
@Getter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded=true)
public final class Rule {
    @EqualsAndHashCode.Include
    private final UUID id;
    private Collection<Condition>conditions;
    private List<Effect>efects;
}
