package com.example.demo.repository;

import com.example.demo.entity.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProductSpecifications {

    public static Specification<Product> hasStatus(Integer status) {
        return (root, query, cb) -> {
            return cb.equal(root.get("status"), status);
        };
    }

    public static Specification<Product> hasStyles(List<Style> styles) {
        return (root, query, criteriaBuilder) -> {
            if (styles == null || styles.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.get("style").in(styles);
        };
    }

    public static Specification<Product> hasBrands(List<Brand> brands) {
        return (root, query, criteriaBuilder) -> {
            if (brands == null || brands.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.get("brand").in(brands);
        };
    }
    public static Specification<Product> hasSoles(List<Sole> soles) {
        return (root, query, criteriaBuilder) -> {
            if (soles == null || soles.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.get("sole").in(soles);
        };
    }
    public static Specification<Product> hasMaterials(List<Material> materials) {
        return (root, query, criteriaBuilder) -> {
            if (materials == null || materials.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.get("material").in(materials);
        };
    }

}
