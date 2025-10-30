package md.donesk.trendshop.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import md.donesk.trendshop.dto.request.CategoryRequest;
import md.donesk.trendshop.dto.request.UpdateCategoryOrderRequest;
import md.donesk.trendshop.dto.response.CategoryAdminResponse;
import md.donesk.trendshop.dto.response.CategoryResponse;
import md.donesk.trendshop.exception.CategoryNotFoundException;
import md.donesk.trendshop.model.Category;
import md.donesk.trendshop.model.Gender;
import md.donesk.trendshop.model.Subcategory;
import md.donesk.trendshop.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final SubCategoryService subCategoryService;

    @Transactional
    public CategoryResponse createCategory(CategoryRequest requestDto) {
        Category category = new Category();
        category.setCategoryName(requestDto.getCategoryName());
        Category savedCategory = categoryRepository.save(category);
        return mapToDto(savedCategory);
    }

    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAllWithSubcategories();
        categories.forEach(category -> {
            if (category.getSubcategories() == null) {
                category.setSubcategories(new ArrayList<>());
            }
        });
        return categories.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public CategoryResponse getCategoryById(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category with id " + categoryId  + " not found"));
        return mapToDto(category);
    }

    @Transactional
    public String importFromCSV(MultipartFile categoriesCsvFile, MultipartFile subcategoriesCsvFile) {
        try {
            // Import categories
            List<Category> categories = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(categoriesCsvFile.getInputStream()))) {
                String line;
                boolean firstLine = true;
                while ((line = br.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }
                    String[] values = line.split(",");
                    if (!categoryRepository.existsByCategoryNameAndGender(values[0], Gender.valueOf(values[1]))) {
                        Category category = new Category();
                        category.setCategoryName(values[0]);
                        category.setGender(Gender.valueOf(values[1]));
                        categories.add(category);
                    }
                }
            }
            categoryRepository.saveAll(categories);

            // Import subcategories
            List<Subcategory> subcategories = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(subcategoriesCsvFile.getInputStream()))) {
                String line;
                boolean firstLine = true;
                while ((line = br.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }
                    String[] values = line.split(",");
                    String categoryName = values[0];
                    Gender gender = Gender.valueOf(values[1]);
                    String subcategoryName = values[2];

                    Category category = categoryRepository.findByCategoryNameAndGender(categoryName, gender)
                            .orElseThrow(() -> new RuntimeException("Category not found: " + categoryName));

                    if (!subCategoryService.existsBySubcategoryNameAndCategory(subcategoryName, category)) {
                        Subcategory subcategory = new Subcategory();
                        subcategory.setSubcategoryName(subcategoryName);
                        subcategory.setCategory(category);
                        subcategories.add(subcategory);
                    }
                }
            }
            subCategoryService.batchSave(subcategories);

            return String.format("Successfully imported %d categories and %d subcategories",
                    categories.size(), subcategories.size());

        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV files: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid data in CSV files: " + e.getMessage(), e);
        }
    }

    public List<CategoryResponse> getCategoriesByGender(String genderStr) {
        try {
            Gender gender = Gender.valueOf(genderStr.toUpperCase());
            return categoryRepository.findByGender(gender).stream().map(this::mapToDto).collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid gender: " + genderStr);
        }
    }

    @Transactional
    public CategoryResponse updateCategory(Integer categoryId, CategoryRequest requestDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category with id " + categoryId  + " not found"));
        category.setCategoryName(requestDto.getCategoryName());
        Category updatedCategory = categoryRepository.save(category);
        return mapToDto(updatedCategory);
    }

    @Transactional
    public void deleteCategory(Integer categoryId) {
        categoryRepository.findById(categoryId)
              .orElseThrow(() -> new CategoryNotFoundException("Category with id " + categoryId  + " not found"));

        categoryRepository.deleteById(categoryId);
    }

    public List<CategoryAdminResponse> getAllVisibleCategories() {
        return categoryRepository.findAllWithSubcategories().stream().map(this::toCategoryAdminResponse).collect(Collectors.toList());
    }

    public List<CategoryAdminResponse> getAllCategoriesForAdmin() {
            return categoryRepository.findAllByOrderByDisplayOrderAsc()
                    .stream()
                    .map(this::toCategoryAdminResponse)
                    .collect(Collectors.toList());
    }

    @Transactional
    public void updateVisibility(Integer categoryId, Boolean visibility) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category with id " + categoryId  + " not found"));
        category.setVisible(visibility);
        categoryRepository.save(category);
    }

    @Transactional
    public void updateOrder(List<UpdateCategoryOrderRequest.CategoryOrderItem> orders) {
        orders.forEach(order -> {
            Category category = categoryRepository.findById(order.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException("Category with id " + order.getCategoryId()  + " not found"));
            category.setDisplayOrder(order.getDisplayOrder());
            categoryRepository.save(category);
        });
    }


    private CategoryResponse mapToDto(Category category) {
        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .gender(String.valueOf(category.getGender()))
                .imageUrl(category.getImageUrl())
                .subcategories(category.getSubcategories().stream().map(subCategoryService::mapToDto).collect(Collectors.toList()))
                .build();
    }

    private CategoryAdminResponse toCategoryAdminResponse(Category category) {
        return CategoryAdminResponse.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .displayOrder(category.getDisplayOrder())
                .gender(String.valueOf(category.getGender()))
                .imageUrl(category.getImageUrl())
                .visible(category.getVisible())
                .subcategories(category.getSubcategories().stream().map(subCategoryService::mapToDto).collect(Collectors.toList()))
                .build();
    }
}
