package md.donesk.trendshop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import md.donesk.trendshop.dto.request.ProductRequest;
import md.donesk.trendshop.dto.response.*;
import md.donesk.trendshop.model.Product;
import md.donesk.trendshop.service.ProductService;
import md.donesk.trendshop.service.SubCategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/trend/products")
@Validated
@CrossOrigin("*")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final SubCategoryService subCategoryService;

    @GetMapping
    public ResponseEntity<ProductPageResponse> getAllProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) String color,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int sizePage,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        Pageable pageable = PageRequest.of(page, sizePage, Sort.by(direction, sort));

        Page<?> products;
        if (name != null && !name.trim().isEmpty()) {
            products = productService.searchProductsByName(name, pageable);
        } else {
            products = productService.findAllProducts(minPrice, maxPrice, size, color, pageable);
        }

        ProductPageResponse response = new ProductPageResponse(
                products.getContent(),
                products.getTotalElements(),
                products.getTotalPages(),
                sizePage
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ProductPageResponse> searchProductsByName(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int sizePage,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, sizePage, sort);

        Page<ProductResponse> products = productService.searchProductsByName(name, pageable);

        ProductPageResponse response = new ProductPageResponse(
                products.getContent(),
                products.getTotalElements(),
                products.getTotalPages(),
                sizePage
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        ProductResponse createdProduct = productService.createProduct(request);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }



    @PutMapping("/{productCode}")
    public ResponseEntity<ProductResponse> updateProduct(@Valid @RequestBody ProductRequest request, @PathVariable String productCode) {
        ProductResponse updatedProduct = productService.updateProduct(productCode, request);
        return new ResponseEntity<>(updatedProduct, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<ProductPageResponse> getProductsByCategoryId(
            @PathVariable("categoryId") Integer categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) String color,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int sizePage,
            @RequestParam(defaultValue = "id") String sort) {

        Pageable pageable = PageRequest.of(page, sizePage, Sort.by(sort));

        Page<ProductResponse> products = productService.findProducts(
                categoryId, minPrice, maxPrice, size, color, pageable
        );

        ProductPageResponse productPageResponse = new ProductPageResponse(
                products.getContent(),
                products.getTotalElements(),
                products.getTotalPages(),
                sizePage
        );

        return ResponseEntity.ok(productPageResponse);
    }

    @GetMapping("/subcategories/{subcategoryId}")
    public ResponseEntity<ProductPageResponse> getProductsBySubcategory(
            @PathVariable("subcategoryId") Integer subcategoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) String color,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int sizePage,
            @RequestParam(defaultValue = "id") String sort) {

        Pageable pageable = PageRequest.of(page, sizePage, Sort.by(sort));

        Page<ProductResponse> products = productService.findProductsBySubcategory(
                subcategoryId,
                minPrice,
                maxPrice,
                size,
                color,
                pageable
        );

        ProductPageResponse response = new ProductPageResponse(
                products.getContent(),
                products.getTotalElements(),
                products.getTotalPages(),
                sizePage
        );

        return ResponseEntity.ok(response);
    }

}