package md.maib.retail.model.ports;

import md.maib.retail.model.effects.LoyaltyEffectType;

import java.util.Optional;

public interface LoyaltyEffectTypes {
    Optional<LoyaltyEffectType> findById(String id);

}
