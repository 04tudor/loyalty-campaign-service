package md.maib.retail.application.LoyaltyEventsFindById.LoyaltyEventType;

import md.maib.retail.model.campaign.LoyaltyEventType;
import md.maib.retail.model.ports.LoyaltyEventTypes;

import java.util.Objects;

public class FindByIdLoyaltyEventTypeService implements FindByIdLoyaltyEventTypeUseCase{
    private final LoyaltyEventTypes loyaltyEventTypes;

    public FindByIdLoyaltyEventTypeService(LoyaltyEventTypes loyaltyEventTypes) {
        this.loyaltyEventTypes = Objects.requireNonNull(loyaltyEventTypes,"loyaltyEventTypes must not be null");
    }

    @Override
    public LoyaltyEventType findById(String id) {
            return loyaltyEventTypes.findById(id);

    }
}
