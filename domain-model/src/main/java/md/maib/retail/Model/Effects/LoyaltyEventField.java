package md.maib.retail.Model.Effects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoyaltyEventField {

    private UUID id;

    private String name;

    private FieldType fieldType;
}
