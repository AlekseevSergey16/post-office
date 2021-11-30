package com.alekseev.postman.service.impl;

import com.alekseev.postman.model.Publisher;
import com.alekseev.postman.repository.PublisherRepository;
import com.alekseev.postman.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void addPublisher(Publisher publisher) {
        publisherRepository.insert(publisher);
    }

    @Override
    public void updatePublisher(Publisher publisher) {
        publisherRepository.update(publisher.getId(), publisher.getName(), publisher.getPhone(),
                publisher.getEmail(), publisher.getInformation());
    }

    @Override
    public Publisher getPublisher(long id) {
        return publisherRepository.findById(id)
                .orElseThrow();
    }

    @Override
    public List<Publisher> getPublishers() {
        List<Publisher> publishers = publisherRepository.findAllPublishers();
        return publishers;
    }

}
