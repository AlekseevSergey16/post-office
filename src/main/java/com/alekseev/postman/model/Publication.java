package com.alekseev.postman.model;

import lombok.Data;

@Data
public class Publication {

    private Long id;

    private String name;
    private String about;
    private Publisher publisher;
    private Double cost;
    private Integer pages;
    private Integer weight;
    private Integer periodicity;

    public Publication() {
    }

    public Publication(Long id) {
        this.id = id;
    }
}
