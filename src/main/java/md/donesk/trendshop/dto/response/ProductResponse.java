package md.donesk.trendshop.dto.response;

import lombok.Data;
import md.donesk.trendshop.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProductResponse {
    private String productCode;
    private String productName;
    private String description;
    private BigDecimal price;
    private List<String> images;
    private List<ProductVariantDTO> variants;

    // Add a static mapper method for convenience
    public static ProductResponse fromEntity(Product product) {
        ProductResponse dto = new ProductResponse();
        dto.setProductCode(product.getProductCode());
        dto.setProductName(product.getProductName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setImages(product.getImages());
        dto.setVariants(product.getVariants().stream()
                .map(ProductVariantDTO::fromEntity)
                .collect(Collectors.toList()));
        return dto;
    }
}