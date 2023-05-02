package com.example.ics.services.impl;

import com.example.ics.repositories.ImageRepository;
import com.example.ics.services.ImaggaService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ImaggaServiceImpl implements ImaggaService {
//    @Autowired
//    ImageRepository imageRepository;

    @Override
    public List<String> getTagsForImage(String imageUrl){
        // Check if image already exists in DB
//           if (imageRepository.findByUrl(imageUrl) != null) {
//                //TODO: I need to add it to the db
//            }

        // Set up connection to Imagga API
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

            // Extract list of tags from response
            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseJson = mapper.readTree(jsonResponse);
            List<String> tags = new ArrayList<>();
            for (JsonNode tag : responseJson.get("result").get("tags")) {
                int confidence = tag.get("confidence").asInt();
                if (confidence >= 30) {
                    tags.add(tag.get("tag").get("en").asText());
                }
            }

            // Save image to DB
//        Image image = new Image(imageUrl, tags);
//        imageRepository.save(image);

            return tags;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
