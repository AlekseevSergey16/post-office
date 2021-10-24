package com.alekseev.postman.service;

import com.alekseev.postman.model.Publisher;

import java.util.List;

public interface PublisherService {

    void addPublisher(Publisher publisher);
    void updatePublisher(Publisher publisher);
    Publisher getPublisher(long id);
    List<Publisher> getPublishers();

}
