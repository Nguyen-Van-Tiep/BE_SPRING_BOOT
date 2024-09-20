package com.example.demo.repository;

import com.example.demo.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Query("SELECT a FROM Address a WHERE a.createdBy = :createdBy ORDER BY a.isDefault DESC, a.modifiedDate DESC")
    Page<Address> findAllByUserIdOrderByIsDefaultDescModifiedDateDesc(@Param("createdBy") String createdBy, Pageable pageable);

    // In AddressRepository
    @Query("SELECT a FROM Address a JOIN User u ON a.createdBy = u.username WHERE u.id = :id ORDER BY a.isDefault DESC, a.modifiedDate DESC")
    Page<Address> findAllUserIdOrderByAddress(@Param("id") Long id, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Address a SET a.isDefault = false WHERE a.isDefault = true AND a.createdBy = :username AND a.id <> :id")
    void resetAllDefaultAddresses(@Param("username") String username,@Param("id") Integer id);
    @Query("SELECT a FROM Address a WHERE a.createdBy = :createdBy ORDER BY a.isDefault DESC, a.modifiedDate DESC")
    List<Address> findAllByUserIdOrderByIsDefaultDescModifiedDateDesc(@Param("createdBy") String createdBy);


    @Modifying
    @Transactional
    @Query("UPDATE Address a SET a.isDefault = false WHERE a.isDefault = true AND a.user.id = :userID AND a.id <> :id")
    void resetAllDefaultAddressesID(@Param("userID") Long userID, @Param("id") Integer id);

    @Query("SELECT a FROM Address a WHERE a.createdBy = :createdBy AND a.isDefault = true")
    Address findDefaultAddress(@Param("createdBy") String createdBy);
}
