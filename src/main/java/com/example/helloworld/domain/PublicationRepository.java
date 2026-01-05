package com.example.helloworld.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PublicationRepository {
    Publication save(Publication publication);
    Optional<Publication> findById(UUID id);
    List<Publication> findByAuthorId(String authorId);
    List<Publication> findByState(PublicationState state);
    void delete(Publication publication);
}
