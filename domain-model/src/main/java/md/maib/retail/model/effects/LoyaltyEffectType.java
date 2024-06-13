package md.maib.retail.model.effects;

import java.util.UUID;

public record LoyaltyEffectType(
        UUID id,
        String name,
        UUID loyaltyEventTypeId
) {


}
