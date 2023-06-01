package com.example.ics.controllers;

import com.example.ics.models.TagEntity;
import com.example.ics.services.TagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagsController {
    private final TagService tagService;
    public TagsController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<TagEntity> getAllTags(@RequestParam(name = "prefix", defaultValue = "") String prefix) {
        if (prefix.isBlank()) {
            return tagService.getUniqueTagNames();
        }
        return tagService.getTagsStartingWithPrefix(prefix);
    }


}
