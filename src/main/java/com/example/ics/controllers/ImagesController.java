package com.example.ics.controllers;

import com.example.ics.models.ImageEntity;
import com.example.ics.services.ImageService;
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
    public String analyzeImage(@RequestBody String imageUrl) {

            return imageService.analyzeImage(imageUrl);
    }
}
