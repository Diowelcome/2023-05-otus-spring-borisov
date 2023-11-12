package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Repository для работы с авторами должно")
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class AuthorRepositoryJpaTest {

    @Autowired
    AuthorRepository authorRepository;

    @DisplayName("возвращать ожидаемого автора по его id")
    @Test
    void shouldGetById() {
        Author testAuthor = authorRepository.save(new Author(0, "Test author"));
        Author actualAuthor = authorRepository.findById(testAuthor.getId()).orElse(null);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(testAuthor);
    }

    @DisplayName("возвращать ожидаемого автора по его имени")
    @Test
    void shouldGetByName() {
        String testName = "Test author";
        Author testAuthor = authorRepository.findByName(testName);
        if (testAuthor == null) {
            testAuthor = authorRepository.save(new Author(0, testName));
        }
        Author actualAuthor = authorRepository.findByName(testAuthor.getName());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(testAuthor);
    }

    @DisplayName("добавлять автора в базу")
    @Test
    void shouldinsertByNameWithoutCheck() {
        String testName = "Test author";
        Author testAuthor = authorRepository.save(new Author(0, testName));
        assertThat(testAuthor.getName()).isEqualTo(testName);
    }

    @DisplayName("возвращать список всех авторов")
    @Test
    void shouldGetAll() {
        List<Author> testList = authorRepository.findAll();
        Author testAuthor1 = authorRepository.save(new Author(0, "Test author 1"));
        Author testAuthor2 = authorRepository.save(new Author(0, "Test author 2"));
        testList.add(testAuthor1);
        testList.add(testAuthor2);
        List<Author> actualList = authorRepository.findAll();
        assertThat(actualList).isEqualTo(testList);
    }

    @DisplayName("корректно изменять имя автора")
    @Test
    void shouldUpdate() {
        String updatedName = "Updated author name";
        Author testAuthor = authorRepository.save(new Author(0, "Test author"));
        Author updatedAuthor = new Author(testAuthor.getId(), updatedName);
        authorRepository.save(updatedAuthor);
        Author actualAuthor = authorRepository.findById(testAuthor.getId()).orElse(null);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(updatedAuthor);
    }
}