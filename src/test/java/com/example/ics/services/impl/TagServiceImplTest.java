package com.example.ics.services.impl;

import com.example.ics.models.TagEntity;
import com.example.ics.repositories.TagRepository;
import com.example.ics.services.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TagServiceImplTest {

    private TagService tagService;

    @Mock
    private TagRepository tagRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tagService = new TagServiceImpl(tagRepository);
    }

    @Test
    void testGetTagsStartingWithPrefix() {
        String prefix = "abc";
        List<TagEntity> expectedTags = new ArrayList<>();

        TagEntity tag1 = new TagEntity();
        tag1.setId(1L);
        tag1.setName("abc123");
        tag1.setConfidence(80);

        TagEntity tag2 = new TagEntity();
        tag2.setId(2L);
        tag2.setName("abc456");
        tag2.setConfidence(90);

        expectedTags.add(tag1);
        expectedTags.add(tag2);

        when(tagRepository.findTagEntityByNameStartingWith(prefix)).thenReturn(expectedTags);


        List<TagEntity> actualTags = tagService.getTagsStartingWithPrefix(prefix);


        assertEquals(expectedTags, actualTags);
    }

    @Test
    void testGetUniqueTagNames() {

        List<TagEntity> expectedTags = new ArrayList<>();

        TagEntity tag1 = new TagEntity();
        tag1.setId(1L);
        tag1.setName("tag1");
        tag1.setConfidence(80);

        TagEntity tag2 = new TagEntity();
        tag2.setId(2L);
        tag2.setName("tag2");
        tag2.setConfidence(90);

        expectedTags.add(tag1);
        expectedTags.add(tag2);

        when(tagRepository.findTagsByDistinctByUniqueName()).thenReturn(expectedTags);

        List<TagEntity> actualTags = tagService.getUniqueTagNames();

        assertEquals(expectedTags, actualTags);
    }
}
