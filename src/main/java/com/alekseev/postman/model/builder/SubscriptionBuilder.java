package com.alekseev.postman.model.builder;

import com.alekseev.postman.model.Publication;
import com.alekseev.postman.model.Subscriber;
import com.alekseev.postman.model.Subscription;

import java.time.LocalDate;

public final class SubscriptionBuilder {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer numberOfMonths;
    private Subscriber subscriber;
    private Publication publication;

    private SubscriptionBuilder() {
    }

    public static SubscriptionBuilder newBuilder() {
        return new SubscriptionBuilder();
    }

    public SubscriptionBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public SubscriptionBuilder startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public SubscriptionBuilder endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public SubscriptionBuilder numberOfMonths(Integer numberOfMonths) {
        this.numberOfMonths = numberOfMonths;
        return this;
    }

    public SubscriptionBuilder subscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
        return this;
    }

    public SubscriptionBuilder publication(Publication publication) {
        this.publication = publication;
        return this;
    }

    public Subscription build() {
        Subscription subscription = new Subscription();
        subscription.setId(id);
        subscription.setStartDate(startDate);
        subscription.setEndDate(endDate);
        subscription.setNumberOfMonths(numberOfMonths);
        subscription.setSubscriber(subscriber);
        subscription.setPublication(publication);
        return subscription;
    }
}
