package com.example.ics.services;

import com.example.ics.models.ImageEntity;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    List<ImageEntity> FindAllImages();
    Optional<ImageEntity> getById(Long id);

    void analyzeImage(String imageUrl);

}
