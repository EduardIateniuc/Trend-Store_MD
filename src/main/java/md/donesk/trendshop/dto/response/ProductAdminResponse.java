package md.donesk.trendshop.dto.response;

import lombok.Data;
import md.donesk.trendshop.model.Product;

import java.util.stream.Collectors;

@Data
public class ProductAdminResponse extends ProductResponse {
    private String categoryName;
    private String subcategoryName;
    private String gender;

    public static ProductAdminResponse fromEntity(Product product) {
        ProductAdminResponse dto = new ProductAdminResponse();
        dto.setProductCode(product.getProductCode());
        dto.setProductName(product.getProductName());
        dto.setDescription(product.getDescription());
        dto.setSubcategoryName(product.getSubcategory().getSubcategoryName());
        dto.setCategoryName(product.getSubcategory().getCategory().getCategoryName());
        dto.setGender(product.getSubcategory().getCategory().getGender().name());
        dto.setPrice(product.getPrice());
        dto.setImages(product.getImages());
        dto.setVariants(product.getVariants().stream()
                .map(ProductVariantDTO::fromEntity)
                .collect(Collectors.toList()));
        return dto;
    }
}
