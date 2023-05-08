package com.example.ics.services.impl;


import com.example.ics.dto.TagDto;
import com.example.ics.models.ImageEntity;
import com.example.ics.models.TagEntity;
import com.example.ics.repositories.ImageRepository;
import com.example.ics.repositories.TagRepository;
import com.example.ics.services.ImageService;
import com.example.ics.services.ImaggaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ImaggaService imaggaService;

    private final TagRepository tagRepository;

    public ImageServiceImpl(ImageRepository imageRepository, ImaggaService imaggaService, TagRepository tagRepository) {
        this.imageRepository = imageRepository;
        this.imaggaService = imaggaService;
        this.tagRepository = tagRepository;
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
    public Optional<ImageEntity> analyzeImage(String imageUrl){
        //Check if image already exists in DB
        if (imageRepository.existsByUrl(imageUrl)) {
            return imageRepository.findByUrl(imageUrl);
        }
        List<TagDto> tagDtos;
        try {
            tagDtos = imaggaService.getTagsForImage(imageUrl);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        // Create new image entity
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setUrl(imageUrl);
        try {
            Dimension imageSize = getImageDimensions(imageUrl);
            imageEntity.setWidth(imageSize.width);
            imageEntity.setHeight(imageSize.height);
        } catch (Exception e) {
            // Handle exception if the image dimensions cannot be obtained
            e.printStackTrace();
        }


        // Create tag entities and add them to image entity
        List<TagEntity> tagEntities = new ArrayList<>();
        for (TagDto tagDto : tagDtos) {
            TagEntity tag = new TagEntity();
            tag.setName(tagDto.getName());
            tag.setConfidence(tagDto.getConfidence());
            if(tagRepository.existsByName(tagDto.getName())){
                tag = tagRepository.findByName(tagDto.getName());
            } else {
                tagRepository.save(tag);
            }
            tagEntities.add(tag);
        }

        imageEntity.setTags(tagEntities);

        // Save image entity to database
        imageRepository.save(imageEntity);

        return imageRepository.findByUrl(imageUrl);
    }

    private Dimension getImageDimensions(String imageUrl) throws Exception {
        URL imageSizeUrl = new URL(imageUrl);
        BufferedImage image = ImageIO.read(imageSizeUrl);
        int width = image.getWidth();
        int height = image.getHeight();
        return new Dimension(width, height);
    }


}
