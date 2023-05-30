package com.example.ics.services.impl;


import com.example.ics.dtos.TagDto;
import com.example.ics.models.ImageEntity;
import com.example.ics.models.TagEntity;
import com.example.ics.repositories.ImageRepository;
import com.example.ics.repositories.TagRepository;
import com.example.ics.services.ImaggaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {
    private ImageServiceImpl underTest;
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private ImaggaService imaggaService;
    @Mock
    private TagRepository tagRepository;

    private ImageEntity imageEntity;

    @BeforeEach
    public void setup() {
        underTest = new ImageServiceImpl(imageRepository, imaggaService, tagRepository);
        imageEntity = ImageEntity.builder()
                .id(1L)
                .url("https://example.com/image.jpg")
                .build();
    }

    @Test
    void testCanGetAllImages() {
        PageRequest pageRequest = PageRequest.of(0, 3);
        //when
        underTest.getAllImages(pageRequest);
        //then
        ArgumentCaptor<PageRequest> pageRequestCaptor = ArgumentCaptor.forClass(PageRequest.class);
        verify(imageRepository).findAll(pageRequestCaptor.capture());

        PageRequest capturedPageRequest = pageRequestCaptor.getValue();
        assertEquals(0, capturedPageRequest.getPageNumber());
        assertEquals(3, capturedPageRequest.getPageSize());
    }


    @Test
    void testGetById() {
        //given
        given(imageRepository.findById(1L)).willReturn(Optional.of(imageEntity));

        //when
        ImageEntity savedImage = imageRepository.findById(imageEntity.getId()).get();
        assertThat(savedImage).isNotNull();
    }

    @Test
    void testValidateImageWithValidImageUrlThatDoesNotThrowException() {
        String validImageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQR4VQpX7c7EKb79RfxZO72GjtXIVZas4vbIV97dayP&s";

        assertDoesNotThrow(() -> underTest.validateImage(validImageUrl));
    }

    @Test
    void testValidateImageWithInvalidImageUrlThatThrowsMalformedURLException() {
        String invalidImageUrl = "https://example.com/not-an-image.txt";

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> underTest.validateImage(invalidImageUrl));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Invalid URL", exception.getReason());
    }


    @Test
    void testCreateTagEntityWithValidTagDtoThatReturnsTagEntityWithCorrectProperties() {
        // Mock dependencies
        TagDto tagDto = new TagDto();
        tagDto.setName("Tag Name");
        tagDto.setConfidence(80);

        // Call the method
        TagEntity result = underTest.createTagEntity(tagDto);

        // Assert the result
        assertThat(result.getName()).isEqualTo(tagDto.getName());
        assertThat(result.getConfidence()).isEqualTo(tagDto.getConfidence());
    }

    @Test
    void testCreateTagEntitiesWithValidTagDtosThatReturnsListOfTagEntities() {

        List<TagDto> tagDtos = new ArrayList<>();
        TagDto tagDto1 = new TagDto();
        tagDto1.setName("Tag 1");
        tagDto1.setConfidence(20);
        TagDto tagDto2 = new TagDto();
        tagDto2.setName("Tag 2");
        tagDto2.setConfidence(10);
        tagDtos.add(tagDto1);
        tagDtos.add(tagDto2);


        List<TagEntity> result = underTest.createTagEntities(tagDtos);
        assertThat(result).hasSize(tagDtos.size());
    }

//TODO: Create tests for - analyzeImage, getImageDimensions, getTagsForImage, createImageEntity
}