package md.donesk.trendshop.dto.response;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import md.donesk.trendshop.model.Product;

@Data
@Builder
public class InventoryResponse {
    private Long inventoryId;
    private Long productId;
    private Long sizeId;
    private String sizeValue;
    private Integer colorId;
    private String colorValue;
    private Integer stockLevel;
}