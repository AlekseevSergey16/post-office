package com.alekseev.postman.repository;

import com.alekseev.postman.model.Publication;
import com.alekseev.postman.repository.mapper.PublicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class PublicationRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public PublicationRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long insert(String name, String about, double cost, int pages, int weight, long publisherId) {
        final String sql = """
                INSERT INTO publication (publication_name, about, cost, pages, weight, publisher_id)
                VALUES (:name, :about, :cost, :pages, :weight, :publisherId)
                """;
        var params = new MapSqlParameterSource()
                .addValue("name", name)
                .addValue("about", about)
                .addValue("cost", cost)
                .addValue("pages", pages)
                .addValue("weight", weight)
                .addValue("publisherId", publisherId);

        var keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, params, keyHolder);

        return (long) keyHolder.getKeys().get("id");
    }

    public void update(long id, String name, String about, double cost, int pages, int weight) {
        final String sql = """
                UPDATE publication SET publication_name = :name, about = :about, cost = :cost, pages = :pages, weight = :weight
                WHERE id = :id
                """;
        var params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("name", name)
                .addValue("about", about)
                .addValue("cost", cost)
                .addValue("pages", pages)
                .addValue("weight", weight);

        jdbcTemplate.update(sql, params);
    }

    public Optional<Publication> findById(long id) {
        final String sql = """
            SELECT publication.id AS publication_id,
                   publication_name,
                   publication.about,
                   publication.cost,
                   publication.pages,
                   publication.weight,
                   publisher.id AS publisher_id,
                   publisher.publisher_name,
                   publisher.phone,
                   publisher.email,
                   publisher.information
            FROM publication
            INNER JOIN publisher ON publication.publisher_id = publisher.id
            WHERE publication.id = :id
            """;
        var params = new MapSqlParameterSource()
                .addValue("id", id);

        return jdbcTemplate.query(sql, params, PublicationMapper.MAPPER)
                .stream().findAny();
    }

}
