package com.alekseev.postman.controller;

import com.alekseev.postman.model.Subscriber;
import com.alekseev.postman.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscribers")
public class SubscriberController {

    private final SubscriberService subscriberService;

    @Autowired
    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @PostMapping
    public void createSubscriber(@RequestBody Subscriber subscriber) {
        subscriberService.addSubscriber(subscriber);
    }

    @GetMapping("/{id}")
    public Subscriber getSubscriber(@PathVariable long id) {
        return subscriberService.getSubscriber(id);
    }

}
