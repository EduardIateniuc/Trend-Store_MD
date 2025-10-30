package md.donesk.trendshop.service;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import md.donesk.trendshop.model.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    public static Specification<Product> withFilters(
            Integer categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            String size,
            String color) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Add category filter
            if (categoryId != null) {
                Join<Product, Subcategory> subcategoryJoin = root.join("subcategory");
                Join<Subcategory, Category> categoryJoin = subcategoryJoin.join("category");
                predicates.add(cb.equal(categoryJoin.get("id"), categoryId));
            }
            // Add join with variants only once
            Join<Product, ProductVariant> variantJoin = root.join("variants", JoinType.LEFT);

            // Price range filter
            if (minPrice != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
            }
            if (maxPrice != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            // Size filter
            if (size != null && !size.isEmpty()) {
                predicates.add(cb.equal(variantJoin.get("size"), size));
            }

            // Color filter
            if (color != null && !color.isEmpty()) {
                predicates.add(cb.equal(variantJoin.get("color"), color));
            }

            // Ensure we only get distinct products when joining with variants
            query.distinct(true);

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Product> withSubcategoryFilters(
            Integer subcategoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            String size,
            String color) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Subcategory filter
            if (subcategoryId != null) {
                predicates.add(criteriaBuilder.equal(root.get("subcategory").get("id"), subcategoryId));
            }

            // Price filters
            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            // Variant filters
            if ((size != null && !size.isEmpty()) || (color != null && !color.isEmpty())) {
                Join<Product, ProductVariant> variantJoin = root.join("variants");

                if (size != null && !size.isEmpty()) {
                    predicates.add(criteriaBuilder.equal(variantJoin.get("size"), size));
                }
                if (color != null && !color.isEmpty()) {
                    predicates.add(criteriaBuilder.equal(variantJoin.get("color"), color));
                }

                // Add distinct to avoid duplicates due to the join
                query.distinct(true);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}