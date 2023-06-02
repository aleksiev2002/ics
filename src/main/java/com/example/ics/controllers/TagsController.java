package com.example.ics.controllers;

import com.example.ics.models.TagEntity;
import com.example.ics.services.TagService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagsController {
    private final TagService tagService;
    public TagsController(TagService tagService) {
        this.tagService = tagService;
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping
    public List<TagEntity> getAllTags(@RequestParam(name = "prefix", defaultValue = "") String prefix) {
        if (prefix.isBlank()) {
            return tagService.getUniqueTagNames();
        }
        return tagService.getTagsStartingWithPrefix(prefix);
    }


}
