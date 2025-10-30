package md.donesk.trendshop.repository;

import md.donesk.trendshop.model.Product;
import md.donesk.trendshop.model.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    List<ProductVariant> findByProductId(Long productId);
    List<ProductVariant> findByProductIdAndColor(Long productId, String color);
    List<ProductVariant> findByProductIdAndSize(Long productId, String size);
    Optional<ProductVariant> findByProductIdAndColorAndSize(Long productId, String color, String size);
    List<ProductVariant> findAllByProductIdAndColorAndSize(Long productId, String color, String size);
    boolean existsByProductIdAndColorAndSizeAndIdNot(Long productId, String color, String size, Long variantId);
}
