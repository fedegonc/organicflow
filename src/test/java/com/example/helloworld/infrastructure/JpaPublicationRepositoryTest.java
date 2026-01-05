package com.example.helloworld.infrastructure;

import com.example.helloworld.domain.Publication;
import com.example.helloworld.domain.PublicationState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JpaPublicationRepositoryTest {

    private JpaPublicationRepository repository;
    private Publication publication1;
    private Publication publication2;

    @BeforeEach
    void setUp() {
        repository = new JpaPublicationRepository();
        
        publication1 = new Publication("Title 1", "Content 1", "author1");
        publication2 = new Publication("Title 2", "Content 2", "author2");
        
        repository.save(publication1);
        repository.save(publication2);
    }

    @Test
    void shouldSavePublication() {
        Publication newPublication = new Publication("New Title", "New Content", "author3");
        Publication saved = repository.save(newPublication);
        
        assertEquals(newPublication, saved);
    }

    @Test
    void shouldFindById() {
        Optional<Publication> found = repository.findById(publication1.getId());
        
        assertTrue(found.isPresent());
        assertEquals(publication1, found.get());
    }

    @Test
    void shouldReturnEmptyWhenNotFound() {
        Optional<Publication> found = repository.findById(UUID.randomUUID());
        
        assertFalse(found.isPresent());
    }

    @Test
    void shouldFindByAuthorId() {
        publication2 = new Publication("Title 3", "Content 3", "author1");
        repository.save(publication2);
        
        List<Publication> author1Publications = repository.findByAuthorId("author1");
        
        assertEquals(2, author1Publications.size());
        assertTrue(author1Publications.contains(publication1));
        assertTrue(author1Publications.contains(publication2));
    }

    @Test
    void shouldFindByState() {
        publication1.publish();
        repository.save(publication1);
        
        List<Publication> publishedPublications = repository.findByState(PublicationState.PUBLISHED);
        
        assertEquals(1, publishedPublications.size());
        assertEquals(publication1, publishedPublications.get(0));
    }

    @Test
    void shouldDeletePublication() {
        repository.save(publication1);
        repository.delete(publication1);
        
        Optional<Publication> found = repository.findById(publication1.getId());
        assertFalse(found.isPresent());
    }
}
