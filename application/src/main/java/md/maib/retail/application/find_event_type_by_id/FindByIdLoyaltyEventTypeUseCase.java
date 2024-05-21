package md.maib.retail.application.find_event_type_by_id;

import md.maib.retail.model.campaign.LoyaltyEventType;

import java.util.Optional;

public interface FindByIdLoyaltyEventTypeUseCase {
    Optional<LoyaltyEventType> findById(String id);
}

