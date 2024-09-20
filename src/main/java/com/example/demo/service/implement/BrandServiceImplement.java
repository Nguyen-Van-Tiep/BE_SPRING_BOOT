package com.example.demo.service.implement;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.Brand;
import com.example.demo.enums.ErrorCode;
import com.example.demo.model.info.PaginationInfo;
import com.example.demo.model.request.BrandsRequest;
import com.example.demo.model.response.BrandsResponse;
import com.example.demo.model.utilities.CommonUtil;
import com.example.demo.repository.BrandRepository;
import com.example.demo.service.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class BrandServiceImplement implements BrandService {
    private final BrandRepository brandRepository;


    @Override
    public BrandsResponse getBrands(BrandsRequest request, int page, int size) throws BusinessException {
        List<Brand> allBrand = brandRepository.findAll(Sort.by(Sort.Direction.fromString("desc"), "id"));
        if (CommonUtil.isNullOrEmpty(request.getName())) {
            request.setName(""); // nếu request.getName() không có giá trị thì gán bằng giá trị rỗng
        }
        List<Brand> filteredBrand = allBrand.stream()
                .filter(brand -> brand.getName().contains(request.getName())).collect(Collectors.toList());
//        List<Brand> filteredBrand = allBrand.stream().collect(Collectors.toList());
        int start = Math.min(page * size, filteredBrand.size());
        int end = Math.min((page + 1) * size, filteredBrand.size());
        List<Brand> pagedBrand = filteredBrand.subList(start,end);
        BrandsResponse brandsResponse = new BrandsResponse();
        brandsResponse.setBrands(pagedBrand);
        PaginationInfo paginationInfo = CommonUtil.getPaginationInfo(page, size, filteredBrand.size());
        brandsResponse.setPagination(paginationInfo);
        return brandsResponse;
    }

    @Override
    public Brand saveBrand(Brand brand) throws BusinessException {
        if (brand.getName() == null || brand.getName().trim().isEmpty()) {
            throw new BusinessException(ErrorCode.BRAND_NAME_IS_NOT_BLANK,"Tên thương hiệu không được để trống!");
        }
        if(brandRepository.existsByNameIgnoreCase(brand.getName().trim().toLowerCase())){
            throw new BusinessException(ErrorCode.BRAND_NAME_IS_EXIST,"Tên thương hiệu đã tồn tại!");
        }
        return brandRepository.save(brand);
    }

    @Override
    public Brand changeStatus(Brand brand) throws BusinessException {
        return brandRepository.save(brand);
    }

    @Override
    public Brand update(Brand brand) throws BusinessException {
        if (brand.getName() == null || brand.getName().trim().isEmpty()) {
            throw new BusinessException(ErrorCode.BRAND_NAME_IS_NOT_BLANK,"Tên thương hiệu không được để trống!");
        }
        if(brandRepository.existsByNameIgnoreCase(brand.getName().trim().toLowerCase())){
            throw new BusinessException(ErrorCode.BRAND_NAME_IS_EXIST,"Tên thương hiệu đã tồn tại!");
        }
        return brandRepository.save(brand);
    }


    //admin lấy ra list đang hoạt động
    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findByStatus(1);
    }

    //custom lấy ra tất cả
    @Override
    public List<Brand> getAllBrandsForC() {
        return brandRepository.findAll();
    }

    /**
     * @param request
     * @return
     */
//    @Override
//    public BrandResponse saveBrand(SaveBrandRequest request, String username) throws BusinessException {
//        String name = request.getName();
//        Brand brand = new Brand();
//        if (request.getId() == 0) {
//            brand.setCreatedBy(username);
//        } else {
//            brand = brandRepository.findCategoryByName(name);
//            if (brand != null && brand.getId() != request.getId()) {
//                throw new BusinessException(ErrorCode.CATEGORY_ALREADY_EXISTS);
//            }
//            brand = brandRepository.findById(request.getId()).orElse(null);
//            if (brand == null) {
//                throw new BusinessException(ErrorCode.CATEGORY_NOT_FOUND);
//            }
//        }
//        brand.setCountry(request.getCountry());
//        brand.setDate(request.getDate());
//        brand.setName(name);
//        brand.setStatus(request.getStatus());
//        brand.setModifiedBy(username);
//        brand.setDescription(request.getDescription());
//        if (request.getImage() != 0) {
//            brand.setImage(request.getImage());
//        }
//        brand.setProductCount(0);
//        brand.setStylistCount(0);
//        brandRepository.save(brand);
//        saveStylist(brand.getId(), request.getStyles(), username);
//        return convertToResponse(brand);
//    }

    /**
     * @param request
     * @param page
     * @param size
     * @return
     * @throws BusinessException
     */
//    @Override
//    public BrandsResponse getBrands(BrandsRequest request, int page, int size, String sortField, String sortType) throws BusinessException {
//        log.info("BrandService getBrands request: {}, page: {}, size: {}", request, page, size);
//        String name = request.getName();
//        Pageable pageable = null;
//        if (CommonUtil.isNullOrEmpty(sortField)) {
//            pageable = PageRequest.of(page, size);
//        } else {
//            Sort sort = Sort.by(Sort.Direction.fromString(sortType), sortField);
//            pageable = PageRequest.of(page, size, sort);
//        }
//        Page<Brand> brands = brandRepository.findByNameContaining(name, pageable);
//        BrandsResponse response = new BrandsResponse();
//        List<BrandResult> brandResults = new ArrayList<>();
//        for (Brand brand : brands.getContent()) {
//            BrandResult brandResult = new BrandResult();
//            brandResult.setId(brand.getId());
//            brandResult.setName(brand.getName());
//            brandResult.setDescription(brand.getDescription());
//            if (!CommonUtil.isNullOrEmpty(brand.getCountry())) {
//                brandResult.setCountryName(CommonUtil.getCountryName(brand.getCountry()));
//                brandResult.setCountryCode(brand.getCountry());
//            }
//            brandResult.setStatusCode(brand.getStatus());
//            brandResult.setDate(brand.getDate());
//            brandResult.setStatus(CommonUtil.getStatusVn(brand.getStatus()));
//            brandResult.setStylistCount(brand.getStylistCount());
//            if (brand.getImage() != 0) {
//                brandResult.setLogo(this.fileAttachmentRepository.findUrlByFileId(brand.getImage()));
//            }
//            brandResult.setProductCount(Math.toIntExact(this.productRepository.countByBrandId(brand.getId())));
//            brandResults.add(brandResult);
//        }
//        PaginationInfo paginationInfo = CommonUtil.getPaginationInfo(brands.getNumber(), brands.getSize(), Math.toIntExact(brands.getTotalElements()));
//        response.setBrands(brandResults);
//        response.setPagination(paginationInfo);
//        return response;
//    }
//
//    /**
//     * @param id
//     * @param username
//     * @return
//     * @throws BusinessException
//     */
//    @Override
//    public BrandResponse deleteBrand(int id, String username) throws BusinessException {
//        log.info("BrandService deleteBrand id: {}, username: {}", id, username);
//        BrandResponse response = new BrandResponse();
//        Brand brand = brandRepository.findById(id).orElse(null);
//        if (brand == null) {
//            throw new BusinessException(ErrorCode.CATEGORY_NOT_FOUND);
//        }
//        brand.setStatus(Constant.STATUS_STYLES.DELETED);
//        brand.setModifiedBy(username);
//        brandRepository.save(brand);
//        response.setId(brand.getId());
//        return response;
//    }
//
//    /**
//     * @param id
//     * @return
//     * @throws BusinessException
//     */
//    @Override
//    public BrandResponse getBrand(int id) throws BusinessException {
//        log.info("BrandService getBrand id: {}", id);
//        Brand brand = brandRepository.findById(id).orElse(null);
//        if (brand == null) {
//            throw new BusinessException(ErrorCode.CATEGORY_NOT_FOUND);
//        }
//        List<Style> styles = styleRepository.findByParentIdAndStatus(id);
//        List<StylistResult> stylistResults = new ArrayList<>();
//        for (Style style : styles) {
//            stylistResults.add(new StylistResult(style.getId(), style.getName(), style.getStatus()));
//        }
//        BrandResponse response = convertToResponse(brand);
//        response.setStyles(stylistResults);
//        return response;
//    }
//
//    private void saveStylist(int parentId, List<StylistResult> stylists, String username) {
//        List<Style> styleList = new ArrayList<>();
//        if (stylists != null && !stylists.isEmpty()) {
//            int stylistCount = 0;
//            for (StylistResult stylist : stylists) {
//                if (stylist.getId()<0){
//                    Style style = new Style();
//                    style.setParentId(parentId);
//                    style.setStatus(Constant.STATUS_STYLES.ACTIVE);
//                    style.setName(stylist.getName());
//                    style.setCreatedBy(username);
//                    style.setModifiedBy(username);
//                    styleList.add(style);
//                    stylistCount++;
//                }else {
//                    Style style = styleRepository.findById(Long.valueOf(stylist.getId())).orElse(null);
//                    if (style == null) {
//                        continue;
//                    }
//
//                    style.setName(stylist.getName());
//                    style.setStatus(stylist.getStatus());
//                    style.setModifiedBy(username);
//                    styleList.add(style);
//
//                    if (!Constant.STATUS_STYLES.DELETED.equals(stylist.getStatus())) {
//                        stylistCount++;
//                    }
//                }
//            }
//            this.styleRepository.saveAll(styleList);
//            Brand brand = this.brandRepository.findById(parentId).orElse(null);
//            if (brand == null) {
//                return;
//            }
//            brand.setStylistCount(stylistCount);
//            this.brandRepository.save(brand);
//        }
//    }
//
//    private BrandResponse convertToResponse(Brand brand) {
//        BrandResponse response = new BrandResponse();
//        response.setId(brand.getId());
//        response.setName(brand.getName());
//        response.setDescription(brand.getDescription());
//        if (brand.getImage() != 0) {
//            response.setImage(this.fileAttachmentRepository.findUrlByFileId(brand.getImage()));
//        }
//        response.setDate(CommonUtil.str2Date(brand.getDate()));
//        if (!CommonUtil.isNullOrEmpty(brand.getCountry())) {
//            BaseResult country = new BaseResult();
//            country.setCode(brand.getCountry());
//            country.setName(CommonUtil.getCountryName(brand.getCountry()));
//            response.setCountry(country);
//        }
//        BaseResult status = new BaseResult();
//        status.setCode(brand.getStatus());
//        status.setName(CommonUtil.getStatusVn(brand.getStatus()));
//        response.setStatus(status);
//        return response;
//    }
}