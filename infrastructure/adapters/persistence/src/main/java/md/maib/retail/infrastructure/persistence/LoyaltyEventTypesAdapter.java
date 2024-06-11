package md.maib.retail.infrastructure.persistence;

import md.maib.retail.model.campaign.FieldType;
import md.maib.retail.model.campaign.LoyaltyEventField;
import md.maib.retail.model.campaign.LoyaltyEventType;
import md.maib.retail.model.ports.LoyaltyEventTypes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.UUID.fromString;

@Service
public class LoyaltyEventTypesAdapter implements LoyaltyEventTypes {
    private static final Map<String, LoyaltyEventType> EVENT_TYPES = Map.of(
            "b0ace6d7-4eee-4cf5-bedf-66de3aa170cd",
            new LoyaltyEventType(fromString("b0ace6d7-4eee-4cf5-bedf-66de3aa170cd"), "Transaction", List.of(
                    new LoyaltyEventField(fromString("745b7917-d69c-4eed-9c3c-d4d835efa9d8"), "cardProduct", FieldType.STRING),
                    new LoyaltyEventField(fromString("ff6dd5ea-cc38-40fd-9ff3-b4040a34ed68"), "amountInMdl", FieldType.DECIMAL),
                    new LoyaltyEventField(fromString("9f6f5d8e-4b21-4be8-a8e4-b16dda248000"), "paymentCountry", FieldType.STRING),
                    new LoyaltyEventField(fromString("e9e2b0fa-6f38-4fe8-8b90-9b4347b037d5"), "retrievalRefNr", FieldType.STRING),
                    new LoyaltyEventField(fromString("3f83b40a-d404-45b6-b8db-dffb8a20b0f4"), "terminalId", FieldType.STRING)
            )));

    @Override
    public Optional<LoyaltyEventType> findById(String id) {
        return Optional.ofNullable(EVENT_TYPES.get(id));
    }
}
