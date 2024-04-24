package md.maib.retail.model.effects;

import md.maib.retail.model.campaign.LoyaltyEventType;

import java.util.UUID;

public record LoyaltyEffectType(
        UUID id,
        String name,
        LoyaltyEventType type

){


}
