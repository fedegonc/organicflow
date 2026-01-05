package com.example.helloworld.usecase;

import com.example.helloworld.domain.Publication;
import com.example.helloworld.domain.PublicationRepository;
import com.example.helloworld.domain.PublicationState;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListDrafts {
    private final PublicationRepository repository;

    public ListDrafts(PublicationRepository repository) {
        this.repository = repository;
    }

    public List<Publication> execute() {
        return repository.findByState(PublicationState.DRAFT);
    }
}
