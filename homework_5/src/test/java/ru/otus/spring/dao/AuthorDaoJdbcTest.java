package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с авторами должно")
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    @Autowired
    AuthorDaoJdbc authorDao;

    @DisplayName("возвращать ожидаемого автора по его id")
    @Test
    void shouldGetById() {
        Author testAuthor = authorDao.getByNameInsertNew("Test author");
        Author actualAuthor = authorDao.getById(testAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(testAuthor);
    }

    @DisplayName("возвращать ожидаемого автора по его имени")
    @Test
    void shouldGetByName() {
        Author testAuthor = authorDao.getByNameInsertNew("Test author");
        Author actualAuthor = authorDao.getByName(testAuthor.getName());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(testAuthor);
    }

    @DisplayName("добавлять автора в базу")
    @Test
    void shouldGetByNameInsertNew() {
        String testName = "Test author";
        Author testAuthor = authorDao.getByNameInsertNew(testName);
        assertThat(testAuthor.getName()).isEqualTo(testName);
    }

    @DisplayName("возвращать список всех авторов")
    @Test
    void shouldGetAll() {
        Author testAuthor1 = authorDao.getByNameInsertNew("Test author 1");
        Author testAuthor2 = authorDao.getByNameInsertNew("Test author 2");
        List<Author> testList = Arrays.asList(testAuthor1, testAuthor2);
        List<Author> actualList = authorDao.getAll();
        assertThat(actualList).isEqualTo(testList);
    }

    @DisplayName("корректно изменять имя автора")
    @Test
    void shouldUpdate() {
        String updatedName = "Updated author name";
        Author testAuthor = authorDao.getByNameInsertNew("Test author");
        Author updatedAuthor = new Author(testAuthor.getId(), updatedName);
        authorDao.update(updatedAuthor);
        Author actualAuthor = authorDao.getById(testAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(updatedAuthor);
    }
}