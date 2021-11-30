package com.alekseev.postman.service.impl;

import com.alekseev.postman.model.Postman;
import com.alekseev.postman.repository.PostmanRepository;
import com.alekseev.postman.service.PostmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostmanServiceImpl implements PostmanService {

    private final PostmanRepository postmanRepository;

    @Autowired
    public PostmanServiceImpl(PostmanRepository postmanRepository) {
        this.postmanRepository = postmanRepository;
    }

    @Override
    public long addPostman(Postman postman) {
        return postmanRepository.insert(postman);
    }

    @Override
    public Postman getById(long id) {
        return postmanRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Postman> getPostmen() {
        return postmanRepository.findAll();
    }

}
