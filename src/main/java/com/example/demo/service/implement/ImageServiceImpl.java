package com.example.demo.service.implement;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.Image;
import com.example.demo.repository.ImageRepository;
import com.example.demo.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    @Autowired
    ImageRepository imageRepository;

//    @Value("${file.url-dir}")
//    private String urlDir;

    //    @Override
//    public List<Integer> saveImage(MultipartFile[] files) throws BusinessException {
//        List<Image> images = new ArrayList<>();
//        String fileId = UUID.randomUUID().toString();
//        String date = CommonUtil.date2Str(new Date());
//        String baseURL = urlDir +"/" + fileId;
//        File dir = new File(baseURL);
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//        for (MultipartFile file : files) {
//            Image image = new Image();
//            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
//            if (file.isEmpty()) {
//                continue;
//            }
//            String url = baseURL + "/" + fileName;
//            try {
//                java.io.File targetLocation = new java.io.File(url);
//                file.transferTo(targetLocation);
//                image.setUrl(url);
//                image.setProductDetails(pd);
//                images.add(image);
//            } catch (Exception e) {
//                log.error("uploadFile: error={}", e.getMessage());
//            }
//        }
//        images = imageRepository.saveAll(images);
//        List<Integer>ids= new ArrayList<>();
//        for (Image image : images){
//                ids.add(image.getId());
//        }
//        return ids;
//    }
    @Override
    public Image saveImage(Image image) throws BusinessException {
        return imageRepository.save(image);
    }

    @Override
    public Image deleteImage(Integer id) throws BusinessException {
        return null;
    }

    @Override
    public Image getImage(Integer id) throws BusinessException {
        return null;
    }

    @Override
    public List<Image> getAllImages() {
        return null;
    }
}
