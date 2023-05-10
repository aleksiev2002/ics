package com.example.ics.dtos;


public class TagDto {

    private String name;
    private int confidence;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getConfidence() {
        return confidence;
    }

    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }
}
