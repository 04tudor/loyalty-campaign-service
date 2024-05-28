package md.maib.retail.infrastructure.persistence;

import lombok.AllArgsConstructor;
import md.maib.retail.model.effects.Effect;
import md.maib.retail.model.effects.LoyaltyEffectType;

import java.util.UUID;
@AllArgsConstructor
public class EffectRecord {
    private UUID loyaltyEventTypeId;

    private String value;

    public static Effect toEffect(EffectRecord effectRecord){
        LoyaltyEffectTypesAdapter loyaltyEffectTypesAdapter=new LoyaltyEffectTypesAdapter();
        LoyaltyEffectType loyaltyEffectType=loyaltyEffectTypesAdapter.findById(String.valueOf(effectRecord.loyaltyEventTypeId)).get();
        return new Effect(loyaltyEffectType,effectRecord.value);
    }
}
