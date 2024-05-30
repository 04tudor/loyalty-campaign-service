package md.maib.retail.infrastructure.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import md.maib.retail.model.effects.Effect;
import md.maib.retail.model.effects.LoyaltyEffectType;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class EffectRecord {
    private UUID loyaltyEventTypeId;
    private String value;
    private final LoyaltyEffectTypesAdapter loyaltyEffectTypesAdapter;
    public Effect toEffect() {
        LoyaltyEffectType loyaltyEffectType = loyaltyEffectTypesAdapter.findById(String.valueOf(loyaltyEventTypeId))
                .orElseThrow(() -> new IllegalArgumentException("LoyaltyEffectType not found"));
        return new Effect(loyaltyEffectType, value);
    }

    public static EffectRecord fromEffect(Effect effect) {
        return new EffectRecord(effect.effectType().id(), effect.value(), null); // Null as we're not using it here
    }

    public static List<Effect> toEffects(List<EffectRecord> effectRecords) {
        return effectRecords.stream()
                .map(EffectRecord::toEffect)
                .toList();
    }
}
