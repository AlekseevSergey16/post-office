package com.alekseev.postman.controller;

import com.alekseev.postman.model.Publisher;
import com.alekseev.postman.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/update")
    public String showUpdatePublisherForm(@RequestParam long publisherId, Model model)  {
        Publisher publisher = publisherService.getPublisher(publisherId);
        model.addAttribute("publisher", publisher);
        model.addAttribute("newPublisher", new Publisher());
        return "update-publisher-form";
    }

    @PutMapping("/{id}")
    public String updatePublisher(@PathVariable long id, @ModelAttribute Publisher publisher) {
        publisher.setId(id);
        publisherService.updatePublisher(publisher);
        return "redirect:/publishers";
    }

}
