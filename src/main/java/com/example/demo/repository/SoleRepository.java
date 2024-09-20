package com.example.demo.repository;

import com.example.demo.entity.Sole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoleRepository extends JpaRepository<Sole, Integer> {
    Boolean existsByNameIgnoreCase(String soleName);
    List<Sole> findByStatus(Integer status);
}

