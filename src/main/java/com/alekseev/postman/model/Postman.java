package com.alekseev.postman.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Postman {

    private Long id;

    private String firstName;
    private String lastName;
    private String middleName;

    private List<Subscription> subscriptions = new ArrayList<>();

}
