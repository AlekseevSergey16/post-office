package com.alekseev.postman.service.impl;

import com.alekseev.postman.model.Subscription;
import com.alekseev.postman.repository.SubscriptionRepository;
import com.alekseev.postman.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public void addSubscription(Subscription subscription) {
        subscriptionRepository.insert(
                subscription.getSubscriber().getId(),
                subscription.getPublication().getId(),
                subscription.getStartDate(),
                subscription.getEndDate(),
                subscription.getNumberOfMonths());
    }

    @Override
    public List<Subscription> getSubscriptionsBySubscriber(long subscriberId) {
        return subscriptionRepository.findBySubscriberId(subscriberId);
    }

}
