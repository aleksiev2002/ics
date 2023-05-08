package com.example.ics.services;

import com.example.ics.dto.TagDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ImaggaService {

    List<TagDto> getTagsForImage(String imageUrl) throws JsonProcessingException;
}