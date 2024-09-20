package com.example.demo.repository;

import com.example.demo.entity.Bill;
import com.example.demo.model.DTO.ProductDTO;
import com.example.demo.model.result.StatisticsResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
    //    lấy danh sách hóa đơn theo mã hóa đơn và tên khách hàng và số điện thoại và trạng thái
    @Query("SELECT b FROM Bill b WHERE b.code LIKE %:code% AND b.recipientName LIKE %:recipientName% AND b.recipientPhoneNumber LIKE %:recipientPhoneNumber% AND b.status = :status")
    Page<Bill> getAll(@Param("code") String code,
                      @Param("recipientName") String recipientName,
                      @Param("recipientPhoneNumber") String recipientPhoneNumber,
                      @Param("status") String status,
                      Pageable pageable);

    @Query("SELECT b FROM Bill b WHERE b.code LIKE %:code% AND b.createdBy LIKE %:username% AND b.status = :status")
    Page<Bill> getAll(String code, String username, String status, Pageable pageable);

    @Query(value = "SELECT b.* FROM BILL b " +
            "WHERE " +
            "LOWER(b.CODE) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(b.RECIPIENT_NAME) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(b.RECIPIENT_PHONE_NUMBER) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(b.STATUS) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(FORMAT(b.CREATED_DATE, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%'))" + // Giả sử b.someDateField là cột ngày
            "OR LOWER(FORMAT(b.DATE_OF_PAYMENT, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%'))" + // Giả sử b.someDateField là cột ngày
            "OR LOWER(FORMAT(b.ESTIMATED_DELIVERY_DATE, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%'))" + // Giả sử b.someDateField là cột ngày
            "OR LOWER(FORMAT(b.MODIFIED_DATE, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%'))" + // Giả sử b.someDateField là cột ngày
            "OR LOWER(FORMAT(b.SHIPPING_DATE, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%'))",
            countQuery = "SELECT COUNT(*) FROM BILL b " +
                    "WHERE " +
                    "LOWER(b.CODE) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "OR LOWER(b.RECIPIENT_NAME) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "OR LOWER(b.RECIPIENT_PHONE_NUMBER) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "OR LOWER(b.STATUS) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "OR LOWER(FORMAT(b.CREATED_DATE, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%'))" + // Giả sử b.someDateField là cột ngày
                    "OR LOWER(FORMAT(b.DATE_OF_PAYMENT, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%'))" + // Giả sử b.someDateField là cột ngày
                    "OR LOWER(FORMAT(b.ESTIMATED_DELIVERY_DATE, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%'))" + // Giả sử b.someDateField là cột ngày
                    "OR LOWER(FORMAT(b.MODIFIED_DATE, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%'))" + // Giả sử b.someDateField là cột ngày
                    "OR LOWER(FORMAT(b.SHIPPING_DATE, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%'))",// Giả sử b.someDateField là cột ngày
            nativeQuery = true)
    Page<Bill> searchBills(@Param("keyword") String keyword, Pageable pageable);


    @Query(value = "SELECT b.* FROM BILL b " +
            "WHERE (" +
            "LOWER(b.CODE) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(b.RECIPIENT_NAME) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(b.RECIPIENT_PHONE_NUMBER) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(FORMAT(b.CREATED_DATE, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(FORMAT(b.DATE_OF_PAYMENT, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(FORMAT(b.ESTIMATED_DELIVERY_DATE, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(FORMAT(b.MODIFIED_DATE, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(FORMAT(b.SHIPPING_DATE, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%')))" +
            "AND LOWER(b.STATUS) LIKE LOWER(CONCAT('%', :status, '%'))",
            countQuery = "SELECT COUNT(*) FROM BILL b " +
                    "WHERE (" +
                    "LOWER(b.CODE) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "OR LOWER(b.RECIPIENT_NAME) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "OR LOWER(b.RECIPIENT_PHONE_NUMBER) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "OR LOWER(FORMAT(b.CREATED_DATE, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "OR LOWER(FORMAT(b.DATE_OF_PAYMENT, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "OR LOWER(FORMAT(b.ESTIMATED_DELIVERY_DATE, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "OR LOWER(FORMAT(b.MODIFIED_DATE, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "OR LOWER(FORMAT(b.SHIPPING_DATE, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%')))" +
                    "AND LOWER(b.STATUS) LIKE LOWER(CONCAT('%', :status, '%'))",
            nativeQuery = true)
    Page<Bill> searchBills(@Param("keyword") String keyword, @Param("status") String status, Pageable pageable);

    Optional<Bill> findByCode(String code);


     //Thống kê theo ngày
    @Query("SELECT new com.example.demo.model.result.StatisticsResult(COUNT(b), SUM(b.price), " +
            "SUM(CASE WHEN b.status = 'F' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN b.status = 'C' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN b.status = 'R' THEN 1 ELSE 0 END)) " +
            "FROM Bill b WHERE b.dateOfPayment = CURRENT_DATE")
    StatisticsResult getStatisticsByDay();


    // Thống kê theo tuần (native query)
    @Query(value = "SELECT COUNT(*), SUM(PRICE), \n" +
            "       SUM(CASE WHEN STATUS = 'F' THEN 1 ELSE 0 END),\n" +
            "       SUM(CASE WHEN STATUS = 'C' THEN 1 ELSE 0 END),\n" +
            "       SUM(CASE WHEN STATUS = 'R' THEN 1 ELSE 0 END)\n" +
            "FROM BILL\n" +
            "WHERE DATE_OF_PAYMENT >= DATEADD(DAY, 1 - DATEPART(WEEKDAY, GETDATE()), CAST(GETDATE() AS DATE))\n" +
            "AND DATE_OF_PAYMENT < DATEADD(DAY, 1 - DATEPART(WEEKDAY, GETDATE()) + 7, CAST(GETDATE() AS DATE))\n",
            nativeQuery = true)
    List<Object[]> getStatisticsByWeek();

    @Query(value = "SELECT COUNT(*) AS totalCount, SUM(PRICE) AS totalAmount, " +
            "SUM(CASE WHEN status = 'F' THEN 1 ELSE 0 END) AS completedCount, " +
            "SUM(CASE WHEN status = 'C' THEN 1 ELSE 0 END) AS cancelledCount, " +
            "SUM(CASE WHEN status = 'R' THEN 1 ELSE 0 END) AS returnedCount " +
            "FROM BILL " +
            "WHERE YEAR(DATE_OF_PAYMENT) = YEAR(GETDATE()) " +
            "AND MONTH(DATE_OF_PAYMENT) = MONTH(GETDATE())", nativeQuery = true)
    List<Object[]> getStatisticsByMonth();


    // Thống kê theo năm
    @Query(value = "SELECT COUNT(*) AS totalCount, SUM(PRICE) AS totalAmount, " +
            "SUM(CASE WHEN status = 'F' THEN 1 ELSE 0 END) AS completedCount, " +
            "SUM(CASE WHEN status = 'C' THEN 1 ELSE 0 END) AS cancelledCount, " +
            "SUM(CASE WHEN status = 'R' THEN 1 ELSE 0 END) AS returnedCount " +
            "FROM Bill " +
            "WHERE YEAR(DATE_OF_PAYMENT) = YEAR(GETDATE())", nativeQuery = true)
    List<Object[]> getStatisticsByYear();



    //+----------------------lọc--------------------//
    @Query("SELECT new com.example.demo.model.DTO.ProductDTO(p.productName, SUM(bd.quantity), pd.price) " +
            "FROM BillDetail bd " +
            "JOIN Product p ON bd.productDetail.product.id = p.id " +
            "JOIN ProductDetail pd ON pd.product.id = p.id " +
            "JOIN Bill b ON bd.bill.id = b.id " +
            "WHERE b.dateOfPayment = CURRENT_DATE " +
            "GROUP BY p.productName, pd.price " +
            "ORDER BY SUM(bd.quantity) DESC")
    List<ProductDTO> findTopSellingProductsByDay();

    @Query("SELECT new com.example.demo.model.DTO.ProductDTO(p.productName, SUM(bd.quantity), pd.price) " +
            "FROM BillDetail bd " +
            "JOIN bd.productDetail pd " +
            "JOIN pd.product p " +
            "JOIN bd.bill b " +
            "WHERE b.dateOfPayment >= :startDate " +
            "AND b.dateOfPayment < :endDate " +
            "GROUP BY p.productName, pd.price " +
            "ORDER BY SUM(bd.quantity) DESC")
    List<ProductDTO> findTopSellingProductsByWeek(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT new com.example.demo.model.DTO.ProductDTO(p.productName, SUM(bd.quantity), pd.price) " +
            "FROM BillDetail bd " +
            "JOIN Product p ON bd.productDetail.product.id = p.id " +
            "JOIN ProductDetail pd ON pd.product.id = p.id " +
            "JOIN Bill b ON bd.bill.id = b.id " +
            "WHERE MONTH(b.dateOfPayment) = MONTH(CURRENT_DATE) " +
            "AND YEAR(b.dateOfPayment) = YEAR(CURRENT_DATE) " +
            "GROUP BY p.productName, pd.price " +
            "ORDER BY SUM(bd.quantity) DESC")
    List<ProductDTO> findTopSellingProductsByMonth();

    @Query("SELECT new com.example.demo.model.DTO.ProductDTO(p.productName, SUM(bd.quantity), pd.price) " +
            "FROM BillDetail bd " +
            "JOIN Product p ON bd.productDetail.product.id = p.id " +
            "JOIN ProductDetail pd ON pd.product.id = p.id " +
            "JOIN Bill b ON bd.bill.id = b.id " +
            "WHERE YEAR(b.dateOfPayment) = YEAR(CURRENT_DATE) " +
            "GROUP BY p.productName, pd.price " +
            "ORDER BY SUM(bd.quantity) DESC")
    List<ProductDTO> findTopSellingProductsByYear();

    @Query("SELECT new com.example.demo.model.DTO.ProductDTO(p.productName, SUM(bd.quantity), pd.price) " +
            "FROM BillDetail bd " +
            "JOIN Product p ON bd.productDetail.product.id = p.id " +
            "JOIN ProductDetail pd ON pd.product.id = p.id " +
            "JOIN Bill b ON bd.bill.id = b.id " +
            "WHERE b.dateOfPayment BETWEEN :startDate AND :endDate " +
            "GROUP BY p.productName, pd.price " +
            "ORDER BY SUM(bd.quantity) DESC")
    List<ProductDTO> findTopSellingProductsByCustomRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    Page<Bill> findByCreatedByAndStatus(String createdBy, String status, Pageable pageable);

    @Query(value = "SELECT b.* FROM BILL b " +
            "WHERE (" +
            "LOWER(b.CODE) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(b.RECIPIENT_NAME) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(b.RECIPIENT_PHONE_NUMBER) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(FORMAT(b.CREATED_DATE, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(FORMAT(b.DATE_OF_PAYMENT, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(FORMAT(b.ESTIMATED_DELIVERY_DATE, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(FORMAT(b.MODIFIED_DATE, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(FORMAT(b.SHIPPING_DATE, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +  // Sửa dấu ngoặc đóng
            "AND LOWER(b.CREATED_BY) LIKE LOWER(CONCAT('%', :createdBy, '%')) " +
            "AND LOWER(b.STATUS) LIKE LOWER(CONCAT('%', :status, '%'))",
            countQuery = "SELECT COUNT(*) FROM BILL b " +
                    "WHERE (" +
                    "LOWER(b.CODE) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "OR LOWER(b.RECIPIENT_NAME) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "OR LOWER(b.RECIPIENT_PHONE_NUMBER) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "OR LOWER(FORMAT(b.CREATED_DATE, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "OR LOWER(FORMAT(b.DATE_OF_PAYMENT, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "OR LOWER(FORMAT(b.ESTIMATED_DELIVERY_DATE, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "OR LOWER(FORMAT(b.MODIFIED_DATE, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "OR LOWER(FORMAT(b.SHIPPING_DATE, 'yyyy-MM-dd')) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +  // Sửa dấu ngoặc đóng
                    "AND LOWER(b.CREATED_BY) LIKE LOWER(CONCAT('%', :createdBy, '%')) " +
                    "AND LOWER(b.STATUS) LIKE LOWER(CONCAT('%', :status, '%'))",
            nativeQuery = true)
    Page<Bill> findByCreatedByAndStatus(@Param("createdBy") String createdBy,
                                        @Param("keyword") String keyword,
                                        @Param("status") String status,
                                        Pageable pageable);










}

