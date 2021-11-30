package com.alekseev.postman.repository;

import com.alekseev.postman.model.Postman;
import com.alekseev.postman.model.Subscription;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostmanRepository {

    public static final RowMapper<Postman> POSTMAN_MAPPER = JdbcTemplateMapperFactory.newInstance()
            .ignorePropertyNotFound().newRowMapper(Postman.class);
    public static final RowMapper<Subscription> SUBSCRIPTION_MAPPER = JdbcTemplateMapperFactory.newInstance()
            .ignorePropertyNotFound().newRowMapper(Subscription.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public PostmanRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long insert(Postman postman) {
        final String sql = """
                INSERT INTO postman (first_name, last_name, middle_name) VALUES (:firstName, :lastName, :middleName);
                """;
        var params = new MapSqlParameterSource()
                .addValue("firstName", postman.getFirstName())
                .addValue("lastName", postman.getLastName())
                .addValue("middleName", postman.getMiddleName());
        var keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, params, keyHolder);

        return (long) keyHolder.getKeys().get("id");
    }

    public List<Postman> findAll() {
        final String sql = """
                SELECT postman.id, postman.first_name, postman.last_name, postman.middle_name
                FROM postman
                """;
        return jdbcTemplate.query(sql, POSTMAN_MAPPER);
    }

    public Optional<Postman> findById(long id) {
        final String sql = """
                SELECT postman.id, postman.first_name, postman.last_name, postman.middle_name
                FROM postman
                WHERE postman.id = ?
                """;
        Optional<Postman> postman = jdbcTemplate.getJdbcTemplate().query(sql, POSTMAN_MAPPER, id)
                .stream().findAny();
//        postman.ifPresent(x -> x.setSubscriptions(findSubscriptionByPostmanId(id)));

        return postman;
    }

    private List<Subscription> findSubscriptionByPostmanId(long postmanId) {
        final String sql = """
                SELECT subscription.start_date,
                       subscription.end_date,
                       subscription.number_of_months,
                       subscription.cost_total,
                
                       subscriber.id            AS subscriber_id,
                       subscriber.first_name    AS subscriber_id,
                       subscriber.last_name     AS subscriber_id,
                       subscriber.middle_name   AS subscriber_id,
                       subscriber.phone         AS subscriber_id,
                       subscriber.email         AS subscriber_id,
                
                       address.id               AS subscriber_address_id,
                       address.street_name      AS subscriber_address_street_name,
                       address.house_number     AS subscriber_address_house_number,
                
                       publication.id           AS publication_id,
                       publication.publication_name,
                       publication.about        AS publication_about,
                       publication.cost         AS publication_about,
                       publication.pages        AS publication_about,
                       publication.weight       AS publication_about,
                
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

}
