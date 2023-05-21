package com.example.ics.controllers;

import com.example.ics.dtos.ImageUrlDto;
import com.example.ics.models.ImageEntity;
import com.example.ics.services.ImageService;
import com.example.ics.utils.RequestThrottler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
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

    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping
    public Page<ImageEntity> findAllImages(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size) {
        PageRequest pr = PageRequest.of(page, size);
        return imageService.getAllImages(pr);
    }

    @GetMapping
    @RequestMapping("{id}")
    public ImageEntity get(@PathVariable Long id) {
        Optional<ImageEntity> imageEntityOptional = imageService.getById(id);

        if (imageEntityOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found");
        }

        return imageEntityOptional.get();
    }

    @PostMapping
    public Optional<ImageEntity> analyzeImage(@RequestBody ImageUrlDto imageUrlDto) throws MalformedURLException {
        requestThrottler.consumeRequest();

        String imageUrl = imageUrlDto.getImageUrl();

        return imageService.analyzeImage(imageUrl);
    }
}
