package com.example.helloworld.controller;

import com.example.helloworld.domain.Publication;
import com.example.helloworld.domain.PublicationRepository;
import com.example.helloworld.domain.PublicationState;
import com.example.helloworld.usecase.CreatePublication;
import com.example.helloworld.usecase.ListDrafts;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/api/publications")
public class PublicationController {
    private final CreatePublication createPublication;
    private final ListDrafts listDrafts;
    private final PublicationRepository repository;

    public PublicationController(CreatePublication createPublication, ListDrafts listDrafts, PublicationRepository repository) {
        this.createPublication = createPublication;
        this.listDrafts = listDrafts;
        this.repository = repository;
    }

    // Web endpoints
    @GetMapping("/")
    public String home(Model model) {
        List<Publication> publications = repository.findByState(PublicationState.PUBLISHED);
        model.addAttribute("publications", publications);
        return "publications";
    }

    @GetMapping("/editor")
    public String editor(Model model) {
        return "editor";
    }

    // API endpoints
    @PostMapping
    @ResponseBody
    public Publication create(@RequestBody CreatePublicationRequest request) {
        return createPublication.execute(request.title(), request.content(), request.authorId());
    }

    @PostMapping("/{id}/publish")
    @ResponseBody
    public Publication publish(@PathVariable UUID id) {
        return repository.findById(id)
                .map(publication -> {
                    publication.publish();
                    repository.save(publication);
                    return publication;
                })
                .orElseThrow();
    }

    @PostMapping("/{id}/archive")
    @ResponseBody
    public Publication archive(@PathVariable UUID id) {
        return repository.findById(id)
                .map(publication -> {
                    publication.archive();
                    repository.save(publication);
                    return publication;
                })
                .orElseThrow();
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Publication update(@PathVariable UUID id, @RequestBody Map<String, String> request) {
        return repository.findById(id)
                .map(publication -> {
                    publication.updateContent(request.get("content"));
                    repository.save(publication);
                    return publication;
                })
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void delete(@PathVariable UUID id) {
        repository.findById(id).ifPresent(publication -> {
            if (publication.getState() != PublicationState.DRAFT) {
                throw new IllegalStateException("Only drafts can be deleted");
            }
            repository.delete(publication);
        });
    }

    @GetMapping("/drafts")
    @ResponseBody
    public List<Publication> getDrafts() {
        return listDrafts.execute();
    }

    @GetMapping("/published")
    @ResponseBody
    public List<Publication> getPublished() {
        return repository.findByState(PublicationState.PUBLISHED);
    }

    public record CreatePublicationRequest(String title, String content, String authorId) {}
}
