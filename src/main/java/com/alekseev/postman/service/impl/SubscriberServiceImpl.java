package com.alekseev.postman.service.impl;

import com.alekseev.postman.model.Subscriber;
import com.alekseev.postman.repository.SubscriberRepository;
import com.alekseev.postman.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriberServiceImpl implements SubscriberService {

    private final SubscriberRepository subscriberRepository;

    @Autowired
    public SubscriberServiceImpl(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    @Override
    public void addSubscriber(Subscriber subscriber) {
        subscriberRepository.insert(subscriber.getFirstName(),
                                    subscriber.getLastName(),
                                    subscriber.getMiddleName(),
                                    subscriber.getAddress(),
                                    subscriber.getPhone(),
                                    subscriber.getEmail());
    }

    @Override
    public Subscriber getSubscriber(long id) {
        return subscriberRepository.findById(id)
                .orElseThrow();
    }
}
