package md.donesk.trendshop.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import md.donesk.trendshop.dto.request.ProductRequest;
import md.donesk.trendshop.dto.response.ProductVariantDTO;
import md.donesk.trendshop.exception.ProductNotFoundException;
import md.donesk.trendshop.model.Product;
import md.donesk.trendshop.model.ProductVariant;
import md.donesk.trendshop.repository.ProductRepository;
import md.donesk.trendshop.repository.ProductVariantRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;


    public List<ProductVariantDTO> getInventoryByProductIdFilter(String productCode) {
        Product product = productRepository.findByProductCode(productCode).orElseThrow(() -> new ProductNotFoundException("Product not found with ProductCode: " + productCode));;

        List<ProductVariant> variants = productVariantRepository.findByProductId(product.getId());
        return variants.stream()
                .map(ProductVariantDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // New method to get filtered variants
    public List<ProductVariantDTO> getFilteredVariants(String productCode, String color, String size) {
        List<ProductVariant> variants;
        Product product = productRepository.findByProductCode(productCode).orElseThrow(() -> new ProductNotFoundException("Product not found with ProductCode: " + productCode));;

        if (color != null && size != null) {
            variants = productVariantRepository.findAllByProductIdAndColorAndSize(product.getId(), color, size);
        } else if (color != null) {
            variants = productVariantRepository.findByProductIdAndColor(product.getId(), color);
        } else if (size != null) {
            variants = productVariantRepository.findByProductIdAndSize(product.getId(), size);
        } else {
            variants = productVariantRepository.findByProductId(product.getId());
        }

        return variants.stream()
                .map(ProductVariantDTO::fromEntity)
                .collect(Collectors.toList());
    }


    @Transactional
    public ProductVariantDTO addProductVariant(String productCode, ProductRequest.ProductVariantRequest request) {
        Product product = productRepository.findByProductCode(productCode).orElseThrow(() -> new ProductNotFoundException("Product not found with ProductCode: " + productCode));;

        // Check if variant with same color and size already exists
        Optional<ProductVariant> existingVariant = productVariantRepository
                .findByProductIdAndColorAndSize(product.getId(), request.getColor(), request.getSize());

        if (existingVariant.isPresent()) {
            throw new RuntimeException(
                    String.format("Variant with color %s and size %s already exists for this product",
                            request.getColor(), request.getSize())
            );
        }

        ProductVariant variant = new ProductVariant();
        variant.setProduct(product);
        variant.setColor(request.getColor());
        variant.setSize(request.getSize());
        variant.setStockLevel(request.getStockLevel());

        ProductVariant savedVariant = productVariantRepository.save(variant);
        return ProductVariantDTO.fromEntity(savedVariant);
    }


    @Transactional
    public ProductVariantDTO updateStockLevel(String productCode, String color, String size, Integer newStockLevel) {
        Product product = productRepository.findByProductCode(productCode).orElseThrow(() -> new ProductNotFoundException("Product not found with ProductCode: " + productCode));;

        ProductVariant variant = productVariantRepository
                .findByProductIdAndColorAndSize(product.getId(), color, size)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Variant with color %s and size %s not found for product %d",
                                color, size, productCode)
                ));

        variant.setStockLevel(newStockLevel);
        ProductVariant updatedVariant = productVariantRepository.save(variant);
        return ProductVariantDTO.fromEntity(updatedVariant);
    }
//    @Transactional
//    public boolean reduceStock(Long productId, Long sizeId, Integer colorId, int quantity) {
//        Inventory inventory = findInventory(productId, sizeId, colorId);
//        if (inventory.getStockLevel() >= quantity) {
//            inventory.setStockLevel(inventory.getStockLevel() - quantity);
//            inventoryRepository.save(inventory);
//            return true;
//        }
//        return false;
//    }
//
//    public boolean checkStockAvailability(Long productId, Long sizeId, Integer colorId, int quantity) {
//        Inventory inventory = findInventory(productId, sizeId, colorId);
//        return inventory.getStockLevel() >= quantity;
//    }

//    private Inventory findInventory(Long productId, Long sizeId, Integer colorId) {
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
//
//        Size size = sizeRepository.findById(sizeId)
//                .orElseThrow(() -> new IllegalArgumentException("Size not found"));
//
//        Color color = colorRepository.findById(colorId)
//                .orElseThrow(() -> new IllegalArgumentException("Color not found"));
//
//        ProductSize productSize = productSizeRepository.findByProductAndSize(product, size)
//                .orElseThrow(() -> new InventoryNotFoundException("No inventory found for this product and size"));
//
//        ProductColor productColor = productColorRepository.findByProductAndColor(product, color)
//                .orElseThrow(() -> new InventoryNotFoundException("No inventory found for this product and color"));
//
//        return inventoryRepository.findByProductAndProductSizeAndProductColor(product, productSize, productColor)
//                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found"));
//
//    }


//    @Transactional
//    public String batchSaveInventory(MultipartFile file) {
//        List<Inventory> inventoriesToSave = new ArrayList<>();
//        StringBuilder errors = new StringBuilder();
//        int lineNumber = 1;
//        int batchSize = 50; // You can adjust this
//
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
//             CSVReader csvReader = new CSVReader(reader)) {
//
//            String[] headers = csvReader.readNext();
//            System.out.println("Headers read: " + Arrays.toString(headers));
//            System.out.println("Headers validation result: " + validateCsvHeaders(headers));
//            if (!validateCsvHeaders(headers)) {
//                return "Invalid CSV format. Required columns: productId,sizeId,colorId,stockLevel";
//            }
//
//            List<String[]> allLines = csvReader.readAll();
//            int totalProcessed = 0;
//
//            for (String[] line : allLines) {
//                lineNumber++;
//                try {
//                    if (line.length < 4) {
//                        throw new IllegalArgumentException("Missing required fields");
//                    }
//
//                    Long productId = Long.valueOf(line[0].trim());
//                    Long sizeId = Long.valueOf(line[1].trim());
//                    Integer colorId = Integer.valueOf(line[2].trim());
//                    Integer stockLevel = Integer.valueOf(line[3].trim());
//
//                    Product product = productRepository.findById(productId)
//                            .orElseThrow(() -> new ProductNotFoundException("Product not found: " + productId));
//
//                    Size size = sizeRepository.findById(sizeId)
//                            .orElseThrow(() -> new IllegalArgumentException("Size not found: " + sizeId));
//
//                    Color color = colorRepository.findById(colorId)
//                            .orElseThrow(() -> new IllegalArgumentException("Color not found: " + colorId));
//
//                    // Create or get ProductSize
//                    ProductSize productSize = productSizeRepository.findByProductAndSize(product, size)
//                            .orElseGet(() -> {
//                                ProductSize newPs = new ProductSize();
//                                newPs.setProduct(product);
//                                newPs.setSize(size);
//                                return productSizeRepository.save(newPs);
//                            });
//
//                    // Create or get ProductColor
//                    ProductColor productColor = productColorRepository.findByProductAndColor(product, color)
//                            .orElseGet(() -> {
//                                ProductColor newPc = new ProductColor();
//                                newPc.setProduct(product);
//                                newPc.setColor(color);
//                                return productColorRepository.save(newPc);
//                            });
//
//                    // Create or update Inventory
//                    Inventory inventory = inventoryRepository
//                            .findByProductAndProductSizeAndProductColor(product, productSize, productColor)
//                            .orElseGet(Inventory::new);
//
//                    inventory.setProduct(product);
//                    inventory.setProductSize(productSize);
//                    inventory.setProductColor(productColor);
//                    inventory.setStockLevel(stockLevel);
//
//                    inventoriesToSave.add(inventory);
//                    totalProcessed++;
//
//                    // Batch save when reaching batch size
//                    if (inventoriesToSave.size() >= batchSize) {
//                        inventoryRepository.saveAll(inventoriesToSave);
//                        inventoriesToSave.clear();
//                    }
//
//                } catch (Exception e) {
//                    errors.append("Error at line ").append(lineNumber)
//                            .append(": ").append(e.getMessage()).append("\n");
//                }
//            }
//
//            // Save remaining items
//            if (!inventoriesToSave.isEmpty()) {
//                inventoryRepository.saveAll(inventoriesToSave);
//            }
//
//            if (!errors.isEmpty()) {
//                return "Processed " + totalProcessed + " records with following errors:\n" + errors;
//            }
//
//            return "Successfully processed " + totalProcessed + " inventory records";
//
//        } catch (IOException | CsvException e) {
//            return "Failed to process CSV file: " + e.getMessage();
//        }
//    }

    private boolean validateCsvHeaders(String[] headers) {
        Set<String> requiredHeaders = Stream.of("productId", "sizeId", "colorId", "stockLevel")
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        Set<String> actualHeaders = Stream.of(headers)
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        return actualHeaders.containsAll(requiredHeaders);
    }
}