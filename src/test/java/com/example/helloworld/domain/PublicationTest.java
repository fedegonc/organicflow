package com.example.helloworld.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

class PublicationTest {

    @Test
    void shouldCreateDraftPublication() {
        Publication publication = new Publication("Title", "Content", "author123");
        
        assertEquals(PublicationState.DRAFT, publication.getState());
        assertNotNull(publication.getId());
        assertEquals("Title", publication.getTitle());
        assertEquals("Content", publication.getContent());
        assertEquals("author123", publication.getAuthorId());
        assertNotNull(publication.getCreatedAt());
        assertNull(publication.getPublishedAt());
    }

    @Test
    void shouldPublishDraft() {
        Publication publication = new Publication("Title", "Content", "author123");
        publication.publish();
        
        assertEquals(PublicationState.PUBLISHED, publication.getState());
        assertNotNull(publication.getPublishedAt());
    }

    @Test
    void shouldNotPublishAlreadyPublished() {
        Publication publication = new Publication("Title", "Content", "author123");
        publication.publish();
        
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            publication::publish
        );
        assertEquals("Only draft publications can be published", exception.getMessage());
    }

    @Test
    void shouldNotPublishArchived() {
        Publication publication = new Publication("Title", "Content", "author123");
        publication.publish();
        publication.archive();
        
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            publication::publish
        );
        assertEquals("Only draft publications can be published", exception.getMessage());
    }

    @Test
    void shouldArchivePublished() {
        Publication publication = new Publication("Title", "Content", "author123");
        publication.publish();
        publication.archive();
        
        assertEquals(PublicationState.ARCHIVED, publication.getState());
    }

    @Test
    void shouldNotArchiveDraft() {
        Publication publication = new Publication("Title", "Content", "author123");
        
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            publication::archive
        );
        assertEquals("Only published publications can be archived", exception.getMessage());
    }

    @Test
    void shouldNotArchiveAlreadyArchived() {
        Publication publication = new Publication("Title", "Content", "author123");
        publication.publish();
        publication.archive();
        
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            publication::archive
        );
        assertEquals("Only published publications can be archived", exception.getMessage());
    }

    @Test
    void shouldUpdateDraftContent() {
        Publication publication = new Publication("Title", "Content", "author123");
        publication.updateContent("New content");
        
        assertEquals("New content", publication.getContent());
    }

    @Test
    void shouldNotUpdatePublishedContent() {
        Publication publication = new Publication("Title", "Content", "author123");
        publication.publish();
        
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> publication.updateContent("New content")
        );
        assertEquals("Published content is immutable", exception.getMessage());
    }

    @Test
    void shouldNotUpdateArchivedContent() {
        Publication publication = new Publication("Title", "Content", "author123");
        publication.publish();
        publication.archive();
        
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> publication.updateContent("New content")
        );
        assertEquals("Published content is immutable", exception.getMessage());
    }

    @Test
    void shouldReturnEditableForDraft() {
        Publication publication = new Publication("Title", "Content", "author123");
        
        assertTrue(publication.isEditable());
    }

    @Test
    void shouldReturnNotEditableForPublished() {
        Publication publication = new Publication("Title", "Content", "author123");
        publication.publish();
        
        assertFalse(publication.isEditable());
    }

    @Test
    void shouldReturnNotEditableForArchived() {
        Publication publication = new Publication("Title", "Content", "author123");
        publication.publish();
        publication.archive();
        
        assertFalse(publication.isEditable());
    }
}
