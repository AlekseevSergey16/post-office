package com.alekseev.postman.controller;

import com.alekseev.postman.model.Subscription;
import com.alekseev.postman.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public void addSubscription(@RequestBody Subscription subscription) {
        subscriptionService.addSubscription(subscription);
    }

    @GetMapping
    public List<Subscription> getSubscriptionsBySubscriber(@RequestParam long subscriberId) {
        return subscriptionService.getSubscriptionsBySubscriber(subscriberId);
    }

}
