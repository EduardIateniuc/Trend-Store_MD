package md.donesk.trendshop.repository;

import md.donesk.trendshop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {


    Optional<Product> findByProductCode(String productCode);

    Page<Product> findByProductNameContainingIgnoreCase(String name, Pageable pageable);
}
