package md.maib.retail.application.find_event_type_by_id;

import md.maib.retail.model.campaign.LoyaltyEventType;
import md.maib.retail.model.ports.LoyaltyEventTypes;

import java.util.Optional;

public interface FindByIdLoyaltyEventTypeUseCase {

    public static FindByIdLoyaltyEventTypeService defaultService(LoyaltyEventTypes loyaltyEventTypes) {
        return new FindByIdLoyaltyEventTypeService(loyaltyEventTypes);
    }
    Optional<LoyaltyEventType> findById(String id);
    Optional<LoyaltyEventType> retrieveLoyaltyEventType(EventTypeRecord eventTypeRecord);

}

