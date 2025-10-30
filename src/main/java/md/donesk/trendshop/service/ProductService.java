package md.donesk.trendshop.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import md.donesk.trendshop.dto.request.ProductRequest;
import md.donesk.trendshop.dto.response.ProductAdminResponse;
import md.donesk.trendshop.dto.response.ProductResponse;
import md.donesk.trendshop.exception.ProductNotFoundException;
import md.donesk.trendshop.exception.SubCategoryNotFoundException;
import md.donesk.trendshop.model.Product;
import md.donesk.trendshop.model.ProductVariant;
import md.donesk.trendshop.model.Subcategory;
import md.donesk.trendshop.repository.ProductRepository;
import md.donesk.trendshop.repository.ProductVariantRepository;
import md.donesk.trendshop.repository.SubCategoryRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static md.donesk.trendshop.service.ProductSpecification.withFilters;
import static md.donesk.trendshop.service.ProductSpecification.withSubcategoryFilters;

@Service
@Transactional
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final SubCategoryRepository subcategoryRepository;
    private final ProductVariantRepository productVariantRepository;
    private final CategoryService categoryService;
    public ProductService(ProductRepository productRepository,
                          SubCategoryRepository subcategoryRepository,
                          ProductVariantRepository productVariantRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.subcategoryRepository = subcategoryRepository;
        this.productVariantRepository = productVariantRepository;
        this.categoryService = categoryService;
    }

    public Page<ProductResponse> findProducts(
            Integer categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            String size,
            String color,
            Pageable pageable) {

        Page<Product> products = productRepository.findAll(
                withFilters(categoryId, minPrice, maxPrice, size, color),
                pageable
        );

        return products.map(ProductResponse::fromEntity);
    }

    public Page<ProductResponse> findProductsBySubcategory(
            Integer subcategoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            String size,
            String color,
            Pageable pageable) {

        Page<Product> products = productRepository.findAll(
                withSubcategoryFilters(subcategoryId, minPrice, maxPrice, size, color),
                pageable
        );

        return products.map(ProductResponse::fromEntity);
    }


    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        log.info("Creating new product: {}", request.getProductName());

        Subcategory subcategory = subcategoryRepository.findById(request.getSubcategoryId())
                .orElseThrow(() -> new SubCategoryNotFoundException("Subcategory not found"));

        Product product = createProductEntity(request, subcategory);
        product.setVariants(new ArrayList<>()); // Initialize the variants list

        try {
            product = productRepository.save(product);
            log.debug("Saved product with ID: {}", product.getId());

            List<ProductVariant> variants = createProductVariants(request, product);
            variants = productVariantRepository.saveAll(variants);

            // Set the saved variants to the product
            product.setVariants(variants);

            return ProductResponse.fromEntity(product);

        } catch (DataIntegrityViolationException e) {
            log.error("Database error while creating product: {}", e.getMessage());
            throw new ServiceException("Failed to create product due to data constraint violation", e);
        } catch (Exception e) {
            log.error("Unexpected error while creating product: {}", e.getMessage());
            throw new ServiceException("Failed to create product", e);
        }
    }

    public ProductResponse getProductById(Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        return ProductResponse.fromEntity(product);
    }



    private Product createProductEntity(ProductRequest request, Subcategory subcategory) {
        Product product = new Product();
        product.setProductCode(request.getProductCode());
        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImages(request.getImages());
        product.setSubcategory(subcategory);
        product.setVariants(new ArrayList<>()); // Initialize variants list
        return product;
    }
    
    private List<ProductVariant> createProductVariants(ProductRequest request, Product product) {
        return request.getVariants().stream()
                .map(variantRequest -> {
                    ProductVariant variant = new ProductVariant();
                    variant.setProduct(product);
                    variant.setSize(variantRequest.getSize());
                    variant.setColor(variantRequest.getColor());
                    variant.setStockLevel(variantRequest.getStockLevel());
                    return variant;
                })
                .collect(Collectors.toList());
    }


    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    public Page<ProductResponse> searchProductsByName(String name, Pageable pageable) {
        Page<Product> products = productRepository.findByProductNameContainingIgnoreCase(name, pageable);
        return products.map(ProductResponse::fromEntity);
    }


    public ProductResponse updateProduct(String productCode, ProductRequest request) {
        Product product = productRepository.findByProductCode(productCode).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        //product.setImages(request.getImages()); TODO
        product.setSubcategory(subcategoryRepository.findById(request.getSubcategoryId()).get());

        return ProductResponse.fromEntity(productRepository.save(product));
    }
    public Page<ProductAdminResponse> findAllProducts(
            BigDecimal minPrice,
            BigDecimal maxPrice,
            String size,
            String color,
            Pageable pageable) {

        Page<Product> products = productRepository.findAll(
                withFilters(null, minPrice, maxPrice, size, color),
                pageable
        );

        return products.map(ProductAdminResponse::fromEntity);
    }
}