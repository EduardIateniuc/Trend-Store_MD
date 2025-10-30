package md.donesk.trendshop.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductRequest {
    @NotBlank(message = "Product name is required")
    @Size(max = 255, message = "Product name must not exceed 255 characters")
    private String productName;

    @NotBlank(message = "Product Code is required")
    @Size(max = 255, message = "Product Code must not exceed 255 characters")
    private String productCode;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price should be greater than zero")
    private BigDecimal price;

    @NotNull(message = "Subcategory ID is required")
    private Integer subcategoryId;

    private List<String> images;

    private List<ProductVariantRequest> variants;

    @Data
    public static class ProductVariantRequest {
        @NotBlank(message = "Size is required")
        @Size(max = 20, message = "Size must not exceed 20 characters")
        private String size;

        @NotBlank(message = "Color is required")
        @Size(max = 50, message = "Color must not exceed 50 characters")
        private String color;

        @Min(value = 0, message = "Stock level cannot be negative")
        private Integer stockLevel;
    }
}