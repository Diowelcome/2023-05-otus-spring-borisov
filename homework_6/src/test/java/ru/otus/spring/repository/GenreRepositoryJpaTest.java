package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Repository для работы с жанрами должно")
@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {

    @Autowired
    GenreRepositoryJpa genreRepository;

    @DisplayName("возвращать ожидаемый жанр по его id")
    @Test
    void shouldGetById() {
        Genre testGenre = genreRepository.getByNameInsertNew("Test genre");
        Genre actualGenre = genreRepository.getById(testGenre.getId()).orElse(null);
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(testGenre);
    }

    @DisplayName("возвращать ожидаемый жанр по его названию")
    @Test
    void shouldGetByName() {
        Genre testGenre = genreRepository.getByNameInsertNew("Test genre");
        Genre actualGenre = genreRepository.getByName(testGenre.getName());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(testGenre);
    }

    @DisplayName("добавлять жанр в базу")
    @Test
    void shouldGetByNameInsertNew() {
        String testGenreName = "Test genre";
        Genre testGenre = genreRepository.getByNameInsertNew(testGenreName);
        assertThat(testGenre.getName()).isEqualTo(testGenreName);
    }

    @DisplayName("возвращать список всех жанров")
    @Test
    void shouldGetAll() {
        Genre testGenre = genreRepository.getByNameInsertNew("Test genre");
        List<Genre> testList = genreRepository.getAll();
        Genre anotherTestGenre = genreRepository.getByNameInsertNew("Another Test Genre");
        testList.add(anotherTestGenre);
        List<Genre> actualList = genreRepository.getAll();
        assertThat(actualList).isEqualTo(testList);
    }

    @DisplayName("корректно изменять название жанра")
    @Test
    void shouldUpdate() {
        Genre testGenre = genreRepository.getByNameInsertNew("Test genre");
        String updatedName = "Updated Genre name";
        Genre updatedGenre = new Genre(testGenre.getId(), updatedName);
        genreRepository.update(updatedGenre);
        Genre actualGenre = genreRepository.getById(testGenre.getId()).orElse(null);
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(updatedGenre);
    }
}
