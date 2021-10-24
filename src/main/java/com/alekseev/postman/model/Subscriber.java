package com.alekseev.postman.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Subscriber {

    private Long id;

    private String firstName;
    private String lastName;
    private String middleName;
    private String address;
    private String phone;
    private String email;

    private List<Subscription> subscriptions;

    public Subscriber() {
    }

    public Subscriber(Long id) {
        this.id = id;
    }

}
