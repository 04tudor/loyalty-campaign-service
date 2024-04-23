package md.maib.retail.Model.Effects;

import md.maib.retail.Model.Conditions.Condition;
import md.maib.retail.Model.Conditions.Operator;

public record LoyaltyEventTypeRecord (
        Condition condition,

        LoyaltyEventField field,

        Operator operator,

        String value

){
}
