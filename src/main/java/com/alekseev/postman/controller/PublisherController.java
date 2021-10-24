package com.alekseev.postman.controller;

import com.alekseev.postman.model.Publisher;
import com.alekseev.postman.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping
    public void createPublisher(@RequestBody Publisher publisher) {
        publisherService.addPublisher(publisher);
    }

    @PutMapping
    public void updatePublisher(@RequestBody Publisher publisher) {
        publisherService.updatePublisher(publisher);
    }

    @GetMapping("/{id}")
    public Publisher getPublisher(@PathVariable long id) {
        return publisherService.getPublisher(id);
    }

    @GetMapping
    public List<Publisher> getPublishers() {
        return publisherService.getPublishers();
    }

}
