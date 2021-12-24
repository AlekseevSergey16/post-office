package com.alekseev.postman.controller;

import com.alekseev.postman.dto.Converter;
import com.alekseev.postman.dto.CreateSubscriptionDto;
import com.alekseev.postman.model.Subscriber;
import com.alekseev.postman.model.Subscription;
import com.alekseev.postman.repository.AddressRepository;
import com.alekseev.postman.service.SubscriberService;
import com.alekseev.postman.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final SubscriberService subscriberService;
    private final AddressRepository addressRepository;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService, SubscriberService subscriberService, AddressRepository addressRepository) {
        this.subscriptionService = subscriptionService;
        this.subscriberService = subscriberService;
        this.addressRepository = addressRepository;
    }

    @GetMapping
    public String showAddSubscription(@RequestParam long publicationId, Model model) {
        model.addAttribute("subscription", new CreateSubscriptionDto());
        model.addAttribute("addresses", addressRepository.findAll());
        model.addAttribute("publicationId", publicationId);

        return "addSubscription";
    }

    @PostMapping
    public String createSubscription(@ModelAttribute CreateSubscriptionDto subscription, @RequestParam long publicationId) {
        subscription.addressId = publicationId;
        subscriptionService.addSubscription(Converter.from(subscription));

        return "redirect:/publications";
    }

    @GetMapping("/subscriptions-by-subscriber")
    public String showSubscriptionBySubscriber(@RequestParam String phone, @RequestParam String email, Model model) {
        Subscriber subscriber = new Subscriber();
        subscriber.setPhone(phone);
        subscriber.setEmail(email);

        Optional<Long> subscriberId = subscriberService.checkExistSubscriber(subscriber);
        if (subscriberId.isEmpty()) {
            return "redirect: /";
        }

        List<Subscription> subscriptions = subscriptionService.getSubscriptionsBySubscriber(subscriberId.get());
        model.addAttribute("subscriptions", subscriptions);

        return "subscriptionList";
    }

    @GetMapping("/subscriptions-by-postman")
    public String showSubscriptionByPostman(@RequestParam long postmanId, Model model) {
        List<Subscription> subscriptions = subscriptionService.getSubscriptionsByPostman(postmanId);
        model.addAttribute("subscriptions", subscriptions);

        return "subscriptionList";
    }

    @DeleteMapping("/{id}")
    public String deleteSubscription(@PathVariable long id) {
        subscriptionService.deleteSubscription(id);
        return "redirect:/";
    }

}
