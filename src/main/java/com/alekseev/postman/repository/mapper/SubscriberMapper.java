package com.alekseev.postman.repository.mapper;


import com.alekseev.postman.model.Subscriber;
import com.alekseev.postman.model.builder.SubscriberBuilder;
import org.springframework.jdbc.core.RowMapper;

public class SubscriberMapper {

    public static final RowMapper<Subscriber> MAPPER = (rs, rowNum) ->
        SubscriberBuilder.newBuilder()
                .id(rs.getLong("sub_id"))
                .firstName(rs.getString("sub_first_name"))
                .lastName(rs.getString("sub_last_name"))
                .middleName(rs.getString("sub_middle_name"))
                .address(rs.getString("sub_address"))
                .phone(rs.getString("sub_phone"))
                .email(rs.getString("sub_email"))
                .build();

}
