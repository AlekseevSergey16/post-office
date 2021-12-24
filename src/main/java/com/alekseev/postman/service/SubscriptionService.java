package com.alekseev.postman.service;

import com.alekseev.postman.model.Subscription;

import java.util.List;

public interface SubscriptionService {

    void addSubscription(Subscription subscription);
    List<Subscription> getSubscriptionsBySubscriber(long subscriberId);
    void deleteSubscription(long id);
    List<Subscription> getSubscriptionsByPostman(long postmanId);

}
