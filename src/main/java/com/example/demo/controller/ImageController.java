package com.example.demo.controller;

import com.example.demo.model.request.ImageRequest;
import com.example.demo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1.0/auth/image")
@CrossOrigin(origins = "*")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PostMapping("/uploads")
    public ResponseEntity<?> uploadImage(@RequestBody List<ImageRequest> requests) {
        for (ImageRequest i : requests){
            System.out.println(i.getName());
        }
        return ResponseEntity.ok().body(requests);
    }

}
