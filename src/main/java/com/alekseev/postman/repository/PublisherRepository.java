package com.alekseev.postman.repository;

import com.alekseev.postman.model.Publisher;
import com.alekseev.postman.repository.mapper.PublisherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PublisherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public PublisherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long insert(String name, String phone, String email, String information) {
        final String sql = "INSERT INTO publisher (name, phone, email, information) VALUES (:name, :phone, :email, :information)";

        var params = new MapSqlParameterSource()
                .addValue("name", name)
                .addValue("phone", phone)
                .addValue("email", email)
                .addValue("information", information);

        var keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, params, keyHolder);

        return (long) keyHolder.getKeys().get("id");
    }

    public void update(Long id, String name, String phone, String email, String information) {
        final String sql = "UPDATE publisher SET name = :name, phone = :phone, email = :email, information = :information "
                         + "WHERE id = ?5";

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
                    SELECT id AS publisher_id, name AS publisher_name, phone, email, information
                    FROM publisher
                    WHERE id = ?;
                    """;
        return jdbcTemplate.getJdbcTemplate().query(sql, PublisherMapper.MAPPER, id)
                .stream().findAny();
    }

    public List<Publisher> findAllPublishers() {
        final String sql = """
                SELECT id AS publisher_id, name AS publisher_name, phone, email, information
                FROM publisher
                """;
        return jdbcTemplate.query(sql, PublisherMapper.MAPPER);
    }

}
