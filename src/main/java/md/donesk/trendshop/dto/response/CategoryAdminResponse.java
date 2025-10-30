package md.donesk.trendshop.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryAdminResponse {
    private Integer categoryId;
    private String categoryName;
    private String gender;
    private String imageUrl;
    private Integer displayOrder;
    private Boolean visible;
    private List<SubCategoryResponse> subcategories;
}
