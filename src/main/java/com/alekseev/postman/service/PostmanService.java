package com.alekseev.postman.service;

import com.alekseev.postman.model.Postman;

import java.util.List;

public interface PostmanService {

    long addPostman(Postman postman);
    Postman getById(long id);
    List<Postman> getPostmen();
    void deletePostman(long id);

}
