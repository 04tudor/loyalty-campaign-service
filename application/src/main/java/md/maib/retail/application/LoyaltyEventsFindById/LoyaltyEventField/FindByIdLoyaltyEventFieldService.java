package md.maib.retail.application.LoyaltyEventsFindById.LoyaltyEventField;


import md.maib.retail.model.campaign.LoyaltyEventField;
import md.maib.retail.model.ports.LoyaltyEventFields;

import java.util.Objects;

public class FindByIdLoyaltyEventFieldService implements FindByIdLoyaltyEventFieldUseCase{
    private final LoyaltyEventFields loyaltyEventFields;

    public FindByIdLoyaltyEventFieldService(LoyaltyEventFields loyaltyEventFields) {
        this.loyaltyEventFields = Objects.requireNonNull(loyaltyEventFields,"loyaltyEventFields must not be null");
    }


    @Override
    public LoyaltyEventField findById(String id) {
            return loyaltyEventFields.findById(id);
    }
}
