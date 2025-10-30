package md.donesk.trendshop.model;

// StringListConverter.java
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
    // Create a logger for error tracking and debugging
    private static final Logger logger = LoggerFactory.getLogger(StringListConverter.class);

    // Create a single ObjectMapper instance for better performance
    // ObjectMapper is thread-safe and can be reused
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        // Handle null input by returning null
        if (attribute == null) {
            return null;
        }

        try {
            // Convert the List<String> to a JSON string
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            // Log the error for debugging purposes
            logger.error("Error converting List<String> to JSON string", e);
            // Return empty array as JSON string in case of error
            return "[]";
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        // Handle null or empty database values
        if (dbData == null || dbData.trim().isEmpty()) {
            // Return empty list instead of null for safer handling
            return new ArrayList<>();
        }

        try {
            // Convert the JSON string back to List<String>
            return objectMapper.readValue(dbData, new TypeReference<List<String>>() {});
        } catch (IOException e) {
            // Log the error for debugging purposes
            logger.error("Error converting JSON string to List<String>", e);
            // Return empty list in case of error
            return new ArrayList<>();
        }
    }
}