package com.example.ics.services.impl;

import com.example.ics.models.TagEntity;
import com.example.ics.repositories.TagRepository;
import com.example.ics.services.TagService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }
    @Override
    public List<TagEntity> getTagsStartingWithPrefix(String prefix) {
        return tagRepository.findTagEntityByNameStartingWith(prefix);
    }
    @Override
    public List<TagEntity> getUniqueTagNames() {
        return tagRepository.findTagsByDistinctByUniqueName();
    }
}
