package md.maib.retail.model.conditions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.UUID;

import static java.util.UUID.randomUUID;

public class RuleId {
    private final UUID id;

    public RuleId(UUID id) {
        this.id = id;
    }

    public RuleId(String id) {
        this.id = UUID.fromString(id);
    }

    public static RuleId newIdentity() {
        return new RuleId(randomUUID());
    }

    public static RuleId valueOf(String id) {
        return new RuleId(UUID.fromString(id));
    }

    @JsonCreator
    public static RuleId fromString(String id) {
        return new RuleId(UUID.fromString(id));
    }

    public UUID getId() {
        return id;
    }

    @JsonValue
    public String toString() {
        return id.toString();
    }
}
