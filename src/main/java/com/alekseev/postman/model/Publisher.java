package com.alekseev.postman.model;

import lombok.Data;

@Data
public class Publisher {

    private Long id;

    private String name;
    private String phone;
    private String email;
    private String information;

    public Publisher() {
    }

    public Publisher(Long id) {
        this.id = id;
    }

}
