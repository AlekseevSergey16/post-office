package com.alekseev.postman.model;

import lombok.Data;

@Data
public class Address {

    private Long id;
    private String street;
    private Integer houseNumber;
    private Integer apartmentNumber;

}
