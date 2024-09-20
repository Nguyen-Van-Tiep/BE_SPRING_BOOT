package com.example.demo.repository;

import com.example.demo.entity.Style;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StyleRepository extends JpaRepository<Style, Integer> {
    Boolean existsByNameIgnoreCase(String styleName);
    List<Style> findByStatus(Integer status);

}

