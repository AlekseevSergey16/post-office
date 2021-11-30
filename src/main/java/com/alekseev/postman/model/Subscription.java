package com.alekseev.postman.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Subscription {

    private Long id;

    private Integer numberOfMonths;

    private LocalDate startDate;
    private LocalDate endDate;

    private Subscriber subscriber;

    private Publication publication;

    public Subscription() {
    }

    public Subscription(Long id) {
        this.id = id;
    }

}
