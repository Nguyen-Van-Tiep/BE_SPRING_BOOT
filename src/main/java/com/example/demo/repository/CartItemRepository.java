package com.example.demo.repository;

import com.example.demo.entity.Cart;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findByProductDetail(ProductDetail productDetail);

    List<CartItem> findByCart(Cart cart);

    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.productDetail.id IN :productDetailIds")
    void deleteAllByProductDetailIdIn(@Param("productDetailIds") List<Integer> productDetailIds);
}
