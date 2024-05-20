package md.maib.retail.application.LoyaltyEventsFindById.LoyaltyEventType;

import md.maib.retail.model.campaign.LoyaltyEventType;
import md.maib.retail.model.ports.LoyaltyEventTypes;

public interface FindByIdLoyaltyEventTypeUseCase {
    public static FindByIdLoyaltyEventTypeService defaultService(LoyaltyEventTypes loyaltyEventTypes) {
        return new FindByIdLoyaltyEventTypeService(loyaltyEventTypes);
    }

    LoyaltyEventType findById(String id);
}
