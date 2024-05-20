package md.maib.retail.model.ports;

import md.maib.retail.model.campaign.LoyaltyEventType;

public interface LoyaltyEventTypes {
    LoyaltyEventType findById(String id);
}
