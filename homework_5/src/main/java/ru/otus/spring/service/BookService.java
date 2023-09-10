package ru.otus.spring.service;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookService {
    List<Book> getAll();
    void showAll();

    Book insert();
    Book update();
    void delete();
}
