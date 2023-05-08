package com.example.ics.controllers;

import com.example.ics.models.ImageEntity;
import com.example.ics.services.ImageService;
import com.example.ics.utils.ImageUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/images")
public class ImagesController {

    private final ImageService imageService;

    public ImagesController(ImageService imageService) {
        this.imageService = imageService;
    }


    @GetMapping
    public List<ImageEntity> findAllImages() {
        return imageService.FindAllImages();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Optional<ImageEntity> get(@PathVariable Long id) {
        return imageService.getById(id);
    }

    @PostMapping
    public Optional<ImageEntity> analyzeImage(@RequestBody String imageUrl) {
//        if (!ImageUtils.isURLValid(imageUrl)) {
//            return "Invalid URL";
//        }
//        if (!ImageUtils.isImage(imageUrl)) {
//            return "Not an image";
//        }
        return imageService.analyzeImage(imageUrl);
    }
}
