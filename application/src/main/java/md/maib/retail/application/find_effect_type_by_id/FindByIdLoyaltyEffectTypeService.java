package md.maib.retail.application.find_effect_type_by_id;

import md.maib.retail.model.effects.LoyaltyEffectType;
import md.maib.retail.model.ports.LoyaltyEffectTypes;

import java.util.Optional;

public class FindByIdLoyaltyEffectTypeService implements FindByIdLoyaltyEffectTypeUseCase {
    private final LoyaltyEffectTypes loyaltyEffectTypes;

    public FindByIdLoyaltyEffectTypeService(LoyaltyEffectTypes loyaltyEffectTypes) {
        this.loyaltyEffectTypes = loyaltyEffectTypes;
    }

    @Override
    public Optional<LoyaltyEffectType> findById(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return loyaltyEffectTypes.findById(id);
    }

    @Override
    public Optional<LoyaltyEffectType> retrieveLoyaltyEffectType(EffectTypeRecord effectTypeRecord) {
        if (effectTypeRecord == null || effectTypeRecord.id() == null) {
            return Optional.empty();
        }
        return loyaltyEffectTypes.findById(effectTypeRecord.id());
    }
}
