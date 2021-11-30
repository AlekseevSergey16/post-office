package com.alekseev.postman.repository;

import com.alekseev.postman.model.Subscriber;
import com.alekseev.postman.model.Subscription;
import com.alekseev.postman.repository.mapper.SubscriptionMapper;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SubscriberRepository {

    public static final RowMapper<Subscriber> SUBSCRIBER_MAPPER = JdbcTemplateMapperFactory.newInstance()
            .ignorePropertyNotFound().newRowMapper(Subscriber.class);
    public static final RowMapper<Subscription> SUBSCRIPTION_MAPPER = JdbcTemplateMapperFactory.newInstance()
            .ignorePropertyNotFound().newRowMapper(Subscription.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public SubscriberRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long insert(Subscriber subscriber) {
        final String sql = """
                INSERT INTO subscriber (first_name, last_name, middle_name, address_id, phone, email)
                VALUES (:firstName, :lastName, :middleName, :address, :phone, :email)
                """;
        var params = new MapSqlParameterSource()
                .addValue("firstName", subscriber.getFirstName())
                .addValue("lastName", subscriber.getLastName())
                .addValue("middleName", subscriber.getMiddleName())
                .addValue("address", subscriber.getAddress().getId()) //todo
                .addValue("phone", subscriber.getPhone())
                .addValue("email", subscriber.getEmail());

        var keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, params, keyHolder);

        return (long) keyHolder.getKeys().get("id");
    }

    public Optional<Long> checkExistSubscriber(Subscriber subscriber) {
        final String sql = """
                SELECT id
                FROM subscriber
                WHERE subscriber.phone = :phone
                  AND subscriber.email = :email
                """;
        var params = new MapSqlParameterSource()
                .addValue("phone", subscriber.getPhone())
                .addValue("email", subscriber.getEmail());

        return jdbcTemplate.query(sql, params, (rs, rowNum) -> rs.getLong("id"))
                .stream().findAny();
    }

    public Optional<Subscriber> findById(long id) {
        final String sql = """
                SELECT subscriber.id,
                       subscriber.first_name,
                       subscriber.last_name,
                       subscriber.middle_name,
                       subscriber.phone,
                       subscriber.email,
                       address.id           AS address_id,
                       address.street_name  AS address_street_name,
                       address.house_number AS address_house_number
                FROM subscriber
                         INNER JOIN address ON subscriber.address_id = address.id
                WHERE subscriber.id = ?
                """;

        Optional<Subscriber> subscriber = Optional
                .ofNullable(jdbcTemplate.getJdbcTemplate().queryForObject(sql, SUBSCRIBER_MAPPER, id));
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
                       publication.publication_name,
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

        return jdbcTemplate.query(sql, params, SUBSCRIPTION_MAPPER);
    }

}
