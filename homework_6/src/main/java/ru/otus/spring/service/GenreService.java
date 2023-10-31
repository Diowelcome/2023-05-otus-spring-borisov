package ru.otus.spring.service;

import ru.otus.spring.domain.Genre;

import java.util.List;

public interface GenreService {
    Genre getById(Long id);

    List<Genre> getAll();

    void showAll();

    Genre update();
}
