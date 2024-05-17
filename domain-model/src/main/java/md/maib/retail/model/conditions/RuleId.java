package md.maib.retail.model.conditions;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.UUID;

import static java.util.Objects.requireNonNull;
import static java.util.UUID.randomUUID;

public record RuleId (UUID campaignId){

    public RuleId {
        requireNonNull(campaignId, "Rule Id must not be null.");
    }

    public static RuleId newIdentity() {
        return new RuleId(randomUUID());
    }

    public static RuleId valueOf(UUID value) {
        return new RuleId(value);
    }

    @JsonValue
    public String getCampaignId() {
        return campaignId.toString();
    }
}
