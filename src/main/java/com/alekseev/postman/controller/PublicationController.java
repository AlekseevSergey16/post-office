package com.alekseev.postman.controller;

import com.alekseev.postman.model.Publication;
import com.alekseev.postman.model.Publisher;
import com.alekseev.postman.service.PublicationService;
import com.alekseev.postman.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/publications")
public class PublicationController {

    private final PublicationService publicationService;

    @Autowired
    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @GetMapping
    public String getPublications(Model model) {
        List<Publication> publications = publicationService.getPublications();
        model.addAttribute("publications", publications);
        return "publicationList";
    }

    @GetMapping("/search-by-publisher")
    public String getPublicationsByPublisher(@RequestParam long publisherId, Model model) {
        model.addAttribute("publications", publicationService.getPublicationsByPublisher(publisherId));
        model.addAttribute("publisherId", publisherId);
        model.addAttribute("publication", new Publication());
        return "addPublication";
    }

    @PostMapping
    public String createPublication(@ModelAttribute Publication publication, @RequestParam long publisherId) {
        publication.setPublisher(new Publisher(publisherId));
        publicationService.addPublication(publication);
        return "redirect:/publications/search-by-publisher?publisherId="+ publisherId;
    }

}
