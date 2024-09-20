package com.example.demo.repository;

import com.example.demo.entity.Brand;
import com.example.demo.entity.Color;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {
    Boolean existsByNameIgnoreCase(String name);
    Boolean existsByCodeIgnoreCase(String code);
    List<Color> findByStatus(Integer status);
}
