package md.donesk.trendshop.repository;

import md.donesk.trendshop.model.ShippingMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingMethodRepository extends JpaRepository<ShippingMethod, Integer> {
}
