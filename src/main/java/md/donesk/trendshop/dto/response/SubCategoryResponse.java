package md.donesk.trendshop.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubCategoryResponse {
    private Integer subcategoryId;
    private String subcategoryName;
    private Integer categoryId;  // To show which category the subcategory belongs to
}
