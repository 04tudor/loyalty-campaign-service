package md.maib.retail.application.find_effect_type_by_id;

import md.maib.retail.model.effects.LoyaltyEffectType;
import md.maib.retail.model.ports.LoyaltyEffectTypes;

import java.util.Optional;

public interface FindByIdLoyaltyEffectTypeUseCase {
    public static FindByIdLoyaltyEffectTypeService defaultService(LoyaltyEffectTypes loyaltyEffectTypes) {
        return new FindByIdLoyaltyEffectTypeService(loyaltyEffectTypes);
    }
    Optional<LoyaltyEffectType> findById(String id);

    Optional<LoyaltyEffectType> retrieveLoyaltyEffectType(EffectTypeRecord effectTypeRecord);
}
