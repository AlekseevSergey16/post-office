package com.alekseev.postman.repository;

import com.alekseev.postman.model.Postman;
import com.alekseev.postman.model.Publication;
import com.alekseev.postman.repository.mapper.PublicationMapper;
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
public class PublicationRepository {

    public static final RowMapper<Publication> PUBLICATION_MAPPER = JdbcTemplateMapperFactory.newInstance()
            .newRowMapper(Publication.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public PublicationRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long insert(Publication publication) {
        final String sql = """
                INSERT INTO publication (publication_name, about, cost, pages, weight, periodicity, publisher_id)
                VALUES (:name, :about, :cost, :pages, :weight, :periodicity, :publisherId)
                """;
        var params = new MapSqlParameterSource()
                .addValue("name", publication.getName())
                .addValue("about", publication.getAbout())
                .addValue("cost", publication.getCost())
                .addValue("pages", publication.getPages())
                .addValue("weight", publication.getWeight())
                .addValue("periodicity", publication.getPeriodicity())
                .addValue("publisherId", publication.getPublisher().getId());

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
            SELECT publication.id,
                   publication.publication_name AS name,
                   publication.about,
                   publication.cost,
                   publication.pages,
                   publication.weight,
                   publication.periodicity,
                   publisher.id          AS publisher_id,
                   publisher.publisher_name,
                   publisher.phone       AS publisher_phone,
                   publisher.email       AS publisher_email,
                   publisher.information AS publisher_information
            FROM publication
                     INNER JOIN publisher ON publication.publisher_id = publisher.id
            WHERE publication.id = :id
            """;
        var params = new MapSqlParameterSource()
                .addValue("id", id);

        return jdbcTemplate.query(sql, params, PUBLICATION_MAPPER)
                .stream().findAny();
    }

    public List<Publication> findByPublisherId(long publisherId) {
        final String sql = """
            SELECT publication.id,
                   publication.publication_name AS name,
                   publication.about,
                   publication.cost,
                   publication.pages,
                   publication.weight,
                   publication.periodicity,
                   publisher.id          AS publisher_id,
                   publisher.publisher_name,
                   publisher.phone       AS publisher_phone,
                   publisher.email       AS publisher_email,
                   publisher.information AS publisher_information
            FROM publication
                     INNER JOIN publisher ON publication.publisher_id = publisher.id
            WHERE publication.publisher_id = :publisherId
            """;
        var params = new MapSqlParameterSource()
                .addValue("publisherId", publisherId);

        return jdbcTemplate.query(sql, params, PUBLICATION_MAPPER);
    }

    public List<Publication> findAll() {
        final String sql = """
            SELECT publication.id,
                   publication.publication_name AS name,
                   publication.about,
                   publication.cost,
                   publication.pages,
                   publication.weight,
                   publication.periodicity,
                   publisher.id          AS publisher_id,
                   publisher.publisher_name,
                   publisher.phone       AS publisher_phone,
                   publisher.email       AS publisher_email,
                   publisher.information AS publisher_information
            FROM publication
                     INNER JOIN publisher ON publication.publisher_id = publisher.id
            """;

        return jdbcTemplate.query(sql, PUBLICATION_MAPPER);
    }

}
