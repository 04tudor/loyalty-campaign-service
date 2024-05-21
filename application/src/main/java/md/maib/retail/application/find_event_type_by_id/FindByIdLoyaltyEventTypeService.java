package md.maib.retail.application.find_event_type_by_id;

import md.maib.retail.model.campaign.LoyaltyEventType;
import md.maib.retail.model.ports.LoyaltyEventTypes;

import java.util.Optional;

public class FindByIdLoyaltyEventTypeService implements FindByIdLoyaltyEventTypeUseCase {
    private final LoyaltyEventTypes loyaltyEventTypes;

    public FindByIdLoyaltyEventTypeService(LoyaltyEventTypes loyaltyEventTypes) {
        this.loyaltyEventTypes = loyaltyEventTypes;
    }

    @Override
    public Optional<LoyaltyEventType> findById(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return loyaltyEventTypes.findById(id);
    }

    @Override
    public Optional<LoyaltyEventType> retrieveLoyaltyEventType(EventTypeRecord eventTypeRecord) {
        if (eventTypeRecord == null || eventTypeRecord.id() == null) {
            return Optional.empty();
        }
        return loyaltyEventTypes.findById(eventTypeRecord.id());
    }
}
