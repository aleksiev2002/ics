package com.example.ics.controllers;

import com.example.ics.dtos.ImageUrlDto;
import com.example.ics.models.ImageEntity;
import com.example.ics.services.ImageService;
import com.example.ics.utils.RequestThrottler;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/images")
public class ImagesController {

    private final ImageService imageService;
    private final RequestThrottler requestThrottler;

    public ImagesController(ImageService imageService, RequestThrottler requestThrottler) {
        this.imageService = imageService;
        this.requestThrottler = requestThrottler;
    }


    @GetMapping
    public List<ImageEntity> findAllImages() {
        return imageService.getAllImages();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Optional<ImageEntity> get(@PathVariable Long id) {
        return imageService.getById(id);
    }

    @PostMapping
    public Optional<ImageEntity> analyzeImage(@RequestBody ImageUrlDto imageUrlDto) throws MalformedURLException {
        requestThrottler.consumeRequest();

        String imageUrl = imageUrlDto.getImageUrl();

        return imageService.analyzeImage(imageUrl);
    }
}
