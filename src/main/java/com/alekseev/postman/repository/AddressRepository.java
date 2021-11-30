package com.alekseev.postman.repository;

import com.alekseev.postman.model.Address;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AddressRepository {

    public static final RowMapper<Address> ADDRESS_MAPPER = JdbcTemplateMapperFactory.newInstance()
            .ignorePropertyNotFound().newRowMapper(Address.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public AddressRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long insert(Address address) {
        final String sql = """
                INSERT INTO address (street_name, house_number, postman_id) 
                VALUES (:streetName, :houseNumber, :postmanId)
                """;
        var params = new MapSqlParameterSource()
                .addValue("streetName", address.getStreetName())
                .addValue("houseNumber", address.getHouseNumber())
                .addValue("postmanId", address.getPostmanId());
        var keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, params, keyHolder);

        return (long) keyHolder.getKeys().get("id");
    }

    public List<Address> findByPostmanId(long postmanId) {
        final String sql = """
                SELECT address.id, address.street_name, address.house_number, address.postman_id
                FROM address
                WHERE address.postman_id = ?
                """;

        return jdbcTemplate.getJdbcTemplate().query(sql, ADDRESS_MAPPER, postmanId);
    }

    public List<Address> findAll() {
        final String sql = """
                SELECT address.id, address.street_name, address.house_number, address.postman_id
                FROM address
                """;

        return jdbcTemplate.getJdbcTemplate().query(sql, ADDRESS_MAPPER);
    }

}
