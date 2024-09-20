package com.example.demo.service.implement;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.Brand;
import com.example.demo.entity.Material;
import com.example.demo.entity.Sole;
import com.example.demo.enums.ErrorCode;
import com.example.demo.model.info.PaginationInfo;
import com.example.demo.model.request.SoleRequest;
import com.example.demo.model.response.BrandsResponse;
import com.example.demo.model.response.SoleResponse;
import com.example.demo.model.utilities.CommonUtil;
import com.example.demo.repository.SoleRepository;
import com.example.demo.service.SoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class SoleServiceImpl implements SoleService {
    @Autowired
    SoleRepository soleRepository;

    @Override
    public SoleResponse getSoles(SoleRequest request, int page, int size) throws BusinessException {
        List<Sole> all = soleRepository.findAll(Sort.by(Sort.Direction.fromString("desc"), "id"));
        if (CommonUtil.isNullOrEmpty(request.getName())) {
            request.setName(""); // nếu request.getName() không có giá trị thì gán bằng giá trị rỗng
        }
        List<Sole> filtered = all.stream()
                .filter(s -> s.getName().contains(request.getName())).collect(Collectors.toList());
        int start = Math.min(page * size, filtered.size());
        int end = Math.min((page + 1) * size, filtered.size());
        List<Sole> pagedS = filtered.subList(start,end);
        SoleResponse soleResponse = new SoleResponse();
        soleResponse.setSoles(pagedS);
        PaginationInfo paginationInfo = CommonUtil.getPaginationInfo(page, size, filtered.size());
        soleResponse.setPagination(paginationInfo);
        return soleResponse;
    }

    @Override
    public Sole saveSole(Sole sole) throws BusinessException {
        if (sole.getName() == null || sole.getName().trim().isEmpty()) {
            throw new BusinessException(ErrorCode.SOLE_NAME_IS_NOT_BLANK,"Tên đế giày không được để trống!");
        }
        if(soleRepository.existsByNameIgnoreCase(sole.getName().trim().toLowerCase())){
            throw new BusinessException(ErrorCode.SOLE_NAME_IS_EXIST,"Tên đế giày đã tồn tại!");
        }
        return soleRepository.save(sole);
    }

    @Override
    public Sole changeStatus(Sole sole) throws BusinessException {
        return soleRepository.save(sole);
    }

    @Override
    public Sole update(Sole sole) throws BusinessException {
        if (sole.getName() == null || sole.getName().trim().isEmpty()) {
            throw new BusinessException(ErrorCode.SOLE_NAME_IS_NOT_BLANK,"Tên đế giày không được để trống!");
        }
        if(soleRepository.existsByNameIgnoreCase(sole.getName().trim().toLowerCase())){
            throw new BusinessException(ErrorCode.SOLE_NAME_IS_EXIST,"Tên đế giày đã tồn tại!");
        }
        return soleRepository.save(sole);
    }


    @Override
    public List<Sole> getAllSoles() {
        return soleRepository.findByStatus(1);
    }

    //custom lấy ra tất cả
    @Override
    public List<Sole> getAllSolesForC() {
        return soleRepository.findAll();}
}