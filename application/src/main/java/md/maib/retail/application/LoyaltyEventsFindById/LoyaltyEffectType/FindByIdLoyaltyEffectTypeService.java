package md.maib.retail.application.LoyaltyEventsFindById.LoyaltyEffectType;

import md.maib.retail.model.effects.LoyaltyEffectType;
import md.maib.retail.model.ports.LoyaltyEffectTypes;

import java.util.Objects;

public class FindByIdLoyaltyEffectTypeService implements FindByIdLoyaltyEffectTypeUseCase{
    private final LoyaltyEffectTypes loyaltyEffectTypes;

    public FindByIdLoyaltyEffectTypeService(LoyaltyEffectTypes loyaltyEffectTypes) {
        this.loyaltyEffectTypes = Objects.requireNonNull(loyaltyEffectTypes,"loyaltyEventTypes must not be null");
    }

    @Override
    public LoyaltyEffectType findById(String id) {
        return loyaltyEffectTypes.findById(id);

    }
}
