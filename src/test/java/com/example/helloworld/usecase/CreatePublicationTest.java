package com.example.helloworld.usecase;

import com.example.helloworld.domain.Publication;
import com.example.helloworld.domain.PublicationRepository;
import com.example.helloworld.domain.PublicationState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreatePublicationTest {

    @Mock
    private PublicationRepository repository;

    @InjectMocks
    private CreatePublication createPublication;

    @Test
    void shouldCreateAndSavePublication() {
        String title = "Test Title";
        String content = "Test Content";
        String authorId = "author123";

        when(repository.save(any(Publication.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Publication result = createPublication.execute(title, content, authorId);

        assertNotNull(result);
        assertEquals(title, result.getTitle());
        assertEquals(content, result.getContent());
        assertEquals(authorId, result.getAuthorId());
        assertEquals(PublicationState.DRAFT, result.getState());
        
        verify(repository).save(result);
    }
}
