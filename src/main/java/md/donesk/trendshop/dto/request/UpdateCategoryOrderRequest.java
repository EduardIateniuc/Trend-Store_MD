package md.donesk.trendshop.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class UpdateCategoryOrderRequest {
private List<CategoryOrderItem> orders;

    @Data
    public static class CategoryOrderItem {
        private Integer categoryId;
        private Integer displayOrder;
    }
}
