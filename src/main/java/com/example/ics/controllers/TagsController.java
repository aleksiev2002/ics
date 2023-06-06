package com.example.ics.controllers;

import com.example.ics.models.TagEntity;
import com.example.ics.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagsController {

    private final TagService tagService;

    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping
    public List<TagEntity> getAllTags(@RequestParam(name = "prefix", defaultValue = "") String prefix) {
        return tagService.getTagsStartingWithPrefix(prefix);
    }


}
