package com.example.demo.service.implement;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.Brand;
import com.example.demo.entity.Material;
import com.example.demo.enums.ErrorCode;
import com.example.demo.model.info.PaginationInfo;
import com.example.demo.model.request.MaterialRequest;
import com.example.demo.model.response.BrandsResponse;
import com.example.demo.model.response.MaterialResponse;
import com.example.demo.model.utilities.CommonUtil;
import com.example.demo.repository.MaterialRepository;
import com.example.demo.service.MaterialService;
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
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    MaterialRepository materialRepository;

    @Override
    public MaterialResponse getMaterials(MaterialRequest request, int page, int size) throws BusinessException {
        List<Material> allM = materialRepository.findAll(Sort.by(Sort.Direction.fromString("desc"), "id"));
        if (CommonUtil.isNullOrEmpty(request.getName())) {
            request.setName(""); // nếu request.getName() không có giá trị thì gán bằng giá trị rỗng
        }
        List<Material> filteredM= allM.stream()
                .filter(m -> m.getName().contains(request.getName())).collect(Collectors.toList());
        int start = Math.min(page * size, filteredM.size());
        int end = Math.min((page + 1) * size, filteredM.size());
        List<Material> pagedM = filteredM.subList(start,end);
        MaterialResponse materialResponse = new MaterialResponse();
        materialResponse.setMaterials(pagedM);
        PaginationInfo paginationInfo = CommonUtil.getPaginationInfo(page, size, filteredM.size());
        materialResponse.setPagination(paginationInfo);
        return materialResponse;
    }

    @Override
    public Material saveMaterial(Material material) throws BusinessException {
        if (material.getName() == null || material.getName().trim().isEmpty()) {
            throw new BusinessException(ErrorCode.MATERIAL_NAME_IS_NOT_BLANK,"Tên chất liệu không được để trống!");
        }
        if(materialRepository.existsByNameIgnoreCase(material.getName().trim().toLowerCase())){
            throw new BusinessException(ErrorCode.MATERIAL_NAME_IS_EXIST,"Tên chất liệu đã tồn tại!");
        }
        return materialRepository.save(material);
    }

    @Override
    public Material changeStatus(Material material) throws BusinessException {
        return materialRepository.save(material);
    }

    @Override
    public Material update(Material material) throws BusinessException {
        if (material.getName() == null || material.getName().trim().isEmpty()) {
            throw new BusinessException(ErrorCode.MATERIAL_NAME_IS_NOT_BLANK,"Tên chất liệu không được để trống!");
        }
        if(materialRepository.existsByNameIgnoreCase(material.getName().trim().toLowerCase())){
            throw new BusinessException(ErrorCode.MATERIAL_NAME_IS_EXIST,"Tên chất liệu đã tồn tại!");
        }
        return materialRepository.save(material);
    }

    //admin lấy ra list đang hoạt động
    @Override
    public List<Material> getAllMaterials() {
        return materialRepository.findByStatus(1);
    }

    //custom lấy ra tất cả
    @Override
    public List<Material> getAllMaterialsForC() {
        return materialRepository.findAll();}
}
