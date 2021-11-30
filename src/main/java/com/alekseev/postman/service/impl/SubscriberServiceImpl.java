package com.alekseev.postman.service.impl;

import com.alekseev.postman.model.Subscriber;
import com.alekseev.postman.repository.SubscriberRepository;
import com.alekseev.postman.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriberServiceImpl implements SubscriberService {

    private final SubscriberRepository subscriberRepository;

    @Autowired
    public SubscriberServiceImpl(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    @Override
    public long addSubscriber(Subscriber subscriber) {
        return subscriberRepository.insert(subscriber);
    }

    @Override
    public Subscriber getSubscriber(long id) {
        return subscriberRepository.findById(id)
                .orElseThrow();
    }

    @Override
    public Optional<Long> checkExistSubscriber(Subscriber subscriber) {
        return subscriberRepository.checkExistSubscriber(subscriber);
    }
}
