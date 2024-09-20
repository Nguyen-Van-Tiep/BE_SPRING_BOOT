package com.example.demo.service.implement;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.Size;
import com.example.demo.enums.ErrorCode;
import com.example.demo.model.info.PaginationInfo;
import com.example.demo.model.request.SizeRequest;
import com.example.demo.model.response.SizeResponse;
import com.example.demo.model.utilities.CommonUtil;
import com.example.demo.repository.SizeRepository;
import com.example.demo.service.SizeService;
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
public class SizeServiceImpl implements SizeService {
    @Autowired
    SizeRepository sizeRepository;

    @Override
    public SizeResponse getSizes(SizeRequest request, int page, int size) throws BusinessException {
        List<Size> allS= sizeRepository.findAll(Sort.by(Sort.Direction.fromString("desc"), "id"));
        if (CommonUtil.isNullOrEmpty(request.getName())) {
            request.setName(""); // nếu request.getName() không có giá trị thì gán bằng giá trị rỗng
        }
        List<Size> filteredS = allS.stream()
                .filter(s -> s.getName().contains(request.getName())).collect(Collectors.toList());
        int start = Math.min(page * size, filteredS.size());
        int end = Math.min((page + 1) * size, filteredS.size());
        List<Size> pagedS= filteredS.subList(start,end);

        SizeResponse sizeResponse = new SizeResponse();
        sizeResponse.setSizes(pagedS);

        PaginationInfo paginationInfo = CommonUtil.getPaginationInfo(page, size, filteredS.size());
        sizeResponse.setPagination(paginationInfo);
        return sizeResponse;
    }

    @Override
    public Size saveSize(Size size) throws BusinessException {
        if (size.getName() == null || size.getName().trim().isEmpty()) {
            throw new BusinessException(ErrorCode.SIZE_NAME_IS_NOT_BLANK,"Tên kích cỡ không được để trống!");
        }
        if(sizeRepository.existsByNameIgnoreCase(size.getName().trim().toLowerCase())){
            throw new BusinessException(ErrorCode.SIZE_NAME_IS_EXIST,"Tên kích cỡ đã tồn tại!");
        }
        return sizeRepository.save(size);
    }
    @Override
    public Size changeStatus(Size size) throws BusinessException {
        return sizeRepository.save(size);
    }

    @Override
    public Size update(Size size) throws BusinessException {
        if (size.getName() == null || size.getName().trim().isEmpty()) {
            throw new BusinessException(ErrorCode.SIZE_NAME_IS_NOT_BLANK,"Tên kích cỡ không được để trống!");
        }
        if(sizeRepository.existsByNameIgnoreCase(size.getName().trim().toLowerCase())){
            throw new BusinessException(ErrorCode.SIZE_NAME_IS_EXIST,"Tên kích cỡ đã tồn tại!");
        }
        return sizeRepository.save(size);
    }


    @Override
    public List<Size> getAllSizes() {
        return sizeRepository.findByStatus(1);
    }
}