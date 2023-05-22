package com.example.ics.services.impl;


import com.example.ics.dtos.TagDto;
import com.example.ics.models.ImageEntity;
import com.example.ics.models.TagEntity;
import com.example.ics.repositories.ImageRepository;
import com.example.ics.repositories.TagRepository;
import com.example.ics.services.ImageService;
import com.example.ics.services.ImaggaService;
import com.example.ics.utils.ImageUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ImaggaService imaggaService;
    private final TagRepository tagRepository;

    public ImageServiceImpl(ImageRepository imageRepository, ImaggaService imaggaService, TagRepository tagRepository) {
        this.imageRepository = imageRepository;
        this.imaggaService = imaggaService;
        this.tagRepository = tagRepository;
    }

    @Override
    public Page<ImageEntity> getAllImages(PageRequest pageRequest) {
        return imageRepository.findAll(pageRequest);
    }

    @Override
    public Optional<ImageEntity> getById(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public Optional<ImageEntity> analyzeImage(String imageUrl) throws MalformedURLException {
        //Checks if the image URL is valid and if it's an image.
        validateImage(imageUrl);
        String checksum;
        try {
            // Download the image from the URL and save it to a temporary file
            File tempFile = downloadImage(imageUrl);
            // Generate the checksum for the downloaded image file
            checksum = generateMD5Checksum(tempFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Check if image already exists in DB
        Optional<ImageEntity> existingImage = getImageByChecksum(checksum);
        if (existingImage.isPresent()) {
            return existingImage;
        }

        List<TagDto> tagDtos = getTagsForImage(imageUrl);

        // Create new image entity, with all related tags
        ImageEntity imageEntity = createImageEntity(imageUrl, tagDtos, checksum);
        saveImageEntity(imageEntity);
        return getImageByChecksum(checksum);
    }

    @Override
    public ImageEntity createImageEntity(String imageUrl, List<TagDto> tagDtos, String checksum) {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setUrl(imageUrl);
        imageEntity.setChecksum(checksum);

        try {
            Dimension imageSize = getImageDimensions(imageUrl);
            imageEntity.setWidth(imageSize.width);
            imageEntity.setHeight(imageSize.height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<TagEntity> tagEntities = createTagEntities(tagDtos);
        imageEntity.setTags(tagEntities);
        return imageEntity;
    }

    @Override
    public TagEntity createTagEntity(TagDto tagDto) {
        String tagName = tagDto.getName();
        TagEntity tagEntity = new TagEntity();
        tagEntity.setName(tagName);
        tagRepository.save(tagEntity);
        tagEntity.setConfidence(tagDto.getConfidence());
        return tagEntity;
    }

    @Override
    public List<TagEntity> createTagEntities(List<TagDto> tagDtos) {
        List<TagEntity> tagEntities = new ArrayList<>();
        for (TagDto tagDto : tagDtos) {
            TagEntity tagEntity = createTagEntity(tagDto);
            tagEntities.add(tagEntity);
        }
        return tagEntities;
    }

    @Override
    public List<TagDto> getTagsForImage(String imageUrl) {
        try {
            return imaggaService.getTagsForImageFromImagga(imageUrl);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Cannot get tags for image", e);
        }
    }

    @Override
    public Dimension getImageDimensions(String imageUrl) throws IOException {

        URL imageSizeUrl;
        try {
            imageSizeUrl = new URL(imageUrl);
            BufferedImage image = ImageIO.read(imageSizeUrl);
            int width = image.getWidth();
            int height = image.getHeight();
            return new Dimension(width, height);
        } catch (IOException e) {
            throw new IOException("Cannot get image dimensions", e);
        }

    }
    @Override
    public void validateImage(String imageUrl) throws ResponseStatusException {
        if (!ImageUtils.isURLValid(imageUrl)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid URL");
        }
        if (!ImageUtils.isImage(imageUrl)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not an image URL");
        }
    }

    private Optional<ImageEntity> getImageByChecksum(String checksum) {
        return imageRepository.findByChecksum(checksum);
    }

    private File downloadImage(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        InputStream in = url.openStream();
        Path tempFilePath = Files.createTempFile("image", ".tmp");
        File tempFile = tempFilePath.toFile();
        try (OutputStream out = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
        return tempFile;
    }

    private String generateMD5Checksum(File file) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        try (FileInputStream fis = new FileInputStream(file);
             DigestInputStream dis = new DigestInputStream(fis, md)) {
            // Read the file content and update the message digest
            byte[] buffer = new byte[8192];
            while (dis.read(buffer) != -1) {
                // Reading and discarding the content as we only need the checksum
            }
        }
        byte[] checksumBytes = md.digest();
        return bytesToHex(checksumBytes);
    }
    private String bytesToHex(byte[] bytes) {
        BigInteger number = new BigInteger(1, bytes);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }

    private void saveImageEntity(ImageEntity imageEntity) {
        imageRepository.save(imageEntity);
    }

}