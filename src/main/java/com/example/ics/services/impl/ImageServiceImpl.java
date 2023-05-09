package com.example.ics.services.impl;


import com.example.ics.dto.TagDto;
import com.example.ics.models.ImageEntity;
import com.example.ics.models.TagEntity;
import com.example.ics.repositories.ImageRepository;
import com.example.ics.repositories.TagRepository;
import com.example.ics.services.ImageService;
import com.example.ics.services.ImaggaService;
import com.example.ics.utils.ImageUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
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
    public Optional<ImageEntity> analyzeImage(String imageUrl) throws MalformedURLException {
        //Checks if the image URL is valid and if it's an image.
        validateImage(imageUrl);
        //Check if image already exists in DB
        Optional<ImageEntity> existingImage = getImageByUrl(imageUrl);
        if (existingImage.isPresent()) {
            return existingImage;
        }

        List<TagDto> tagDtos = getTagsForImage(imageUrl);

        // Create new image entity, with all related tags
        ImageEntity imageEntity = createImageEntity(imageUrl, tagDtos);
        saveImageEntity(imageEntity);
        return getImageByUrl(imageUrl);
    }

    private Dimension getImageDimensions(String imageUrl) throws IOException {

        URL imageSizeUrl;
        try {
            imageSizeUrl = new URL(imageUrl);
            BufferedImage image = ImageIO.read(imageSizeUrl);
            int width = image.getWidth();
            int height = image.getHeight();
            return new Dimension(width, height);
        } catch (IOException e) {
            throw new IOException("Cannot get image dimensions", e);
        }

    }

    private void validateImage(String imageUrl) throws MalformedURLException {
        if (!ImageUtils.isURLValid(imageUrl)) {
            throw new MalformedURLException("Invalid URL");
        }
        if (!ImageUtils.isImage(imageUrl)) {
            throw new MalformedURLException("Not an image");
        }
    }

    private Optional<ImageEntity> getImageByUrl(String imageUrl) {
        return imageRepository.findByUrl(imageUrl);
    }

    private List<TagDto> getTagsForImage(String imageUrl) {
        try {
            return imaggaService.getTagsForImage(imageUrl);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Cannot get tags for image", e);
        }
    }

    private ImageEntity createImageEntity(String imageUrl, List<TagDto> tagDtos) {
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
        List<TagEntity> tagEntities = createTagEntities(tagDtos);
        imageEntity.setTags(tagEntities);
        return imageEntity;
    }

    private List<TagEntity> createTagEntities(List<TagDto> tagDtos) {
        List<TagEntity> tagEntities = new ArrayList<>();
        for (TagDto tagDto : tagDtos) {
            TagEntity tagEntity = createTagEntity(tagDto);
            tagEntities.add(tagEntity);
        }
        return tagEntities;
    }

    private TagEntity createTagEntity(TagDto tagDto) {
        String tagName = tagDto.getName();
        TagEntity tagEntity = tagRepository.findByName(tagName);
        if (tagEntity == null) {
            tagEntity = new TagEntity();
            tagEntity.setName(tagName);
            tagRepository.save(tagEntity);
        }
        tagEntity.setConfidence(tagDto.getConfidence());
        return tagEntity;
    }

    private void saveImageEntity(ImageEntity imageEntity) {
        imageRepository.save(imageEntity);
    }

}
