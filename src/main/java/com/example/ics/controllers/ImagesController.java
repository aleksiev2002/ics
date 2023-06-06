package com.example.ics.controllers;

import com.example.ics.dtos.ImageUrlDto;
import com.example.ics.models.ImageEntity;
import com.example.ics.services.ImageService;
import com.example.ics.utils.RequestThrottler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImagesController {

    private final ImageService imageService;
    private final RequestThrottler requestThrottler;

    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping
    public Page<ImageEntity> findAllImages(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size) {
        PageRequest pr = PageRequest.of(page, size);
        return imageService.getAllImages(pr);
    }
    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping
    @RequestMapping("{id}")
    public ImageEntity get(@PathVariable Long id) {
        Optional<ImageEntity> imageEntityOptional = imageService.getById(id);

        if (imageEntityOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found");
        }

        return imageEntityOptional.get();
    }
    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping
    public Optional<ImageEntity> analyzeImage(@RequestBody ImageUrlDto imageUrlDto) throws MalformedURLException {
        requestThrottler.consumeRequest();

        String imageUrl = imageUrlDto.getImageUrl();

        return imageService.analyzeImage(imageUrl);
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping("/tags")
    public List<ImageEntity> searchImagesByTags(@RequestParam("tags") List<String> tags) {
        return imageService.searchImagesByTags(tags);
    }
}
