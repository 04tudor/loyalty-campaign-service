package md.maib.retail.persistence;

import md.maib.retail.model.campaign.LoyaltyEventType;
import md.maib.retail.model.ports.LoyaltyEventTypes;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service

public class LoyaltyEventTypesAdapter implements LoyaltyEventTypes {
    private static final Map<String, LoyaltyEventType> EVENT_TYPES = Map.of(
            "b0ace6d7-4eee-4cf5-bedf-66de3aa170cd", new LoyaltyEventType(UUID.fromString("b0ace6d7-4eee-4cf5-bedf-66de3aa170cd"), "Event_Type", null));

    @Override
    public Optional<LoyaltyEventType> findById(String id) {
        return Optional.ofNullable(EVENT_TYPES.get(id));
    }
}
