package ru.otus.spring.service;

import ru.otus.spring.domain.Author;

import java.util.List;

public interface AuthorService {
    Author getById(Long id);

    Author getByName(String name);

    List<Author> getAll();

    Author update(Author author);

    Author getAuthorByNameInsertNew(String authorName);
}
