package ru.otus.spring.repository;

import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    Optional<Author> getById(Long id);

    Author getByName(String name);

    Author getByNameInsertNew(String name);

    List<Author> getAll();

    Author update(Author author);
}
