package com.alekseev.postman.service.impl;

import com.alekseev.postman.model.Publication;
import com.alekseev.postman.repository.PublicationRepository;
import com.alekseev.postman.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicationServiceImpl implements PublicationService {

    private final PublicationRepository publicationRepository;

    @Autowired
    public PublicationServiceImpl(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    @Override
    public void addPublication(Publication publication) {
        publicationRepository.insert(publication.getName(), publication.getAbout(), publication.getCost(),
                publication.getPages(), publication.getWeight(), publication.getPublisher().getId());
    }

    @Override
    public void updatePublication(Publication publication) {
        publicationRepository.update(publication.getId(), publication.getName(), publication.getAbout(), publication.getCost(),
                publication.getPages(), publication.getWeight());
    }

    @Override
    public Publication getPublication(long id) {
        return publicationRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Publication> getPublicationsByPublisher(long publisherId) {
        return null;
    }
}
