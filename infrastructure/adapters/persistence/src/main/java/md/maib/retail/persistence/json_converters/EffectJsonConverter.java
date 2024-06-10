package md.maib.retail.persistence.json_converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import md.maib.retail.persistence.EffectRecord;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Converter(autoApply = true)
public class EffectJsonConverter implements AttributeConverter<Collection<EffectRecord>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Collection<EffectRecord> effects) {
        try {
            return objectMapper.writeValueAsString(effects);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting effects to JSON", e);
        }
    }

    @Override
    public Collection<EffectRecord> convertToEntityAttribute(String json) {
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, EffectRecord.class));
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to effects", e);
        }
    }
}

