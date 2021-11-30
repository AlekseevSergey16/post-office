package com.alekseev.postman.service;

import com.alekseev.postman.model.Subscriber;

import java.util.Optional;

public interface SubscriberService {

    long addSubscriber(Subscriber subscriber);
    Subscriber getSubscriber(long id);
    Optional<Long> checkExistSubscriber(Subscriber subscriber);

}
