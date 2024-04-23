package md.maib.retail.model.effects;

import md.maib.retail.model.conditions.Condition;
import md.maib.retail.model.conditions.Operator;

public record LoyaltyEventTypeRecord (
        Condition condition,

        LoyaltyEventField field,

        Operator operator,

        String value

){
}
