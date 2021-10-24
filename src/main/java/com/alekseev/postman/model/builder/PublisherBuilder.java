package com.alekseev.postman.model.builder;

import com.alekseev.postman.model.Publisher;

public final class PublisherBuilder {

    private Long id;
    private String name;
    private String phone;
    private String email;
    private String information;

    private PublisherBuilder() {
    }

    public static PublisherBuilder newBuilder() {
        return new PublisherBuilder();
    }

    public PublisherBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public PublisherBuilder name(String name) {
        this.name = name;
        return this;
    }

    public PublisherBuilder phone(String phone) {
        this.phone = phone;
        return this;
    }

    public PublisherBuilder email(String email) {
        this.email = email;
        return this;
    }

    public PublisherBuilder information(String information) {
        this.information = information;
        return this;
    }

    public Publisher build() {
        Publisher publisher = new Publisher();
        publisher.setId(id);
        publisher.setName(name);
        publisher.setPhone(phone);
        publisher.setEmail(email);
        publisher.setInformation(information);
        return publisher;
    }
}
