package com.alekseev.postman.model;

import lombok.Data;

@Data
public class Address {

    private Long id;
    private String streetName;
    private Integer houseNumber;
    private Long postmanId;

    public Address() {
    }

    public Address(Long id) {
        this.id = id;
    }
}
