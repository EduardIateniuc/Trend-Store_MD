package md.donesk.trendshop.repository;

import md.donesk.trendshop.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByEmail(String email);

    boolean existsByEmail(String email);
}
