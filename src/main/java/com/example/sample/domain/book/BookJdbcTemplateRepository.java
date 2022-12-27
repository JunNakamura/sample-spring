package com.example.sample.domain.book;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookJdbcTemplateRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BookJdbcTemplateRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

     List<String> findAllTitles() {
        String sql = "select title from book";
        SqlParameterSource params = new MapSqlParameterSource();
        return jdbcTemplate.queryForList(sql, params, String.class);
    }

}
