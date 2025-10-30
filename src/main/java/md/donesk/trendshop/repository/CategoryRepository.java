package md.donesk.trendshop.repository;

import md.donesk.trendshop.model.Category;
import md.donesk.trendshop.model.Gender;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.subcategories")
    List<Category> findAllWithSubcategories();

    @Query("SELECT DISTINCT c FROM Category c " +
            "LEFT JOIN FETCH c.subcategories s " +
            "WHERE c.gender = :gender")
    List<Category> findByGender(@Param("gender") Gender gender);

    boolean existsByCategoryNameAndGender(String categoryName, Gender gender);

    Optional<Category> findByCategoryNameAndGender(String categoryName, Gender gender);

    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.subcategories WHERE c.visible = true ORDER BY c.displayOrder ASC")
    List<Category> findVisibleCategoriesWithSubcategories();

    @Query("SELECT MAX(c.displayOrder) FROM Category c")
    Optional<Integer> findMaxDisplayOrder();

    @Modifying
    @Query("UPDATE Category c SET c.displayOrder = c.displayOrder + 1 WHERE c.displayOrder >= :startOrder")
    void shiftDisplayOrdersUp(@Param("startOrder") Integer startOrder);

    @Modifying
    @Query("UPDATE Category c SET c.displayOrder = c.displayOrder - 1 WHERE c.displayOrder >= :startOrder")
    void shiftDisplayOrdersDown(@Param("startOrder") Integer startOrder);

    @Query("SELECT c FROM Category c WHERE c.visible = true AND c.displayOrder < :currentOrder ORDER BY c.displayOrder DESC")
    Optional<Category> findPreviousVisibleCategory(@Param("currentOrder") Integer currentOrder);

    @Query("SELECT c FROM Category c WHERE c.visible = true AND c.displayOrder > :currentOrder ORDER BY c.displayOrder ASC")
    Optional<Category> findNextVisibleCategory(@Param("currentOrder") Integer currentOrder);


    List<Category> findAllByOrderByDisplayOrderAsc();

}
