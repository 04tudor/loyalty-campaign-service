package md.maib.retail.model.campaign;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class LoyaltyEventField {

    private UUID id;

    private String name;

    private FieldType fieldType;
}
