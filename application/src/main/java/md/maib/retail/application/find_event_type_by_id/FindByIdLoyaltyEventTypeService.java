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
        return loyaltyEventTypes.findById(id);
    }
}
