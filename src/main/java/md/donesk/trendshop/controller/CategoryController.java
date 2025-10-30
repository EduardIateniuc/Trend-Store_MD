package md.donesk.trendshop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import md.donesk.trendshop.dto.request.CategoryRequest;
import md.donesk.trendshop.dto.request.UpdateCategoryOrderRequest;
import md.donesk.trendshop.dto.request.UpdateCategoryVisibilityRequest;
import md.donesk.trendshop.dto.response.CategoryAdminResponse;
import md.donesk.trendshop.dto.response.CategoryResponse;
import md.donesk.trendshop.repository.SubCategoryRepository;
import md.donesk.trendshop.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/trend/categories")
@RequiredArgsConstructor
@Validated
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest requestDto) {
        CategoryResponse categoryResponse = categoryService.createCategory(requestDto);
        return ResponseEntity.ok(categoryResponse);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Integer categoryId) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(categoryResponse);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Integer categoryId,
            @Valid @RequestBody CategoryRequest requestDto) {
        CategoryResponse categoryResponse = categoryService.updateCategory(categoryId, requestDto);
        return ResponseEntity.ok(categoryResponse);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/genders/{gender}")
    public ResponseEntity<List<CategoryResponse>> getCategoriesByGender(@PathVariable("gender") String gender) {

        List<CategoryResponse> categories = categoryService.getCategoriesByGender(gender);
        return ResponseEntity.ok(categories);
    }


    @PostMapping("/upload")
    public ResponseEntity<String> uploadCategoriesWithSubcategories(@RequestParam("category_file") MultipartFile categoryFile, @RequestParam("subcategory_file") MultipartFile subcategoryFile) {
        try {
            String result = categoryService.importFromCSV(categoryFile, subcategoryFile);
            if (result.startsWith("Error") || result.startsWith("Failed")) {
                return ResponseEntity.badRequest().body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing file: " + e.getMessage());
        }
    }

    @GetMapping("/visible")
    public ResponseEntity<List<CategoryAdminResponse>> getAllVisibleCategories() {
        return ResponseEntity.ok(categoryService.getAllVisibleCategories());
    }

    @GetMapping("/admin")
    public ResponseEntity<List<CategoryAdminResponse>> getAllCategoriesForAdmin() {
        return ResponseEntity.ok(categoryService.getAllCategoriesForAdmin());
    }

    @PatchMapping("/{categoryId}/visibility")
    public ResponseEntity<?> updateVisibility(
            @PathVariable Integer categoryId,
            @RequestBody UpdateCategoryVisibilityRequest request
    ) {
        categoryService.updateVisibility(categoryId, request.getVisible());
        return ResponseEntity.ok().build();
    }


    @PostMapping("/reorder")
    public ResponseEntity<?> reorderCategories(
            @RequestBody UpdateCategoryOrderRequest request
    ) {
        categoryService.updateOrder(request.getOrders());
        return ResponseEntity.ok().build();
    }



}

