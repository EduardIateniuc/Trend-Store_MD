package md.donesk.trendshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductListResponse{
    private Long productId;
    private String productName;
    private BigDecimal price;
    private String images;
    private String subcategoryName;
    private String categoryName;
}