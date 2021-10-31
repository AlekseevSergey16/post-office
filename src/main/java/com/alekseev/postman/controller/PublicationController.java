package com.alekseev.postman.controller;

import com.alekseev.postman.model.Publication;
import com.alekseev.postman.model.Publisher;
import com.alekseev.postman.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/publications")
public class PublicationController {

    private final PublicationService publicationService;

    @Autowired
    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @GetMapping("/publications/{id}")
    public Publication getPublicationById(@PathVariable long id) {
        return publicationService.getPublication(id);
    }

    @GetMapping("/publishers/{publisherId}/publications")
    public List<Publication> getPublicationsByPublisher(@PathVariable long publisherId) {
        return publicationService.getPublicationsByPublisher(publisherId);
    }

    @PostMapping("/publishers/{publisherId}/publications")
    public void createPublication(@PathVariable long publisherId, @RequestBody Publication publication) {
        publication.setPublisher(new Publisher(publisherId));
        publicationService.addPublication(publication);
    }

    @PutMapping
    public void updatePublication(@RequestBody Publication publication) {
        publicationService.updatePublication(publication);
    }

}
