package com.example.ics.services.impl;


import com.example.ics.models.ImageEntity;
import com.example.ics.repositories.ImageRepository;
import com.example.ics.services.ImageService;
import com.example.ics.services.ImaggaService;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ImaggaService imaggaService;

    public ImageServiceImpl(ImageRepository imageRepository, ImaggaService imaggaService) {
        this.imageRepository = imageRepository;
        this.imaggaService = imaggaService;
    }

    @Override
    public List<ImageEntity> FindAllImages() {
        return imageRepository.findAll();
    }

    @Override
    public Optional<ImageEntity> getById(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public String analyzeImage(String imageUrl){
        List<String> tags = imaggaService.getTagsForImage(imageUrl);
        return tags.toString();


        //TODO: First you need to check if the image url is already in the db.
        //TODO: Maybe i should create ImaggaService to implement the logic for sending the url to the API.
        //TODO: Implement the logic for the url submission, then sending it to Imagga API, and making a record in the db.
    }
}
