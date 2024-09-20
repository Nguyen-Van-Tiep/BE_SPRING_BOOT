package com.example.demo.service.implement;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.Brand;
import com.example.demo.entity.Style;
import com.example.demo.enums.ErrorCode;
import com.example.demo.model.info.PaginationInfo;
import com.example.demo.model.request.StyleRequest;
import com.example.demo.model.response.BrandsResponse;
import com.example.demo.model.response.StyleResponse;
import com.example.demo.model.utilities.CommonUtil;
import com.example.demo.repository.StyleRepository;
import com.example.demo.service.StyleService;
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
public class StyleServiceImpl implements StyleService {
    @Autowired
    StyleRepository styleRepository;

    @Override
    public StyleResponse getStyles(StyleRequest request, int page, int size) throws BusinessException {
        List<Style> all = styleRepository.findAll(Sort.by(Sort.Direction.fromString("desc"), "id"));
        if (CommonUtil.isNullOrEmpty(request.getName())) {
            request.setName(""); // nếu request.getName() không có giá trị thì gán bằng giá trị rỗng
        }
        List<Style> filtered = all.stream()
                .filter(s -> s.getName().contains(request.getName())).collect(Collectors.toList());
        int start = Math.min(page * size, filtered.size());
        int end = Math.min((page + 1) * size, filtered.size());
        List<Style> paged = filtered.subList(start,end);
        StyleResponse styleResponse = new StyleResponse();
        styleResponse.setStyles(paged);
        PaginationInfo paginationInfo = CommonUtil.getPaginationInfo(page, size, filtered.size());
        styleResponse.setPagination(paginationInfo);
        return styleResponse;
    }

    @Override
    public Style saveStyle(Style style) throws BusinessException {
        if (style.getName() == null || style.getName().trim().isEmpty()) {
            throw new BusinessException(ErrorCode.STYLE_NAME_IS_NOT_BLANK,"Tên kiểu dáng không được để trống!");
        }
        if(styleRepository.existsByNameIgnoreCase(style.getName().trim().toLowerCase())){
            throw new BusinessException(ErrorCode.STYLE_NAME_IS_EXIST,"Tên kiểu dáng đã tồn tại!");
        }
        return styleRepository.save(style);
    }

    @Override
    public Style changeStatus(Style style) throws BusinessException {
        return styleRepository.save(style);
    }

    @Override
    public Style update(Style style) throws BusinessException {
        return styleRepository.save(style);
    }


    @Override
    public List<Style> getAllStyles() {
        return styleRepository.findByStatus(1);
    }

    //custom lấy ra tất cả
    @Override
    public List<Style> getAllStylesForC() {
        return styleRepository.findAll();}
}
