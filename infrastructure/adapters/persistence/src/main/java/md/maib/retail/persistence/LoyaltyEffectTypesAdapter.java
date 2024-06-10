package md.maib.retail.persistence;

import md.maib.retail.model.effects.LoyaltyEffectType;
import md.maib.retail.model.ports.LoyaltyEffectTypes;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@Service
public class LoyaltyEffectTypesAdapter implements LoyaltyEffectTypes {

    private static final Map<String, LoyaltyEffectType> EFFECT_TYPES = Map.of(
            "4e1a8086-90de-4796-95e8-121f24412656", new LoyaltyEffectType(UUID.fromString("4e1a8086-90de-4796-95e8-121f24412656"), "add_points", null));

    @Override
    public Optional<LoyaltyEffectType> findById(String id) {
        return Optional.ofNullable(EFFECT_TYPES.get(id));
    }
}
