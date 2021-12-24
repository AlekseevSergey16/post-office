package com.alekseev.postman.repository;

import com.alekseev.postman.model.Subscription;
import com.alekseev.postman.repository.mapper.SubscriptionMapper;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class SubscriptionRepository {

    public static final RowMapper<Subscription> SUBSCRIPTION_MAPPER = JdbcTemplateMapperFactory.newInstance()
            .ignorePropertyNotFound().newRowMapper(Subscription.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public SubscriptionRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long insert(Subscription subscription) {
        final String sql = """
                INSERT INTO subscription (subscriber_id, publication_id, start_date, end_date, number_of_months, cost_total)
                VALUES (:subscriberId, :publicationId, :startDate, :endDate, :numberOfMonths,
                        (SELECT publication.cost FROM publication WHERE publication.id = :publicationId) * :numberOfMonths)
                """;
        var params = new MapSqlParameterSource()
                .addValue("subscriberId", subscription.getSubscriber().getId())
                .addValue("publicationId", subscription.getPublication().getId())
                .addValue("startDate", LocalDate.now())
                .addValue("endDate", LocalDate.now().plusMonths(subscription.getNumberOfMonths()))
                .addValue("numberOfMonths", subscription.getNumberOfMonths());
        var keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, params, keyHolder);

        return (long) keyHolder.getKeys().get("id");
    }

    public List<Subscription> findBySubscriberId(long subscriberId) {
        final String sql = """
                SELECT subscription.id,
                       subscription.start_date,
                       subscription.end_date,
                       subscription.number_of_months,
                       subscription.cost_total,
                
                       subscriber.id          AS subscriber_id,
                       subscriber.first_name  AS subscriber_first_name,
                       subscriber.last_name   AS subscriber_last_name,
                       subscriber.middle_name AS subscriber_middle_name,
                       subscriber.phone       AS subscriber_phone,
                       subscriber.email       AS subscriber_email,
                
                       address.id             AS address_id,
                       address.street_name    AS address_street_name,
                       address.house_number   AS address_house_number,
                
                       publication.id         AS publication_id,
                       publication.publication_name,
                       publication.about      AS publication_about,
                       publication.cost       AS publication_about,
                       publication.pages      AS publication_about,
                       publication.weight     AS publication_about,
                
                       postman.id             AS postman_id,
                       postman.first_name     AS postman_id,
                       postman.last_name      AS postman_id,
                       postman.middle_name    AS postman_id
                FROM subscription
                         INNER JOIN subscriber ON subscription.subscriber_id = subscriber.id
                         INNER JOIN publication ON subscription.publication_id = publication.id
                         INNER JOIN address on subscriber.address_id = address.id
                         INNER JOIN postman on address.postman_id = postman.id
                WHERE subscription.subscriber_id = :subscriberId
                """;

        var params = new MapSqlParameterSource()
                .addValue("subscriberId", subscriberId);

        return jdbcTemplate.query(sql, params, SUBSCRIPTION_MAPPER);
    }

    public List<Subscription> findSubscriptionByPostmanId(long postmanId) {
        final String sql = """
                SELECT subscription.start_date,
                       subscription.end_date,
                       subscription.number_of_months,
                       subscription.cost_total,
                
                       subscriber.id            AS subscriber_id,
                       subscriber.first_name    AS subscriber_first_name,
                       subscriber.last_name     AS subscriber_last_name,
                       subscriber.middle_name   AS subscriber_middle_name,
                       subscriber.phone         AS subscriber_phone,
                       subscriber.email         AS subscriber_email,
                
                       address.id               AS subscriber_address_id,
                       address.street_name      AS subscriber_address_street_name,
                       address.house_number     AS subscriber_address_house_number,
                
                       publication.id           AS publication_id,
                       publication.publication_name,
                       publication.about        AS publication_about,
                       publication.cost         AS publication_cost,
                       publication.pages        AS publication_pages,
                       publication.weight       AS publication_weight,
                
                       publisher.id             AS publication_publisher_id,
                       publisher.publisher_name AS publication_publisher_name,
                       publisher.phone          AS publication_publisher_phone,
                       publisher.email          AS publication_publisher_email,
                       publisher.information    AS publication_publisher_information
                FROM subscription
                         INNER JOIN subscriber ON subscriber.id = subscription.subscriber_id
                         INNER JOIN address ON subscriber.address_id = address.id
                         INNER JOIN postman ON address.postman_id = postman.id
                         INNER JOIN publication ON publication.id = subscription.publication_id
                         INNER JOIN publisher ON publication.publisher_id = publisher.id
                WHERE postman.id = ?;
                """;

        return jdbcTemplate.getJdbcTemplate().query(sql, SUBSCRIPTION_MAPPER, postmanId);
    }

    public void delete(long id) {
        final String sql = """
                DELETE FROM subscription WHERE subscription.id = ?
                """;
        jdbcTemplate.getJdbcTemplate().update(sql, id);
    }

}
