package ru.otus.spring.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("id");
        String title = rs.getString("title");
        Author author = new Author(rs.getLong("author_id"), rs.getString("author_name"));
        Genre genre = new Genre(rs.getLong("genre_id"), rs.getString("genre_name"));
        return new Book(id, title, author, genre);
    }
}