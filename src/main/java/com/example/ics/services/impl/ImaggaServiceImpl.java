package com.example.ics.services.impl;

import com.example.ics.configs.ImaggaConfig;
import com.example.ics.dtos.TagDto;
import com.example.ics.services.ImaggaService;
import com.example.ics.services.exceptions.ImaggaServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Base64;
import java.util.stream.Collectors;

@Service
public class ImaggaServiceImpl implements ImaggaService {

    private final ImaggaConfig imaggaConfig;

    public ImaggaServiceImpl(ImaggaConfig imaggaConfig) {
        this.imaggaConfig = imaggaConfig;
    }

    @Override
    public List<TagDto> getTagsForImageFromImagga(String imageUrl) {
        try {
            String jsonResponse = getJsonResponseFromImagga(imageUrl);
            // Extract list of tags from response
            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseJson = mapper.readTree(jsonResponse);
            List<TagDto> tags = new ArrayList<>();
            for (JsonNode tag : responseJson.get("result").get("tags")) {
                int confidence = tag.get("confidence").asInt();
                if (confidence >= 30) {
                    TagDto tagDto = new TagDto();
                    tagDto.setName(tag.get("tag").get("en").asText());
                    tagDto.setConfidence(confidence);
                    tags.add(tagDto);
                }
            }

            // Sort the list by confidence rate in descending order
            tags.sort(Comparator.comparingInt(TagDto::getConfidence).reversed());

            // Take the first five tags
            tags = tags.stream().limit(5).collect(Collectors.toList());

            return tags;
        } catch (JsonProcessingException e) {
            throw new ImaggaServiceException(HttpStatus.BAD_REQUEST, "Error processing JSON response from Imagga API", e);
        }
    }
    private String getJsonResponseFromImagga(String imageUrl) {
        String credentialsToEncode =  imaggaConfig.getApiKey() + ":" + imaggaConfig.getSecretKey();
        String basicAuth = Base64.getEncoder().encodeToString(credentialsToEncode.getBytes(StandardCharsets.UTF_8));

        String url = imaggaConfig.getEndpointUrl() + "?image_url=" + imageUrl;

        try {
            URL urlObject = new URL(url);
            HttpURLConnection connection;
            connection = (HttpURLConnection) urlObject.openConnection();
            connection.setRequestProperty("Authorization", "Basic " + basicAuth);
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader connectionInput = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String jsonResponse = connectionInput.readLine();
                connectionInput.close();
                return jsonResponse;
            } else {
                throw new ImaggaServiceException(HttpStatus.BAD_REQUEST, "Failed to get response from Imagga API. Response code: ");
            }
        } catch (IOException e) {
            throw new ImaggaServiceException(HttpStatus.BAD_REQUEST, "Failed to get JSON response from Imagga API", e);
        }
    }
}
