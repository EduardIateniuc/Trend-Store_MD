package md.donesk.trendshop.dto.request;

import lombok.Data;
import md.donesk.trendshop.model.Gender;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProductCsvDTO {
    private String productCode;
    private String productName;
    private String description;
    private String gender;
    private String categoryName;     // Add this field
    private String subcategoryName;  // can be null
    private BigDecimal price;
    private String imageUrl;// can be null



    public Gender getGenderEnum() {
        return Gender.valueOf(gender);
    }
    
    public List<String> parseImageUrls() {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            return new ArrayList<>();
        }
        // First, remove any enclosing quotes
        String cleaned = imageUrl.replaceAll("^\"|\"$", "").trim();

        // Split by pipe and process each URL
        return Arrays.stream(cleaned.split("\\|"))
                .map(String::trim)                // Remove whitespace
                .filter(s -> !s.isEmpty())        // Remove empty strings
                .collect(Collectors.toList());
    }
}