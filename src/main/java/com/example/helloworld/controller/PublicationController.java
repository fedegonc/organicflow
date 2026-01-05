package com.example.helloworld.controller;

import com.example.helloworld.domain.Publication;
import com.example.helloworld.domain.PublicationState;
import com.example.helloworld.domain.PublicationRepository;
import com.example.helloworld.usecase.CreatePublication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/publications")
public class PublicationController {
    private final CreatePublication createPublication;
    private final PublicationRepository repository;

    public PublicationController(CreatePublication createPublication, PublicationRepository repository) {
        this.createPublication = createPublication;
        this.repository = repository;
    }

    @PostMapping
    public Publication create(@RequestBody CreatePublicationRequest request) {
        return createPublication.execute(request.title(), request.content(), request.authorId());
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<Void> publish(@PathVariable UUID id) {
        return repository.findById(id)
                .map(publication -> {
                    publication.publish();
                    repository.save(publication);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/archive")
    public ResponseEntity<Void> archive(@PathVariable UUID id) {
        return repository.findById(id)
                .map(publication -> {
                    publication.archive();
                    repository.save(publication);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/published")
    public List<Publication> getPublished() {
        return repository.findByState(PublicationState.PUBLISHED);
    }

    @GetMapping("/author/{authorId}")
    public List<Publication> getByAuthor(@PathVariable String authorId) {
        return repository.findByAuthorId(authorId);
    }

    public record CreatePublicationRequest(String title, String content, String authorId) {}
}
