package md.donesk.trendshop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class SubCategoryRequest{

    @NotBlank(message = "Subcategory name is required")
    @Size(min = 2, max = 100, message = "Subcategory name must be between 2 and 100 characters")
    private String subcategoryName;

}
