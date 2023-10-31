package ru.otus.spring.repository;

import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Optional<Book> getById(Long id);

    Book getByTitle(String title);

    List<Book> getAll();

    Book insert(Book book);

    Book update(Book book);

    void delete(Long id);
}
