package com.alekseev.postman.controller;

import com.alekseev.postman.model.Publisher;
import com.alekseev.postman.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public String getPublishers(Model model) {
        List<Publisher> publishers = publisherService.getPublishers();
        model.addAttribute("publishers", publishers);
        model.addAttribute("publisher", new Publisher());
        return "publisherList";
    }

    @PostMapping
    public String createPublishers(@ModelAttribute Publisher publisher) {
        publisherService.addPublisher(publisher);
        return "redirect:/publishers";
    }

}
