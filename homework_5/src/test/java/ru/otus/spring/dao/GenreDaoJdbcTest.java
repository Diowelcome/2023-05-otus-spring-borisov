package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.transaction.BeforeTransaction;
import ru.otus.spring.domain.Genre;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с жанрами должно")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    private Genre testGenre;

    @Autowired
    GenreDaoJdbc genreDao;

    @BeforeTransaction
    void setUp() {
        this.testGenre = genreDao.getByNameInsertNew("Test Genre");
    }

    @DisplayName("возвращать ожидаемый жанр по его id")
    @Test
    void shouldGetById() {
        Genre actualGenre = genreDao.getById(testGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(testGenre);
    }

    @DisplayName("возвращать ожидаемый жанр по его названию")
    @Test
    void shouldGetByName() {
        Genre actualGenre = genreDao.getByName(testGenre.getName());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(testGenre);
    }

    @DisplayName("добавлять жанр в базу")
    @Test
    void shouldGetByNameInsertNew() {
        assertThat(testGenre.getName()).isEqualTo(this.testGenre.getName());
    }

    @DisplayName("возвращать список всех жанров")
    @Test
    void shouldGetAll() {
        List<Genre> testList = genreDao.getAll();
        Genre anotherTestGenre = genreDao.getByNameInsertNew("Another Test Genre");
        testList.add(anotherTestGenre);
        List<Genre> actualList = genreDao.getAll();
        assertThat(actualList).isEqualTo(testList);
    }

    @DisplayName("корректно изменять название жанра")
    @Test
    void shouldUpdate() {
        String updatedName = "Updated Genre name";
        Genre updatedGenre = new Genre(this.testGenre.getId(), updatedName);
        genreDao.update(updatedGenre);
        Genre actualGenre = genreDao.getById(this.testGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(updatedGenre);
    }
}
