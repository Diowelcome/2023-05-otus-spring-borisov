package ru.otus.spring.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;
import ru.otus.spring.mapper.BookMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Book getById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        List<Book> bookList = jdbc.query("select b.id, b.title, b.author_id, a.name as author_name, b.genre_id, g.name as genre_name " +
                "  from book b " +
                "  join author a on a.id = b.author_id " +
                "  join genre g on g.id = b.genre_id " +
                "where b.id = :id", params, new BookMapper());
        return bookList.isEmpty() ? null : bookList.get(0);
    }

    @Override
    public Book getByTitle(String title) {
        Map<String, Object> params = Collections.singletonMap("title", title);
        List<Book> bookList = jdbc.query("select b.id, b.title, b.author_id, a.name as author_name, b.genre_id, g.name as genre_name " +
                "  from book b " +
                "  join author a on a.id = b.author_id " +
                "  join genre g on g.id = b.genre_id " +
                "where upper(b.title) = upper(:title)", params, new BookMapper());
        return bookList.isEmpty() ? null : bookList.get(0);
    }

    @Override
    public List<Book> getAll() {
        List<Book> bookList = jdbc.query("select b.id, b.title, b.author_id, a.name as author_name, b.genre_id, g.name as genre_name " +
                "  from book b " +
                "  join author a on a.id = b.author_id " +
                "  join genre g on g.id = b.genre_id ", new BookMapper());
        return bookList;
    }

    @Override
    public void insert(String title, Long authorId, Long genreId) {
        jdbc.update("insert into book (title, author_id, genre_id) values (:title, :author_id, :genre_id)",
                Map.of("title", title, "author_id", authorId, "genre_id", genreId));
    }

    @Override
    public void update(Book book) {
        jdbc.update("update book set title = :title, author_id = :author_id, genre_id = :genre_id where id = :id",
                Map.of("title", book.getTitle(), "author_id", book.getAuthor().getId(), "genre_id", book.getGenre().getId(), "id", book.getId()));
    }

    @Override
    public void delete(Long id) {
        jdbc.update("delete from book where id = :id",
                Map.of("id", id));
    }
}
