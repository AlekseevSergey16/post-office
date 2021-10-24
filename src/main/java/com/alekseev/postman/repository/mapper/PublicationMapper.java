package com.alekseev.postman.repository.mapper;

import com.alekseev.postman.model.Publication;
import com.alekseev.postman.model.builder.PublicationBuilder;
import org.springframework.jdbc.core.RowMapper;

public class PublicationMapper {

    public static final RowMapper<Publication> MAPPER = (rs, rowNum) ->
        PublicationBuilder.newBuilder()
                .id(rs.getLong("publication_id"))
                .name(rs.getString("publication_name"))
                .about(rs.getString("about"))
                .cost(rs.getDouble("cost"))
                .pages(rs.getInt("pages"))
                .weight(rs.getInt("weight"))
                .publisher(PublisherMapper.MAPPER.mapRow(rs, rowNum))
                .build();

}
