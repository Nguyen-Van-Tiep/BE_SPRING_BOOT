package com.example.demo.repository;

import com.example.demo.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material,Integer> {
    Boolean existsByNameIgnoreCase(String materialName);
    List<Material> findByStatus(Integer status);
}
