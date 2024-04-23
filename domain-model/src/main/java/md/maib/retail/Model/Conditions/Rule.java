package md.maib.retail.Model.Conditions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import md.maib.retail.Model.Effects.Effect;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rule {
    private UUID id;
    private Collection<Condition>conditions;
    private List<Effect>efects;
}
