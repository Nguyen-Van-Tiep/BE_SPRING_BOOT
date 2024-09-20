package com.example.demo.repository;

import com.example.demo.entity.ShippingHistory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ShippingHistoryRepository extends JpaRepository<ShippingHistory, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM ShippingHistory sh WHERE sh.billId = :billId")
    void deleteAllByBillId(@Param("billId") Integer billId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ShippingHistory sh WHERE sh.billId = :billId AND sh.level = :level")
    Integer deleteByBillIdAndLevelGreaterThanEqual(@Param("billId") Integer billId, @Param("level") int level);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM shipping_history WHERE BILL_ID = :billId AND level >= :level", nativeQuery = true)
    void deleteByBillIdAndLevelGreaterThanEquals(@Param("billId") Integer billId, @Param("level") int level);


    //    lấy dan sách lịch sử vận chuyển theo mã hóa đơn là lích sử tổng quan và sắp xếp ngày chỉnh sửa theo thứ tự tăng dần
    List<ShippingHistory> findByBillIdAndParentIdIsNullOrderByModifiedDateAsc(Integer billId);

//    lấy danh sách lịch sử vận chuyển theo mã hóa đơn và sắp xếp ngày chỉnh sửa theo thứ tự tăng dần và trạng thái hoàn thành

    List<ShippingHistory> findByBillIdAndStatusOrderByModifiedDateDesc(Integer billId, String status);

        //lấy lịch sử vận chuyển có parentId = null và có trạng thái đang chờ và sắp xếp ngày chỉnh sửa theo thứ tự tăng dần
    ShippingHistory findFirstByBillIdAndParentIdIsNullAndStatusOrderByModifiedDateAsc(Integer billId, String status);
    //    cập nhật trạng thái của lịch sử vận chuyển theo mã hóa đơn
    @Modifying
    @Transactional
    @Query("update ShippingHistory set status = ?2 where billId = ?1")
    void updateStatusByBillId(Integer billId, String status);


}
