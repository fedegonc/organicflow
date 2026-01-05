package com.example.helloworld.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Publication {
    private final UUID id;
    private String title;
    private String content;
    private PublicationState state;
    private final LocalDateTime createdAt;
    private LocalDateTime publishedAt;
    private String authorId;

    public Publication(String title, String content, String authorId) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
        if (authorId == null || authorId.trim().isEmpty()) {
            throw new IllegalArgumentException("Author ID cannot be null or empty");
        }
        
        this.id = UUID.randomUUID();
        this.title = title.trim();
        this.content = content.trim();
        this.state = PublicationState.DRAFT;
        this.createdAt = LocalDateTime.now();
        this.authorId = authorId.trim();
    }

    public void publish() {
        if (state != PublicationState.DRAFT) {
            throw new IllegalStateException("Only draft publications can be published");
        }
        this.state = PublicationState.PUBLISHED;
        this.publishedAt = LocalDateTime.now();
    }

    public void archive() {
        if (state != PublicationState.PUBLISHED) {
            throw new IllegalStateException("Only published publications can be archived");
        }
        this.state = PublicationState.ARCHIVED;
    }

    public boolean isEditable() {
        return state == PublicationState.DRAFT;
    }

    public void updateContent(String newContent) {
        if (!isEditable()) {
            throw new IllegalStateException("Published content is immutable");
        }
        if (newContent == null || newContent.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
        this.content = newContent.trim();
    }

    // Getters
    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public PublicationState getState() { return state; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getPublishedAt() { return publishedAt; }
    public String getAuthorId() { return authorId; }
}
