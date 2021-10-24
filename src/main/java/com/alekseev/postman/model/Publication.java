package com.alekseev.postman.model;

import lombok.Data;

@Data
public class Publication {

    private Long id;

    private String name;
    private String about;
    private Double cost;
    private Integer pages;
    private Integer weight;

    private Publisher publisher;

    public Publication() {
    }

    public Publication(Long id) {
        this.id = id;
    }

}
