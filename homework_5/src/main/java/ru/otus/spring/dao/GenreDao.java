package ru.otus.spring.dao;

import ru.otus.spring.domain.Genre;

import java.util.List;

public interface GenreDao {
    Genre getById(Long id);

    Genre getByName(String name);

    Genre getByNameInsertNew(String name);

    List<Genre> getAll();

    void update(Genre genre);
}