package md.maib.retail.infrastructure.persistence.json_converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.SneakyThrows;

import java.util.Map;

@Converter(autoApply = true)
public class MetaInfoJsonConverter implements AttributeConverter<Map<String, Object>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public String convertToDatabaseColumn(Map<String, Object> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting MetaInfo to JSON", e);
        }
    }

    @Override
    @SneakyThrows
    public Map<String, Object> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<>() {
                    }
            );
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting JSON to MetaInfo", e);
        }
    }

}
