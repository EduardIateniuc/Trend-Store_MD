package md.donesk.trendshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import md.donesk.trendshop.model.ProductVariant;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantDTO {
    private Long id;
    private String size;
    private String color;
    private Integer stockLevel;

    public static ProductVariantDTO fromEntity(ProductVariant variant) {
        return ProductVariantDTO.builder()
                .id(variant.getId())
                .size(variant.getSize())
                .color(variant.getColor())
                .stockLevel(variant.getStockLevel())
                .build();
    }
}