package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookDao {
    Book getById(Long id);

    Book getByTitle(String title);

    List<Book> getAll();

    void insert(String title, Long authorId, Long genreId);

    void update(Book book);

    void delete(Long id);
}
