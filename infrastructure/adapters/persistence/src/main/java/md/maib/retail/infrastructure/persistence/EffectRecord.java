package md.maib.retail.infrastructure.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import md.maib.retail.model.effects.Effect;
import md.maib.retail.model.effects.LoyaltyEffectType;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class EffectRecord {
    private UUID loyaltyEventTypeId;
    private String value;

    public Effect toEffect(LoyaltyEffectTypesAdapter loyaltyEffectTypesAdapter) {
        LoyaltyEffectType loyaltyEffectType = loyaltyEffectTypesAdapter.findById(String.valueOf(loyaltyEventTypeId))
                .orElseThrow(() -> new IllegalArgumentException("LoyaltyEffectType not found"));
        return new Effect(loyaltyEffectType, value);
    }

    public static EffectRecord fromEffect(Effect effect) {
        return new EffectRecord(effect.effectType().id(), effect.value());
    }

    public static List<Effect> toEffects(List<EffectRecord> effectRecords, LoyaltyEffectTypesAdapter loyaltyEffectTypesAdapter) {
        return effectRecords.stream()
                .map(record -> record.toEffect(loyaltyEffectTypesAdapter))
                .toList();
    }
}
