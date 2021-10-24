package com.alekseev.postman.model.builder;

import com.alekseev.postman.model.Publication;
import com.alekseev.postman.model.Publisher;

public final class PublicationBuilder {

    private Long id;
    private String name;
    private String about;
    private Double cost;
    private Integer pages;
    private Integer weight;
    private Publisher publisher;

    private PublicationBuilder() {
    }

    public static PublicationBuilder newBuilder() {
        return new PublicationBuilder();
    }

    public PublicationBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public PublicationBuilder name(String name) {
        this.name = name;
        return this;
    }

    public PublicationBuilder about(String about) {
        this.about = about;
        return this;
    }

    public PublicationBuilder cost(Double cost) {
        this.cost = cost;
        return this;
    }

    public PublicationBuilder pages(Integer pages) {
        this.pages = pages;
        return this;
    }

    public PublicationBuilder weight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public PublicationBuilder publisher(Publisher publisher) {
        this.publisher = publisher;
        return this;
    }

    public Publication build() {
        Publication publication = new Publication();
        publication.setId(id);
        publication.setName(name);
        publication.setAbout(about);
        publication.setCost(cost);
        publication.setPages(pages);
        publication.setWeight(weight);
        publication.setPublisher(publisher);
        return publication;
    }

}
