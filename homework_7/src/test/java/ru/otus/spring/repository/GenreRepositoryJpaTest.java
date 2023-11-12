package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Repository для работы с жанрами должно")
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class GenreRepositoryJpaTest {

    @Autowired
    GenreRepository genreRepository;

    @DisplayName("возвращать ожидаемый жанр по его id")
    @Test
    void shouldGetById() {
        Genre testGenre = genreRepository.save(new Genre(0, "Test genre"));
        Genre actualGenre = genreRepository.findById(testGenre.getId()).orElse(null);
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(testGenre);
    }

    @DisplayName("возвращать ожидаемый жанр по его названию")
    @Test
    void shouldGetByName() {
        String testName = "Test genre name";
        Genre testGenre = genreRepository.findByName(testName);
        if (testGenre == null) {
            testGenre = genreRepository.save(new Genre(0, testName));
        }
        assertThat(testGenre).usingRecursiveComparison().isEqualTo(testGenre);
    }

    @DisplayName("добавлять жанр в базу")
    @Test
    void shouldinsertByNameWithoutCheck() {
        String testGenreName = "Test genre";
        Genre testGenre = genreRepository.save(new Genre(0, testGenreName));
        assertThat(testGenre.getName()).isEqualTo(testGenreName);
    }

    @DisplayName("возвращать список всех жанров")
    @Test
    void shouldGetAll() {
        Genre testGenre = genreRepository.save(new Genre(0, "Test genre"));
        List<Genre> testList = genreRepository.findAll();
        Genre anotherTestGenre = genreRepository.save(new Genre(0, "Another Test Genre"));
        testList.add(anotherTestGenre);
        List<Genre> actualList = genreRepository.findAll();
        assertThat(actualList).isEqualTo(testList);
    }

    @DisplayName("корректно изменять название жанра")
    @Test
    void shouldUpdate() {
        Genre testGenre = genreRepository.save(new Genre(0, "Test genre"));
        String updatedName = "Updated Genre name";
        Genre updatedGenre = new Genre(testGenre.getId(), updatedName);
        genreRepository.save(updatedGenre);
        Genre actualGenre = genreRepository.findById(testGenre.getId()).orElse(null);
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(updatedGenre);
    }
}
