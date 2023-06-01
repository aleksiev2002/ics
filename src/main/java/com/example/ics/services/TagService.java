package com.example.ics.services;

import com.example.ics.models.TagEntity;

import java.util.List;

public interface TagService {
    List<TagEntity> getTagsStartingWithPrefix(String prefix);

    List<TagEntity> getUniqueTagNames();
}
