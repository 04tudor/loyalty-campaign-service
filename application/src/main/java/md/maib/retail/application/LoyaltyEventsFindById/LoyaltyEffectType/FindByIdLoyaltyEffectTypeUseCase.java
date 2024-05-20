package md.maib.retail.application.LoyaltyEventsFindById.LoyaltyEffectType;

import md.maib.retail.model.effects.LoyaltyEffectType;
import md.maib.retail.model.ports.LoyaltyEffectTypes;

public interface FindByIdLoyaltyEffectTypeUseCase {
    public static FindByIdLoyaltyEffectTypeService defaultService(LoyaltyEffectTypes loyaltyEffectTypes) {
        return new FindByIdLoyaltyEffectTypeService(loyaltyEffectTypes);
    }

    LoyaltyEffectType findById(String id);
}
