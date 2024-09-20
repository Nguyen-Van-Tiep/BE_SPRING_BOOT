package com.example.demo.repository;

import com.example.demo.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductDetail, Integer> {
    @Query("SELECT pd FROM ProductDetail pd WHERE pd.product.id= :productId ORDER BY pd.id DESC")
    List<ProductDetail> findByProductIdOrderByDesc(@Param("productId") Integer productId);
}
