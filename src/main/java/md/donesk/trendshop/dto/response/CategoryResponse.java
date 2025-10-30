package md.donesk.trendshop.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class CategoryResponse {

    private Integer categoryId;
    private String categoryName;
    private String gender;
    private String imageUrl;
    private List<SubCategoryResponse> subcategories;
}
