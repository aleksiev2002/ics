package com.example.ics.services.impl;

import com.example.ics.models.TagEntity;
import com.example.ics.repositories.TagRepository;
import com.example.ics.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;


    @Override
    public List<TagEntity> getTagsStartingWithPrefix(String prefix) {
        return tagRepository.findTagEntityByNameStartingWith(prefix);
    }

}
