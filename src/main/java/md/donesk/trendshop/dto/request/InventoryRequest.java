package md.donesk.trendshop.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventoryRequest {

    @NotNull(message = "Stock level cannot be lower than")
    private Integer stockLevel;

}
