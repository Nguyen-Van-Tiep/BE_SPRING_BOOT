package com.example.demo.service.implement;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductDetail;
import com.example.demo.model.info.PaginationInfo;
import com.example.demo.model.request.ProductDetailRequest;
import com.example.demo.model.response.ProductDetailResponse;
import com.example.demo.model.response.ProductDetailsResponse;
import com.example.demo.model.utilities.CommonUtil;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.ProductDetailsRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductDetailsServiceImpl implements ProductDetailsService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductDetailsRepository productDetailsRepository;

    @Autowired
    ImageRepository imageRepository;

    @Override
    public ProductDetailsResponse getProductDetails(ProductDetailRequest request, int page, int size) throws BusinessException {
        List<ProductDetail> allPD = productDetailsRepository.findByProductIdOrderByDesc(request.getProID());

        List<ProductDetail> filteredPD = allPD.stream()
                .filter(dp -> request.getSize() == null || dp.getSize().getId() == request.getSize().getId())
                .filter(dp -> request.getColor() == null || dp.getColor().getId() == request.getColor().getId())
                .collect(Collectors.toList());
        int start = Math.min(page * size, filteredPD.size());
        int end = Math.min((page + 1) * size, filteredPD.size());
        List<ProductDetail> pagedPD = filteredPD.subList(start, end);
        ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse();
        productDetailsResponse.setProductDetails(pagedPD);

        PaginationInfo paginationInfo = CommonUtil.getPaginationInfo(page, size, filteredPD.size());
        productDetailsResponse.setPagination(paginationInfo);
        return productDetailsResponse;
    }

    @Override
    public List<ProductDetail> getAllProductDetails() {
        return productDetailsRepository.findAll();
    }

    @Override
    public List<ProductDetail> updateAll(List<ProductDetail> productDetails) throws BusinessException {
        return productDetailsRepository.saveAll(productDetails);
    }

    @Override
    public ProductDetailResponse getProductDetail(Integer id) throws BusinessException {
        ProductDetailResponse productDetailResponse = new ProductDetailResponse();
        ProductDetail productDetail = productDetailsRepository.findById(id).get();
        productDetailResponse.setProductDetail(productDetail);
        productDetailResponse.setImages(imageRepository.findByProductDetail(productDetail));
        return productDetailResponse;
    }

    @Override
    public ProductDetail updatePD(Integer idPro,ProductDetail productDetail) throws BusinessException {
        ProductDetail productDetail1 = productDetail;
        Product product = productRepository.findById(idPro).get();
        productDetail1.setProduct(product);
        System.out.println(productDetail1.getProduct().getId());
        return productDetailsRepository.save(productDetail1);
//        return productDetail;
    }


    @Override
    public ProductDetail saveProductDetails(ProductDetail ProductDetail) throws BusinessException {
        return null;
    }

    @Override
    public ProductDetail changStatus(Integer id) throws BusinessException {
//        return productDetailsRepository.save(pd);
        ProductDetail productDetail = productDetailsRepository.findById(id).get();
        if(productDetail.getStatus()==1){
            productDetail.setStatus(0);
        }else {
            productDetail.setStatus(1);
        }
        return productDetailsRepository.save(productDetail);
    }




}
