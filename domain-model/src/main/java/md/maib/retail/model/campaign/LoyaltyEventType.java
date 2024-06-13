package md.maib.retail.model.campaign;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoyaltyEventType {
    private UUID id;

    private String name;

    private List<LoyaltyEventField> fields;


    public LoyaltyEventType(String id) {
        this.id = UUID.fromString(id);
    }
}

