package md.maib.retail.model.ports;

import md.maib.retail.model.campaign.LoyaltyEventType;

import java.util.Optional;

public interface LoyaltyEventTypes {
    Optional<LoyaltyEventType> findById(String id);
}
