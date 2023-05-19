package com.example.ics.controllers;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


class ImagesControllerTest {

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    void testFindAllImages() {
        given()
                .when()
                .get("/images")
                .then()
                .statusCode(200)
                .body("$", hasSize(greaterThan(0))); // Assuming I have at least one image in the db
    }

    @Test
    void testGetImageById() {
        int imageId = 3;

        given()
                .pathParam("id", imageId)
                .when()
                .get("/images/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(imageId));
    }

    @Test
    void testGetImageById_IdNotPresent_Returns404() {
        long invalidId = 100000; // Assuming this ID does not exist in the database

        given()
                .pathParam("id", invalidId)
                .when()
                .get("/images/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    void testAnalyzeImageWithCorrectURL() {
        String imageUrl = "https://car-images.bauersecure.com/wp-images/13221/everrati911_00.jpg"; // Valid image URL

        given()
                .contentType("application/json")
                .body("{\"imageUrl\":\"" + imageUrl + "\"}")
                .when()
                .post("/images")
                .then()
                .statusCode(200)
                .body("url", equalTo(imageUrl))
                .body("id", equalTo(4));
    }

    @Test
    void testAnalyzeImageWithInvalidImageUrlAndReturnsBadRequest() {
        String invalidImageUrl = "invalid-url";

        given()
                .contentType("application/json")
                .body(invalidImageUrl)
                .when()
                .post("/images")
                .then()
                .statusCode(400);
    }

//    @Test
//    void testAnalyzeImageWithRequestThrottlingThatEnforcesRateLimit() {
//        String imageUrl = "https://car-images.bauersecure.com/wp-images/13221/everrati911_00.jpg"; // Valid image URL
//        // Perform consecutive requests beyond the allowed rate
//        for (int i = 0; i < 5; i++) {
//            given()
//                    .contentType("application/json")
//                    .body("{\"imageUrl\":\"" + imageUrl + "\"}")
//                    .when()
//                    .post("/images")
//                    .then()
//                    .statusCode(200);
//        }
//
//        // Attempt one more request, which should be throttled
//        given()
//                .contentType("application/json")
//                .body("{\"imageUrl\":\"https://example.com/image.jpg\"}")
//                .when()
//                .post("/images")
//                .then()
//                .statusCode(429);
//    }



}