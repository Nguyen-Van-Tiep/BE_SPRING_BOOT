package com.example.demo.repository;

import com.example.demo.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<Size,Integer> {
    Boolean existsByNameIgnoreCase(String sizeName);
    List<Size> findByStatus(Integer status);
}

