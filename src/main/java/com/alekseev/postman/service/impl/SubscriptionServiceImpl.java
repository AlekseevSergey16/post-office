package com.alekseev.postman.service.impl;

import com.alekseev.postman.model.Subscriber;
import com.alekseev.postman.model.Subscription;
import com.alekseev.postman.repository.SubscriptionRepository;
import com.alekseev.postman.service.SubscriberService;
import com.alekseev.postman.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriberService subscriberService;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, SubscriberService subscriberService) {
        this.subscriptionRepository = subscriptionRepository;
        this.subscriberService = subscriberService;
    }

    @Override
    public void addSubscription(Subscription subscription) {
        Subscriber subscriber = subscription.getSubscriber();
        Optional<Long> optionalId = subscriberService.checkExistSubscriber(subscriber);

        if (optionalId.isEmpty()) {
            long id = subscriberService.addSubscriber(subscriber);
            subscriber.setId(id);
        } else {
            subscriber.setId(optionalId.get());
        }

        subscriptionRepository.insert(subscription);
    }

    @Override
    public List<Subscription> getSubscriptionsBySubscriber(long subscriberId) {
        return subscriptionRepository.findBySubscriberId(subscriberId);
    }

}
