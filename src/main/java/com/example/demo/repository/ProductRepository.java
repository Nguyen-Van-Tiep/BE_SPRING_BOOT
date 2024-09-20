package com.example.demo.repository;

import com.example.demo.entity.Brand;
import com.example.demo.entity.Product;
import com.example.demo.model.DTO.BestSellingProductDto;
import com.example.demo.model.DTO.MonthlyRevenueDto;
import com.example.demo.model.DTO.RevenueData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    @Query("SELECT pd.product.id,pd.product.productName, SUM(bd.quantity) AS totalQuantity " +
            "FROM BillDetail bd JOIN ProductDetail pd ON bd.productDetail.id = pd.id " +
            "GROUP BY pd.product.id,pd.product.productName " +
            "ORDER BY totalQuantity DESC")
    List<Object[]> findTop10Products(Pageable pageable);

    @Query("SELECT pd.product.id, SUM(bd.quantity), SUM(bd.quantity * bd.price), pd.product.productName " +
            "FROM BillDetail bd JOIN ProductDetail pd ON bd.productDetail.id = pd.id " +
            "GROUP BY pd.product.id, pd.product.productName " +
            "ORDER BY SUM(bd.quantity) DESC")
    List<Object[]> findTop10ProductsT(Pageable pageable);

    @Query("SELECT pd.product.id, pd.product.productName, SUM(bd.quantity) AS totalQuantity, SUM(bd.quantity * bd.price) AS totalRevenue " +
            "FROM BillDetail bd JOIN ProductDetail pd ON bd.productDetail.id = pd.id " +
            "GROUP BY pd.product.id, pd.product.productName " +
            "ORDER BY totalQuantity DESC")
    List<Object[]> findTop10Product(Pageable pageable);

    @Query("SELECT YEAR(b.dateOfPayment) AS year, MONTH(b.dateOfPayment) AS month, DAY(b.dateOfPayment) AS day, " +
            "SUM(bd.quantity * bd.price) AS totalRevenue " +
            "FROM BillDetail bd JOIN bd.bill b " +
            "GROUP BY YEAR(b.dateOfPayment), MONTH(b.dateOfPayment), DAY(b.dateOfPayment) " +
            "ORDER BY year, month, day")
    List<Object[]> findRevenueByDayMonthYear();

    @Query("SELECT YEAR(b.dateOfPayment) AS year, " +
            "SUM(bd.quantity * bd.price) AS totalRevenue " +
            "FROM BillDetail bd JOIN bd.bill b " +
            "GROUP BY YEAR(b.dateOfPayment) " +
            "ORDER BY YEAR(b.dateOfPayment)")
    List<Object[]> findRevenueByYear();

    @Query("SELECT YEAR(b.dateOfPayment) AS year, MONTH(b.dateOfPayment) AS month, " +
            "SUM(bd.quantity * bd.price) AS totalRevenue " +
            "FROM BillDetail bd JOIN bd.bill b " +
            "GROUP BY YEAR(b.dateOfPayment), MONTH(b.dateOfPayment) " +
            "ORDER BY YEAR(b.dateOfPayment), MONTH(b.dateOfPayment)")
    List<Object[]> findRevenueByMonthYear();

    @Query(value = "SELECT DATEPART(MONTH, b.DATE_OF_PAYMENT) AS month, \n" +
            "       ISNULL(SUM(b.PRICE), 0) AS totalAmount\n" +
            "FROM BILL b\n" +
            "WHERE DATEPART(MONTH, b.DATE_OF_PAYMENT) BETWEEN 1 AND 12\n" +
            "GROUP BY DATEPART(MONTH, b.DATE_OF_PAYMENT)\n" +
            "ORDER BY month\n", nativeQuery = true)
    List<Object[]> findMonthlyRevenue();


    @Query("SELECT new com.example.demo.model.DTO.BestSellingProductDto(p.productName, SUM(bp.quantity)) " +
            "FROM BillDetail bp JOIN bp.productDetail.product p JOIN bp.bill b " +
            "WHERE DATEPART(MONTH, b.dateOfPayment) = :currentMonth " +
            "GROUP BY p.productName ORDER BY SUM(bp.quantity) DESC")
    List<BestSellingProductDto> findBestSellingProducts(@Param("currentMonth") int currentMonth);

    @Query("SELECT new com.example.demo.model.DTO.BestSellingProductDto(p.productName, SUM(bp.quantity)) " +
            "FROM BillDetail bp JOIN bp.productDetail.product p JOIN bp.bill b " +
            "WHERE (:productName IS NULL OR p.productName LIKE %:productName%) " +
            "AND (:month IS NULL OR DATEPART(MONTH, b.dateOfPayment) = :month) " +
            "GROUP BY p.productName")
    List<BestSellingProductDto> findByProductNameAndMonth(
            @Param("productName") String productName,
            @Param("month") Integer month);


    @Query("SELECT p FROM Product p WHERE p.id = (SELECT MAX(p2.id) FROM Product p2)")
    Product getLastProductId();

    List<Product> findTop4ByStatusOrderByCreatedDateDesc(Integer status);

    @Query("SELECT p FROM Product p WHERE p.id IN (SELECT dp.product.id FROM ProductDetail dp WHERE dp.id IN :detailProductIds)")
    List<Product> findByDetailProductIds(List<Integer> detailProductIds);

    boolean existsByProductNameIgnoreCase(String productName);

    List<Product> findByStatus(Integer status);

    //    @Query("SELECT new com.example.demo.model.DTO.BestSellingProductDto(bd.productDetail.product.productName, SUM(bd.quantity)) " +
//            "FROM BillDetail bd " +
//            "GROUP BY bd.productDetail.product.productName " +
//            "ORDER BY SUM(bd.quantity) DESC")
//    List<BestSellingProductDto> findTop4BestSellingProducts();
    @Query("SELECT bd.productDetail.product " +
            "FROM BillDetail bd " +
            "GROUP BY bd.productDetail.product " +
            "ORDER BY SUM(bd.quantity) DESC")
    List<Product> findTop4BestSellingProducts(Pageable pageable);

    Product findByProductNameIgnoreCase(String productName);
}
