package md.maib.retail.model.conditions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import md.maib.retail.model.campaign.FieldType;
import md.maib.retail.model.campaign.LoyaltyEventField;

@Getter

@AllArgsConstructor
public class Condition  {
    private ConditionId conditionId;
    private FieldType field;
    private Operator operator;
    private String value;
}
