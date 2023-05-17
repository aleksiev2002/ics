package com.example.ics.services.impl;


import com.example.ics.models.ImageEntity;
import com.example.ics.repositories.ImageRepository;
import com.example.ics.repositories.TagRepository;
import com.example.ics.services.ImaggaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    void canGetAllImages() {
        //when
        underTest.getAllImages();
        //then
        verify(imageRepository).findAll();
    }


    @Test
    void getById() {
        //given
        given(imageRepository.findById(1L)).willReturn(Optional.of(imageEntity));

        //when
        ImageEntity savedImage = imageRepository.findById(imageEntity.getId()).get();

        assertThat(savedImage).isNotNull();
    }


}