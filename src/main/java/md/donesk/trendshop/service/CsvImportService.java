package md.donesk.trendshop.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import md.donesk.trendshop.dto.request.ProductCsvDTO;
import md.donesk.trendshop.dto.request.ProductVariantCsvDTO;
import md.donesk.trendshop.exception.ProductNotFoundException;
import md.donesk.trendshop.exception.SubCategoryNotFoundException;
import md.donesk.trendshop.model.Gender;
import md.donesk.trendshop.model.Product;
import md.donesk.trendshop.model.ProductVariant;
import md.donesk.trendshop.model.Subcategory;
import md.donesk.trendshop.repository.ProductRepository;
import md.donesk.trendshop.repository.ProductVariantRepository;
import md.donesk.trendshop.repository.SubCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CsvImportService {
    private final ProductService productService;
    private final SubCategoryRepository subcategoryRepository;
    private final ProductRepository productRepository;
    private final ProductVariantRepository variantRepository;

    public CsvImportService(ProductService productService, SubCategoryRepository subcategoryRepository, ProductRepository productRepository, ProductVariantRepository variantRepository) {
        this.productService = productService;
        this.subcategoryRepository = subcategoryRepository;
        this.productRepository = productRepository;
        this.variantRepository = variantRepository;
    }

    @Transactional
    public void importProductsFromCsv(MultipartFile file) throws IOException {
        List<ProductCsvDTO> products = readProductsFromCsv(file);
        List<Product> productsToSave = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        for (ProductCsvDTO dto : products) {
            try {
                Product product = createProduct(dto);
                productsToSave.add(product);
            } catch (Exception e) {
                String errorMessage = String.format("Error processing product %s: %s",
                        dto.getProductCode(), e.getMessage());
                log.error(errorMessage);
                errors.add(errorMessage);
                throw new RuntimeException("Import failed. No products were saved. Errors: " +
                        String.join(", ", errors));
            }
        }

        if (errors.isEmpty()) {
            try {
                productRepository.saveAll(productsToSave);
                log.info("Successfully imported {} products", productsToSave.size());
            } catch (Exception e) {
                String errorMessage = "Failed to save products batch: " + e.getMessage();
                log.error(errorMessage);
                throw new RuntimeException(errorMessage);
            }
        }
    }

    @Transactional
    public void importVariantsFromCsv(MultipartFile file) throws IOException {
        List<ProductVariantCsvDTO> variants = readVariantsFromCsv(file);

        for (ProductVariantCsvDTO variantDto : variants) {
            try {
                Product product = productRepository.findByProductCode(variantDto.getProductCode())
                        .orElseThrow(() -> new ProductNotFoundException(
                                "Product not found with code: " + variantDto.getProductCode()));
                importVariant(product, variantDto);
            } catch (Exception e) {
                log.error("Error importing variant for product {}: {}",
                        variantDto.getProductCode(), e.getMessage());
            }
        }
    }

    private void importProduct(ProductCsvDTO productDto) {
        Optional<Product> existingProduct = productRepository.findByProductCode(productDto.getProductCode());

        if (existingProduct.isPresent()) {
            updateExistingProduct(existingProduct.get(), productDto);
        } else {
            createProduct(productDto);
        }
    }

    private Product createProduct(ProductCsvDTO dto) {
        // Validate product doesn't already exist
        if (productRepository.findByProductCode(dto.getProductCode()).isPresent()) {
            throw new IllegalArgumentException(
                    String.format("Product with code %s already exists", dto.getProductCode()));
        }

        Product product = new Product();
        product.setProductCode(dto.getProductCode());
        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());

        // Parse and set images
        try {
            List<String> imagesList = dto.parseImageUrls();
            product.setImages(imagesList);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    String.format("Failed to parse images for product %s: %s",
                            dto.getProductCode(), e.getMessage()));
        }

        // Find and set subcategory
        if (dto.getSubcategoryName() != null && dto.getCategoryName() != null) {
            Gender gender = Gender.valueOf(dto.getGender());
            Subcategory subcategory = subcategoryRepository
                    .findByNameGenderAndCategory(
                            dto.getSubcategoryName(),
                            gender,
                            dto.getCategoryName())
                    .orElseThrow(() -> new SubCategoryNotFoundException(
                            String.format("Subcategory %s not found in category %s for gender %s",
                                    dto.getSubcategoryName(),
                                    dto.getCategoryName(),
                                    dto.getGender())));
            product.setSubcategory(subcategory);
        } else {
            throw new IllegalArgumentException(
                    String.format("Category and subcategory are required for product %s",
                            dto.getProductCode()));
        }

        return product;
    }

    private List<ProductCsvDTO> readProductsFromCsv(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<ProductCsvDTO> csvToBean = new CsvToBeanBuilder<ProductCsvDTO>(reader)
                    .withType(ProductCsvDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();
        } catch (Exception e) {
            log.error("Failed to parse CSV file: {}", e.getMessage());
            throw new IOException("Failed to parse CSV file", e);
        }
    }

    private Product updateExistingProduct(Product product, ProductCsvDTO dto) {
        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());

        if (dto.getImageUrl() != null) {
            product.setImages(Collections.singletonList(dto.getImageUrl()));
        }

        if (dto.getSubcategoryName() != null) {
            Subcategory subcategory = subcategoryRepository.findBySubcategoryName(dto.getSubcategoryName())
                    .orElseThrow(() -> new SubCategoryNotFoundException(
                            "Subcategory not found: " + dto.getSubcategoryName()));
            product.setSubcategory(subcategory);
        }

        return productRepository.save(product);
    }

    private void importVariant(Product product, ProductVariantCsvDTO dto) {
        Optional<ProductVariant> existingVariant = variantRepository
                .findByProductIdAndColorAndSize(product.getId(), dto.getColor(), dto.getSize());

        if (existingVariant.isPresent()) {
            // Update existing variant
            ProductVariant variant = existingVariant.get();
            variant.setStockLevel(dto.getStockLevel());
            variantRepository.save(variant);
        } else {
            // Create new variant
            ProductVariant variant = new ProductVariant();
            variant.setProduct(product);
            variant.setColor(dto.getColor());
            variant.setSize(dto.getSize());
            variant.setStockLevel(dto.getStockLevel());
            variantRepository.save(variant);
        }
    }


    private List<ProductVariantCsvDTO> readVariantsFromCsv(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<ProductVariantCsvDTO> csvToBean = new CsvToBeanBuilder<ProductVariantCsvDTO>(reader)
                    .withType(ProductVariantCsvDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();
        }
    }
}