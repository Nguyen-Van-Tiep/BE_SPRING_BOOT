package com.example.demo.controller;

import com.example.demo.model.DTO.ProductDTO;
import com.example.demo.model.DTO.StatisticsDto;
import com.example.demo.model.result.StatisticsResult;
import com.example.demo.service.implement.BillServiceImplement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1.0/dash-board")

public class StatisticsController {
    @Autowired
    private BillServiceImplement billService;

    @GetMapping("/day")
    public StatisticsDto getStatisticsByDay() {
        return billService.getStatisticsByDay();
    }

    @GetMapping("/week")
    public ResponseEntity<StatisticsResult> getStatisticsByWeek() {
        StatisticsResult statistics = billService.getWeeklyStatistics();
        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/month")
    public StatisticsDto getStatisticsByMonth() {
        return billService.getStatisticsByMonth();
    }

    @GetMapping("/year")
    public StatisticsDto getStatisticsByYear() {
        return billService.getStatisticsByYear();
    }

    @GetMapping("/top-selling-products/today")
    public List<ProductDTO> getTopSellingProductsByDay() {
        return billService.getTopSellingProductsByDay();
    }

    @GetMapping("/top-selling-products/this-week")
    public List<ProductDTO> getTopSellingProductsByWeek(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return billService.getTopSellingProductsByWeek(startDate, endDate);
    }


    @GetMapping("/top-selling-products/this-month")
    public List<ProductDTO> getTopSellingProductsByMonth() {
        return billService.getTopSellingProductsByMonth();
    }

    @GetMapping("/top-selling-products/this-year")
    public List<ProductDTO> getTopSellingProductsByYear() {
        return billService.getTopSellingProductsByYear();
    }

    @GetMapping("/top-selling-products/custom-range")
    public List<ProductDTO> getTopSellingProductsByCustomRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return billService.getTopSellingProductsByCustomRange(startDate, endDate);
    }
}
