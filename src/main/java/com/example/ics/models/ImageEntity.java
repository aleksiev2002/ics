package com.example.ics.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity(name = "images")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;
    @Column(name = "analyzed_with")
    private String analyzedWith;
    private int width;
    private int height;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "image_tags",
            joinColumns = @JoinColumn(name = "image_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<TagEntity> tags;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getAnalyzedWith() {
        return analyzedWith;
    }

    public void setAnalyzedWith(String analyzedWith) {
        this.analyzedWith = analyzedWith;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<TagEntity> getTags() {
        return tags;
    }

    public void setTags(List<TagEntity> tags) {
        this.tags = tags;
    }

    public ImageEntity() {
        this.analyzedWith = "Imagga";
        this.uploadedAt = LocalDateTime.now();

    }


}
