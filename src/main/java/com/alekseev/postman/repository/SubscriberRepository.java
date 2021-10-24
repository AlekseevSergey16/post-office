package com.alekseev.postman.repository;

import com.alekseev.postman.model.Subscriber;
import com.alekseev.postman.model.Subscription;
import com.alekseev.postman.repository.mapper.SubscriptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SubscriberRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public SubscriberRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long insert(String firstName, String lastName, String middleName, String address, String phone, String email) {
        final String sql = """
                INSERT INTO subscriber (first_name, last_name, middle_name, address, phone, email)
                VALUES (:firstName, :lastName, :middleName, :address, :phone, :email)
                """;
        var params = new MapSqlParameterSource()
                .addValue("firstName", firstName)
                .addValue("lastName", lastName)
                .addValue("middleName", middleName)
                .addValue("address", address)
                .addValue("phone", phone)
                .addValue("email", email);

        var keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, params, keyHolder);

        return (long) keyHolder.getKeys().get("id");
    }

    public Optional<Subscriber> findById(long id) {
        final String sql = """
                SELECT subscriber.id          AS sub_id,
                       subscriber.first_name  AS sub_first_name,
                       subscriber.last_name   AS sub_last_name,
                       subscriber.middle_name AS sub_last_name,
                       subscriber.address     AS sub_last_name,
                       subscriber.phone       AS sub_last_name,
                       subscriber.email       AS sub_last_name
                FROM subscriber
                """;

        Optional<Subscriber> subscriber = Optional
                .ofNullable(jdbcTemplate.getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper<Subscriber>(), id));
        subscriber.ifPresent(sub -> sub.setSubscriptions(findSubscriptionBySubscriberId(id)));

        return jdbcTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Subscriber>(), id)
                .stream().findAny();
    }

    private List<Subscription> findSubscriptionBySubscriberId(long subscriberId) {
        final String sql = """
                SELECT subscription.id        AS subscription_id,
                       subscription.start_date,
                       subscription.end_date,
                       subscription.number_of_months,
                       publication.id         AS publication_id,
                       publication.name       AS publication_name,
                       publication.about,
                       publication.cost,
                       publication.pages,
                       publication.weight
                FROM subscription
                         INNER JOIN publication ON subscription.publication_id = publication.id
                WHERE subscription.subscriber_id = :subscriberId
                """;

        var params = new MapSqlParameterSource()
                .addValue("subscriberId", subscriberId);

        return jdbcTemplate.query(sql, params, SubscriptionMapper.MAPPER);
    }

}
