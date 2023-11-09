package ru.otus.spring.repository;

import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    Optional<Genre> getById(Long id);

    Genre getByName(String name);

    Genre insertByNameWithoutCheck(String name);

    List<Genre> getAll();

    Genre update(Genre genre);
}
