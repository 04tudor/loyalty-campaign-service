package md.maib.retail.infrastructure.persistence;

import md.maib.retail.model.effects.LoyaltyEffectType;
import md.maib.retail.model.ports.LoyaltyEffectTypes;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@Service
public class LoyaltyEffectTypesAdapter implements LoyaltyEffectTypes {

    private static final Map<String, LoyaltyEffectType> EFFECT_TYPES = Map.of(
            "4e1a8086-90de-4796-95e8-121f24412656", new LoyaltyEffectType(UUID.fromString("4e1a8086-90de-4796-95e8-121f24412656"), "add_points", UUID.fromString("b0ace6d7-4eee-4cf5-bedf-66de3aa170cd")),
            "beff13ab-c85f-49f6-9638-95fb578f2d74", new LoyaltyEffectType(UUID.fromString("beff13ab-c85f-49f6-9638-95fb578f2d74"), "cashback", UUID.fromString("dccc8d05-4f7c-4830-bceb-c26895918293")),
            "9f62131e-6c55-498d-b294-82766a2ef192", new LoyaltyEffectType(UUID.fromString("9f62131e-6c55-498d-b294-82766a2ef192"), "coupon", UUID.fromString("db92ed4e-7f8e-4f4c-b1a6-c6136c828ebb"))
    );

    @Override
    public Optional<LoyaltyEffectType> findById(String id) {
        return Optional.ofNullable(EFFECT_TYPES.get(id));
    }
}
