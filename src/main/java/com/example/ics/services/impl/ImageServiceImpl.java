package com.example.ics.services.impl;


import com.example.ics.models.ImageEntity;
import com.example.ics.repositories.ImageRepository;
import com.example.ics.services.ImageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public List<ImageEntity> FindAllImages() {
        return imageRepository.findAll();
    }

    @Override
    public Optional<ImageEntity> getById(Long id) {
        return imageRepository.findById(id);
    }
}
