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

    public void delete(long id) {
        final String sql = """
                DELETE FROM postman WHERE postman.id = ?
                """;
        jdbcTemplate.getJdbcTemplate().update(sql, id);
    }

}
