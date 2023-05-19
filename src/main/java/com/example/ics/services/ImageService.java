package com.example.ics.services;

import com.example.ics.dtos.TagDto;
import com.example.ics.models.ImageEntity;
import com.example.ics.models.TagEntity;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

public interface ImageService {
    List<ImageEntity> getAllImages();

    Optional<ImageEntity> getById(Long id);

    Optional<ImageEntity> analyzeImage(String imageUrl) throws MalformedURLException;

    Dimension getImageDimensions(String imageUrl) throws IOException;

    List<TagDto> getTagsForImage(String imageUrl);

    ImageEntity createImageEntity(String imageUrl, List<TagDto> tagDtos, String checksum);

    TagEntity createTagEntity(TagDto tagDto);

    List<TagEntity> createTagEntities(List<TagDto> tagDtos);

    void validateImage(String imageUrl) throws MalformedURLException;
}