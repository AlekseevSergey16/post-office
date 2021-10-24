package com.alekseev.postman.repository.mapper;

import com.alekseev.postman.model.Subscription;
import com.alekseev.postman.model.builder.SubscriptionBuilder;
import org.springframework.jdbc.core.RowMapper;

public class SubscriptionMapper {

    public static final RowMapper<Subscription> MAPPER = (rs, rowNum) ->
        SubscriptionBuilder.newBuilder()
                .id(rs.getLong("subscription_id"))
                .startDate(rs.getDate("start_date").toLocalDate())
                .endDate(rs.getDate("end_date").toLocalDate())
                .numberOfMonths(rs.getInt("number_of_months"))
//                .subscriber(SubscriberMapper.MAPPER.mapRow(rs, rowNum))
                .publication(PublicationMapper.MAPPER.mapRow(rs, rowNum))
                .build();

}
