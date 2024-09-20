package com.example.demo.repository;

import com.example.demo.entity.Image;
import com.example.demo.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findByProductDetail(ProductDetail productDetail);
    @Query("SELECT i.url FROM Image i WHERE i.productDetail.id = :productDetailId")
    String findUrlByProductDetailId(@Param("productDetailId") Integer productDetailId);
}