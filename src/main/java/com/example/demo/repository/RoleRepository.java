package com.example.demo.repository;

import com.example.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r.name FROM Role r WHERE r.id IN (SELECT u.roleId FROM UserRole u WHERE u.userId = :userId)")
    List<String> getRolesByUser(@Param("userId") Long userId);
    Role findByName(String name);
}
