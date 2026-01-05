package com.example.helloworld.usecase;

import com.example.helloworld.domain.Publication;
import com.example.helloworld.domain.PublicationRepository;
import org.springframework.stereotype.Service;

@Service
public class CreatePublication {
    private final PublicationRepository repository;

    public CreatePublication(PublicationRepository repository) {
        this.repository = repository;
    }

    public Publication execute(String title, String content, String authorId) {
        Publication publication = new Publication(title, content, authorId);
        return repository.save(publication);
    }
}
