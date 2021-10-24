package com.alekseev.postman.model.builder;

import com.alekseev.postman.model.Subscriber;
import com.alekseev.postman.model.Subscription;

import java.util.List;

public final class SubscriberBuilder {

    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String address;
    private String phone;
    private String email;
    private List<Subscription> subscriptions;

    private SubscriberBuilder() {
    }

    public static SubscriberBuilder newBuilder() {
        return new SubscriberBuilder();
    }

    public SubscriberBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public SubscriberBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public SubscriberBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public SubscriberBuilder middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public SubscriberBuilder address(String address) {
        this.address = address;
        return this;
    }

    public SubscriberBuilder phone(String phone) {
        this.phone = phone;
        return this;
    }

    public SubscriberBuilder email(String email) {
        this.email = email;
        return this;
    }

    public SubscriberBuilder subscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
        return this;
    }

    public Subscriber build() {
        Subscriber subscriber = new Subscriber();
        subscriber.setId(id);
        subscriber.setFirstName(firstName);
        subscriber.setLastName(lastName);
        subscriber.setMiddleName(middleName);
        subscriber.setAddress(address);
        subscriber.setPhone(phone);
        subscriber.setEmail(email);
        subscriber.setSubscriptions(subscriptions);
        return subscriber;
    }
}
