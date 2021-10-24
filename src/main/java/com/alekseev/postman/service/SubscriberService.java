package com.alekseev.postman.service;

import com.alekseev.postman.model.Subscriber;

public interface SubscriberService {

    void addSubscriber(Subscriber subscriber);
    Subscriber getSubscriber(long id);

}
