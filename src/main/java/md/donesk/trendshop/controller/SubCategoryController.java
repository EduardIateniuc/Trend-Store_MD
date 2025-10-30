package md.donesk.trendshop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import md.donesk.trendshop.dto.request.SubCategoryRequest;
import md.donesk.trendshop.dto.response.SubCategoryResponse;
import md.donesk.trendshop.service.SubCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trend/categories/{categoryId}/subcategories")
@RequiredArgsConstructor
@Validated
public class SubCategoryController {
    private final SubCategoryService subcategoryService;

    @PostMapping
    public ResponseEntity<SubCategoryResponse> createSubcategory(@PathVariable("categoryId") Integer categoryId, @Valid @RequestBody SubCategoryRequest requestDto) {
        SubCategoryResponse subcategoryResponse = subcategoryService.createSubcategory(categoryId, requestDto);
        return ResponseEntity.ok(subcategoryResponse);
    }

    @GetMapping
    public ResponseEntity<List<SubCategoryResponse>> getAllSubcategories(@PathVariable Integer categoryId) {
        List<SubCategoryResponse> subcategories = subcategoryService.getAllSubcategories(categoryId);
        return ResponseEntity.ok(subcategories);
    }

    @GetMapping("/{subcategoryId}")
    public ResponseEntity<SubCategoryResponse> getSubcategoryById(@PathVariable Integer subcategoryId) {
        SubCategoryResponse subcategoryResponse = subcategoryService.getSubcategoryById(subcategoryId);
        return ResponseEntity.ok(subcategoryResponse);
    }

    @PutMapping("/{subcategoryId}")
    public ResponseEntity<SubCategoryResponse> updateSubcategory(
            @PathVariable("categoryId") Integer categoryId,
            @PathVariable Integer subcategoryId,
            @Valid @RequestBody SubCategoryRequest requestDto) {
        SubCategoryResponse subcategoryResponse = subcategoryService.updateSubcategory(categoryId, subcategoryId, requestDto);
        return ResponseEntity.ok(subcategoryResponse);
    }

    @DeleteMapping("/{subcategoryId}")
    public ResponseEntity<Void> deleteSubcategory(@PathVariable Integer subcategoryId) {
        subcategoryService.deleteSubcategory(subcategoryId);
        return ResponseEntity.noContent().build();
    }
}
