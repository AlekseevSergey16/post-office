package com.alekseev.postman.repository.mapper;

import com.alekseev.postman.model.Publisher;
import com.alekseev.postman.model.builder.PublisherBuilder;
import org.springframework.jdbc.core.RowMapper;

public class PublisherMapper {

    public static final RowMapper<Publisher> MAPPER = (rs, rowNum) ->
        PublisherBuilder.newBuilder()
                .id(rs.getLong("publisher_id"))
                .name(rs.getString("publisher_name"))
                .phone(rs.getString("phone"))
                .email(rs.getString("email"))
                .information(rs.getString("information"))
                .build();

}
