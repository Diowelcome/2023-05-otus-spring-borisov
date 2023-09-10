package ru.otus.spring.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.spring.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements RowMapper<Author> {
    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        return new Author(id, name);
    }
}
