package md.donesk.trendshop.controller;

import jakarta.validation.Valid;
import md.donesk.trendshop.dto.request.ProductRequest;
import md.donesk.trendshop.dto.response.ProductVariantDTO;
import md.donesk.trendshop.service.ProductVariantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trend/variants")
public class ProductVariantController {


    private final ProductVariantService productVariantService;

    public ProductVariantController(ProductVariantService productVariantService) {
        this.productVariantService = productVariantService;
    }

    @GetMapping("/{productCode}")
    public ResponseEntity<List<ProductVariantDTO>> getProductVariantByProductId(
            @PathVariable String productCode,
            @RequestParam(value = "color", required = false) String color,
            @RequestParam(value = "size", required = false) String size) {
        return ResponseEntity.ok(productVariantService.getFilteredVariants(productCode, color, size));
    }

    @PostMapping("/product/{productCode}")
    public ResponseEntity<ProductVariantDTO> addVariant(
            @PathVariable String productCode,
            @Valid @RequestBody ProductRequest.ProductVariantRequest request) {
        return ResponseEntity.ok(productVariantService.addProductVariant(productCode, request));
    }

    @PatchMapping("/product/{productCode}/stock")
    public ResponseEntity<ProductVariantDTO> updateStockLevel(
            @PathVariable String productCode,
            @RequestParam String color,
            @RequestParam String size,
            @RequestParam Integer stockLevel) {
        return ResponseEntity.ok(productVariantService.updateStockLevel(productCode, color, size, stockLevel));
    }

}
