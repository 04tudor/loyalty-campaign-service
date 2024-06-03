package md.maib.retail.infrastructure.persistence.json_converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import md.maib.retail.model.conditions.Condition;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Converter(autoApply = true)
public class ConditionJsonConverter implements AttributeConverter<Collection<Condition>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Collection<Condition> conditions) {
        try {
            return objectMapper.writeValueAsString(conditions);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting conditions to JSON", e);
        }
    }

    @Override
    public Collection<Condition> convertToEntityAttribute(String json) {
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, Condition.class));
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to conditions", e);
        }
    }
}

