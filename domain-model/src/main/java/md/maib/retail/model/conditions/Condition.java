package md.maib.retail.model.conditions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import md.maib.retail.model.campaign.LoyaltyEventField;

@Getter

@AllArgsConstructor
public class Condition  {
    private LoyaltyEventField field;
    private Operator operator;
    private String value;
}
