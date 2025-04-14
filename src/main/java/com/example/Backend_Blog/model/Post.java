package com.example.Backend_Blog.model;

import jakarta.persistence.*;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String postImg;
    private String contentUrl;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    public Post(String title, String description, String postImg, String contentUrl, User author) {
        this.title = title;
        this.description = description;
        this.postImg = postImg;
        this.contentUrl = contentUrl;
        this.author = author;
    }

    public Post() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostImg() {
        return postImg;
    }

    public void setPostImg(String postImg) {
        this.postImg = postImg;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", postImg='" + postImg + '\'' +
                ", contentUrl='" + contentUrl + '\'' +
                '}';
    }
}
