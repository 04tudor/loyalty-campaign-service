package md.maib.retail.model.conditions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import md.maib.retail.model.campaign.FieldType;

@Getter

@AllArgsConstructor
@NoArgsConstructor
public class Condition {
    private FieldType field;
    private Operator operator;
    private String value;
}
