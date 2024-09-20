package com.example.demo.service.implement;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.Brand;
import com.example.demo.entity.Color;
import com.example.demo.enums.ErrorCode;
import com.example.demo.model.info.PaginationInfo;
import com.example.demo.model.request.ColorRequest;
import com.example.demo.model.response.BrandsResponse;
import com.example.demo.model.response.ColorResponse;
import com.example.demo.model.utilities.CommonUtil;
import com.example.demo.repository.ColorRepository;
import com.example.demo.service.ColorService;
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
public class ColorServiceImpl implements ColorService {
    @Autowired
    ColorRepository colorRepository;

    @Override
    public ColorResponse getColors(ColorRequest request, int page, int size) throws BusinessException {
        List<Color> allColors = colorRepository.findAll(Sort.by(Sort.Direction.fromString("desc"), "id"));
        if (CommonUtil.isNullOrEmpty(request.getName())) {
            request.setName(""); // nếu request.getName() không có giá trị thì gán bằng giá trị rỗng
        }
        List<Color> filteredColor = allColors.stream()
                .filter(c -> c.getName().contains(request.getName())).collect(Collectors.toList());
        int start = Math.min(page * size, filteredColor.size());
        int end = Math.min((page + 1) * size, filteredColor.size());
        List<Color> pagedC = filteredColor.subList(start,end);
        ColorResponse colorResponse = new ColorResponse();
        colorResponse.setColors(pagedC);
        PaginationInfo paginationInfo = CommonUtil.getPaginationInfo(page, size, filteredColor.size());
        colorResponse.setPagination(paginationInfo);
        return colorResponse;
    }

    @Override
    public Color saveColor(Color color) throws BusinessException {
        if(colorRepository.existsByNameIgnoreCase(color.getName().trim().toLowerCase())){
            throw new BusinessException(ErrorCode.COLOR_NAME_IS_EXIST,"Tên màu đã tồn tại!");
        }
        if(colorRepository.existsByCodeIgnoreCase(color.getCode().trim().toLowerCase())){
            throw new BusinessException(ErrorCode.COLOR_CODE_IS_EXIST,"Mã màu đã tồn tại!");
        }
        return colorRepository.save(color);
    }

    @Override
    public Color changeStatus(Color color) throws BusinessException {
        return colorRepository.save(color);
    }

    @Override
    public Color update(Color color) throws BusinessException {
        if(colorRepository.existsByNameIgnoreCase(color.getName().trim().toLowerCase())){
            throw new BusinessException(ErrorCode.COLOR_NAME_IS_EXIST,"Tên màu đã tồn tại!");
        }
        if(colorRepository.existsByCodeIgnoreCase(color.getCode().trim().toLowerCase())){
            throw new BusinessException(ErrorCode.COLOR_CODE_IS_EXIST,"Mã màu đã tồn tại!");
        }
        return colorRepository.save(color);
    }

    //admin lấy ra list đang hoạt động
    @Override
    public List<Color> getAllColors() {
        return colorRepository.findByStatus(1);
    }
}
