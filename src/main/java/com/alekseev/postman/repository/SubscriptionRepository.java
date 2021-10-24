package com.alekseev.postman.repository;

import com.alekseev.postman.model.Subscription;
import com.alekseev.postman.repository.mapper.SubscriptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class SubscriptionRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public SubscriptionRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long insert(long subscriberId, long publicationId, LocalDate startDate, LocalDate endDate, int numberOfMonths) {
        final String sql = """
                INSERT INTO subscription (subscriber_id, publication_id, start_date, end_date, number_of_months)
                VALUES (:subscriberId, :publicationId, :startDate, :endDate, :numberOfMonths)
                """;
        var params = new MapSqlParameterSource()
                .addValue("subscriberId", subscriberId)
                .addValue("publicationId", publicationId)
                .addValue("startDate", startDate)
                .addValue("endDate", endDate)
                .addValue("numberOfMonths", numberOfMonths);
        var keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, params, keyHolder);

        return (long) keyHolder.getKeys().get("id");
    }

    public List<Subscription> findBySubscriberId(long subscriberId) {
        final String sql = """
                SELECT subscription.id        AS subscription_id,
                       subscription.start_date,
                       subscription.end_date,
                       subscription.number_of_months,
                                
                       subscriber.id          AS sub_id,
                       subscriber.first_name  AS sub_first_name,
                       subscriber.last_name   AS sub_last_name,
                       subscriber.middle_name AS sub_last_name,
                       subscriber.address     AS sub_last_name,
                       subscriber.phone       AS sub_last_name,
                       subscriber.email       AS sub_last_name,
                                
                       publication.id         AS publication_id,
                       publication.name       AS publication_name,
                       publication.about,
                       publication.cost,
                       publication.pages,
                       publication.weight
                FROM subscription
                         INNER JOIN subscriber ON subscription.subscriber_id = subscriber.id
                         INNER JOIN publication ON subscription.publication_id = publication.id
                WHERE subscription.subscriber_id = :subscriberId
                """;

        var params = new MapSqlParameterSource()
                .addValue("subscriberId", subscriberId);

        return jdbcTemplate.query(sql, params, SubscriptionMapper.MAPPER);
    }

}
