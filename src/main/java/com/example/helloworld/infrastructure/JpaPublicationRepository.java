package com.example.helloworld.infrastructure;

import com.example.helloworld.domain.Publication;
import com.example.helloworld.domain.PublicationRepository;
import com.example.helloworld.domain.PublicationState;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class JpaPublicationRepository implements PublicationRepository {
    private final ConcurrentHashMap<UUID, Publication> store = new ConcurrentHashMap<>();

    @Override
    public Publication save(Publication publication) {
        store.put(publication.getId(), publication);
        return publication;
    }

    @Override
    public Optional<Publication> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Publication> findByAuthorId(String authorId) {
        return store.values().stream()
                .filter(p -> p.getAuthorId().equals(authorId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Publication> findByState(PublicationState state) {
        return store.values().stream()
                .filter(p -> p.getState() == state)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Publication publication) {
        store.remove(publication.getId());
    }
}
