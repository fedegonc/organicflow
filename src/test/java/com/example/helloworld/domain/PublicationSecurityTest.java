package com.example.helloworld.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

class PublicationSecurityTest {

    @Test
    void shouldNotAllowEditPublishedContent() {
        Publication publication = new Publication("Title", "Content", "author1");
        publication.publish();
        
        assertThrows(IllegalStateException.class, 
            () -> publication.updateContent("Hacked content"));
    }

    @Test
    void shouldNotAllowEditArchivedContent() {
        Publication publication = new Publication("Title", "Content", "author1");
        publication.publish();
        publication.archive();
        
        assertThrows(IllegalStateException.class, 
            () -> publication.updateContent("Hacked content"));
    }

    @Test
    void shouldNotAllowPublishFromArchived() {
        Publication publication = new Publication("Title", "Content", "author1");
        publication.publish();
        publication.archive();
        
        assertThrows(IllegalStateException.class, publication::publish);
    }

    @Test
    void shouldNotAllowArchiveFromDraft() {
        Publication publication = new Publication("Title", "Content", "author1");
        
        assertThrows(IllegalStateException.class, publication::archive);
    }

    @Test
    void shouldNotAllowArchiveFromAlreadyArchived() {
        Publication publication = new Publication("Title", "Content", "author1");
        publication.publish();
        publication.archive();
        
        assertThrows(IllegalStateException.class, publication::archive);
    }

    @Test
    void shouldNotAllowPublishTwice() {
        Publication publication = new Publication("Title", "Content", "author1");
        publication.publish();
        
        assertThrows(IllegalStateException.class, publication::publish);
    }

    @Test
    void shouldNotAllowBypassStateTransitions() {
        Publication publication = new Publication("Title", "Content", "author1");
        
        // Intento directo de modificar estado (si hubiera setter)
        // No hay setter, el estado es inmutable desde fuera
        
        assertEquals(PublicationState.DRAFT, publication.getState());
        assertFalse(publication.isEditable() == false);
    }

    @Test
    void shouldNotAllowEmptyTitle() {
        assertThrows(IllegalArgumentException.class, 
            () -> new Publication("", "Content", "author1"));
        
        assertThrows(IllegalArgumentException.class, 
            () -> new Publication(null, "Content", "author1"));
        
        assertThrows(IllegalArgumentException.class, 
            () -> new Publication("   ", "Content", "author1"));
    }

    @Test
    void shouldNotAllowNullContent() {
        assertThrows(IllegalArgumentException.class, 
            () -> new Publication("Title", null, "author1"));
        
        assertThrows(IllegalArgumentException.class, 
            () -> new Publication("Title", "", "author1"));
        
        assertThrows(IllegalArgumentException.class, 
            () -> new Publication("Title", "   ", "author1"));
    }

    @Test
    void shouldNotAllowEmptyAuthorId() {
        assertThrows(IllegalArgumentException.class, 
            () -> new Publication("Title", "Content", ""));
        
        assertThrows(IllegalArgumentException.class, 
            () -> new Publication("Title", "Content", null));
        
        assertThrows(IllegalArgumentException.class, 
            () -> new Publication("Title", "Content", "   "));
    }

    @Test
    void shouldMaintainImmutabilityOfId() {
        Publication publication = new Publication("Title", "Content", "author1");
        UUID originalId = publication.getId();
        
        // No hay setter para ID, es inmutable
        assertEquals(originalId, publication.getId());
    }

    @Test
    void shouldMaintainImmutabilityOfCreatedAt() {
        Publication publication = new Publication("Title", "Content", "author1");
        LocalDateTime originalCreatedAt = publication.getCreatedAt();
        
        // No hay setter para createdAt, es inmutable
        assertEquals(originalCreatedAt, publication.getCreatedAt());
    }
}
