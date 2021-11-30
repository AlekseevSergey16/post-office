package com.alekseev.postman.dto;

import com.alekseev.postman.model.Address;
import com.alekseev.postman.model.Publication;
import com.alekseev.postman.model.Subscriber;
import com.alekseev.postman.model.Subscription;
import com.alekseev.postman.model.builder.SubscriberBuilder;
import com.alekseev.postman.model.builder.SubscriptionBuilder;

public class Converter {

    public static Subscription from(CreateSubscriptionDto subscriptionDto) {
        Subscriber subscriber = SubscriberBuilder.newBuilder()
                .firstName(subscriptionDto.firstName)
                .lastName(subscriptionDto.lastName)
                .middleName(subscriptionDto.middleName)
                .address(new Address(subscriptionDto.addressId))
                .phone(subscriptionDto.phone)
                .email(subscriptionDto.email)
                .build();

        return SubscriptionBuilder.newBuilder()
                .subscriber(subscriber)
                .numberOfMonths(subscriptionDto.numberOfMonths)
                .publication(new Publication(subscriptionDto.publicationId))
                .build();
    }

}
