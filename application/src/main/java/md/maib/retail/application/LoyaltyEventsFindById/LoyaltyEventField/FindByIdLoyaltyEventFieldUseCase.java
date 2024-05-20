package md.maib.retail.application.LoyaltyEventsFindById.LoyaltyEventField;

import md.maib.retail.model.campaign.LoyaltyEventField;
import md.maib.retail.model.ports.LoyaltyEventFields;

public interface FindByIdLoyaltyEventFieldUseCase {
    public static FindByIdLoyaltyEventFieldService defaultService(LoyaltyEventFields loyaltyEventFields) {
        return new FindByIdLoyaltyEventFieldService(loyaltyEventFields);
    }

    LoyaltyEventField findById(String id);
}
