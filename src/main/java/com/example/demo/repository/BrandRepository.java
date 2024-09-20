package com.example.demo.repository;

import com.example.demo.entity.Brand;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

    Boolean existsByNameIgnoreCase(String brandName);
    List<Brand> findByStatus(Integer status);
}
