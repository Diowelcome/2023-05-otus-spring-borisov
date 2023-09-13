package ru.otus.spring.dao;

import ru.otus.spring.domain.Author;

import java.util.List;

public interface AuthorDao {
    Author getById(Long id);

    Author getByName(String name);

    Author getByNameInsertNew(String name);

    List<Author> getAll();

    void update(Author author);
}
