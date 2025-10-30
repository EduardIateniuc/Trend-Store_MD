package md.donesk.trendshop.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import md.donesk.trendshop.dto.request.SubCategoryRequest;
import md.donesk.trendshop.dto.response.SubCategoryResponse;
import md.donesk.trendshop.exception.CategoryNotFoundException;
import md.donesk.trendshop.model.Category;
import md.donesk.trendshop.model.Subcategory;
import md.donesk.trendshop.repository.CategoryRepository;
import md.donesk.trendshop.repository.SubCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubCategoryService {
    private final SubCategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;


    @Transactional
    public SubCategoryResponse createSubcategory(Integer categoryId, SubCategoryRequest requestDto) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(EntityNotFoundException::new); // Validate category
        Subcategory subcategory = new Subcategory();
        subcategory.setSubcategoryName(requestDto.getSubcategoryName());
        subcategory.setCategory(category);
        Subcategory savedSubcategory = subcategoryRepository.save(subcategory);
        return mapToDto(savedSubcategory);
    }

    public boolean existsBySubcategoryNameAndCategory(String subcategoryName, Category category) {
        return subcategoryRepository.existsBySubcategoryNameAndCategory(subcategoryName, category);
    }

    public Integer batchSave(List<Subcategory> subCategoryRequests) {
        return subcategoryRepository.saveAll(subCategoryRequests).size();
    }


    public List<SubCategoryResponse> getAllSubcategories(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("Category not found!"));
        return subcategoryRepository.findByCategory(category).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public SubCategoryResponse getSubcategoryById(Integer subcategoryId) {
        Subcategory subcategory = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));
        return mapToDto(subcategory);
    }

    @Transactional
    public SubCategoryResponse updateSubcategory(Integer categoryId, Integer subcategoryId, SubCategoryRequest requestDto) {
        Subcategory subcategory = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(EntityNotFoundException::new); // Validate category
        subcategory.setSubcategoryName(requestDto.getSubcategoryName());
        subcategory.setCategory(category);
        Subcategory updatedSubcategory = subcategoryRepository.save(subcategory);
        return mapToDto(updatedSubcategory);
    }

    @Transactional
    public void deleteSubcategory(Integer subcategoryId) {
        subcategoryRepository.deleteById(subcategoryId);
    }

    public SubCategoryResponse mapToDto(Subcategory subcategory) {
        return SubCategoryResponse.builder()
                .subcategoryId(subcategory.getSubcategoryId())
                .subcategoryName(subcategory.getSubcategoryName())
                .categoryId(subcategory.getCategory().getCategoryId())
                .build();
    }
}
