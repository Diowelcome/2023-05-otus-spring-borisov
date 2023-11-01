package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Repository для работы с авторами должно")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {

    @Autowired
    AuthorRepositoryJpa authorRepository;

    @DisplayName("возвращать ожидаемого автора по его id")
    @Test
    void shouldGetById() {
        Author testAuthor = authorRepository.insertByNameWithoutCheck("Test author");
        Author actualAuthor = authorRepository.getById(testAuthor.getId()).orElse(null);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(testAuthor);
    }

    @DisplayName("возвращать ожидаемого автора по его имени")
    @Test
    void shouldGetByName() {
        Author testAuthor = authorRepository.insertByNameWithoutCheck("Test author");
        Author actualAuthor = authorRepository.getByName(testAuthor.getName());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(testAuthor);
    }

    @DisplayName("добавлять автора в базу")
    @Test
    void shouldinsertByNameWithoutCheck() {
        String testName = "Test author";
        Author testAuthor = authorRepository.insertByNameWithoutCheck(testName);
        assertThat(testAuthor.getName()).isEqualTo(testName);
    }

    @DisplayName("возвращать список всех авторов")
    @Test
    void shouldGetAll() {
        List<Author> testList = authorRepository.getAll();
        Author testAuthor1 = authorRepository.insertByNameWithoutCheck("Test author 1");
        Author testAuthor2 = authorRepository.insertByNameWithoutCheck("Test author 2");
        testList.add(testAuthor1);
        testList.add(testAuthor2);
        List<Author> actualList = authorRepository.getAll();
        assertThat(actualList).isEqualTo(testList);
    }

    @DisplayName("корректно изменять имя автора")
    @Test
    void shouldUpdate() {
        String updatedName = "Updated author name";
        Author testAuthor = authorRepository.insertByNameWithoutCheck("Test author");
        Author updatedAuthor = new Author(testAuthor.getId(), updatedName);
        authorRepository.update(updatedAuthor);
        Author actualAuthor = authorRepository.getById(testAuthor.getId()).orElse(null);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(updatedAuthor);
    }
}