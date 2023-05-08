package com.example.ics.services.impl;

import com.example.ics.dto.TagDto;
import com.example.ics.services.ImaggaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ImaggaServiceImpl implements ImaggaService {

    @Override
    public List<TagDto> getTagsForImage(String imageUrl) throws JsonProcessingException {
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
        Collections.sort(tags, Comparator.comparingInt(TagDto::getConfidence).reversed());

        // Take the first five tags
        tags = tags.stream().limit(5).collect(Collectors.toList());

        return tags;
    }
    private String getJsonResponseFromImagga(String imageUrl) {
        String credentialsToEncode = "acc_d83c6c2a0e9725d" + ":" + "00172f01e99ba2b6f2dcb52ab34ff3f8";
        String basicAuth = Base64.getEncoder().encodeToString(credentialsToEncode.getBytes(StandardCharsets.UTF_8));

        String endpoint_url = "https://api.imagga.com/v2/tags";
        String url = endpoint_url + "?image_url=" + imageUrl;

        try {
            URL urlObject = new URL(url);
            HttpURLConnection connection = null;
            connection = (HttpURLConnection) urlObject.openConnection();
            connection.setRequestProperty("Authorization", "Basic " + basicAuth);
            int responseCode = connection.getResponseCode();

            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            BufferedReader connectionInput = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String jsonResponse = connectionInput.readLine();

            connectionInput.close();

            return jsonResponse;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}