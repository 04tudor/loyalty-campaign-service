package md.maib.retail.Model.Conditions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import md.maib.retail.Model.Effects.LoyaltyEventField;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Condition  {
    private LoyaltyEventField field;
    private Operator operator;
    private String value;
}
