package com.alekseev.postman.service;

import com.alekseev.postman.model.Publication;

import java.util.List;

public interface PublicationService {

    void addPublication(Publication publication);

    void updatePublication(Publication publication);

    Publication getPublication(long id);

    List<Publication> getPublicationsByPublisher(long publisherId);

}
