package com.alekseev.postman.repository;

import com.alekseev.postman.model.Publisher;
import com.alekseev.postman.repository.mapper.PublisherMapper;
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
public class PublisherRepository {

    private static final RowMapper<Publisher> PUBLISHER_MAPPER = JdbcTemplateMapperFactory.newInstance()
            .ignorePropertyNotFound().newRowMapper(Publisher.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public PublisherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long insert(Publisher publisher) {
        final String sql = "INSERT INTO publisher (publisher_name, phone, email, information) " +
                            "VALUES (:name, :phone, :email, :information)";

        var params = new MapSqlParameterSource()
                .addValue("name", publisher.getName())
                .addValue("phone", publisher.getPhone())
                .addValue("email", publisher.getEmail())
                .addValue("information", publisher.getInformation());

        var keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, params, keyHolder);

        return (long) keyHolder.getKeys().get("id");
    }

    public void update(Long id, String name, String phone, String email, String information) {
        final String sql = "UPDATE publisher SET publisher_name = :name, phone = :phone, email = :email, information = :information "
                         + "WHERE id = :id";

        var params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("name", name)
                .addValue("phone", phone)
                .addValue("email", email)
                .addValue("information", information);

        jdbcTemplate.update(sql, params);
    }

    public Optional<Publisher> findById(long id) {
        final String sql = """
                    SELECT publisher.id,
                           publisher.publisher_name AS name,
                           publisher.phone,
                           publisher.email,
                           publisher.information
                    FROM publisher
                    WHERE id = ?;
                    """;
        return jdbcTemplate.getJdbcTemplate().query(sql, PUBLISHER_MAPPER, id)
                .stream().findAny();
    }

    public List<Publisher> findAllPublishers() {
        final String sql = """
                SELECT publisher.id,
                       publisher.publisher_name AS name,
                       publisher.phone,
                       publisher.email,
                       publisher.information
                FROM publisher;
                """;
        return jdbcTemplate.getJdbcTemplate().query(sql, PUBLISHER_MAPPER);
    }

}
