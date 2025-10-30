package md.donesk.trendshop.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class OrderItemRequest {
    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Size ID is required")
    private Long sizeId;

    @NotNull(message = "Color ID is required")
    
    private Integer colorId;
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}
