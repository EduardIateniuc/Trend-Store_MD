package md.donesk.trendshop.repository;

import md.donesk.trendshop.model.Category;
import md.donesk.trendshop.model.Gender;
import md.donesk.trendshop.model.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<Subcategory, Integer> {


    boolean existsBySubcategoryNameAndCategory(String subcategoryName, Category category);

    Optional<Subcategory> findBySubcategoryName(String subcategoryName);

    @Query("SELECT s FROM Subcategory s JOIN s.category c " +
            "WHERE s.subcategoryName = :subcategoryName " +
            "AND c.gender = :gender " +
            "AND c.categoryName = :categoryName")
    Optional<Subcategory> findByNameGenderAndCategory(
            @Param("subcategoryName") String subcategoryName,
            @Param("gender") Gender gender,
            @Param("categoryName") String categoryName);

    List<Subcategory> findByCategory(Category category);
}
