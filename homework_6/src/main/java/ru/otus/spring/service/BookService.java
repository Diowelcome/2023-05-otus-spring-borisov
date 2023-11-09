package ru.otus.spring.service;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookService {
    Book getById(Long id);

    Book getByTitle(String title);

    List<Book> getAll();

    Book insert(Book book);

    Book update(Book book);

    void delete(Book book);
}
