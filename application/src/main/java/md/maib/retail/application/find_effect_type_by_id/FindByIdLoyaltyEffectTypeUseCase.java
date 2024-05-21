package md.maib.retail.application.find_effect_type_by_id;

import md.maib.retail.model.effects.LoyaltyEffectType;
import md.maib.retail.model.ports.LoyaltyEffectTypes;

import java.util.Optional;

public interface FindByIdLoyaltyEffectTypeUseCase {
    Optional<LoyaltyEffectType> findById(String id);
}
