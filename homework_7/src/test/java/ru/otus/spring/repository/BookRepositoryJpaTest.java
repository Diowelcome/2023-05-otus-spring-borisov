package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Repository для работы с книгами должно")
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class BookRepositoryJpaTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("возвращать книгу по идентификатору")
    @Test
    void shouldGetById() {
        String testTitle = "Test title";
        Author testAuthor = authorRepository.save(new Author(0, "Test author"));
        Genre testGenre = genreRepository.save(new Genre(0, "Test genre"));
        Book actualBook = new Book(0, testTitle, testAuthor, testGenre, new ArrayList<>());
        actualBook = bookRepository.save(actualBook);
        Long testId = actualBook.getId();
        Book expectedBook = new Book(testId, testTitle, testAuthor, testGenre, new ArrayList<>());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("возвращать книгу по заголовку")
    @Test
    void shouldGetByTitle() {
        String testTitle = "Test title";
        Author testAuthor = authorRepository.save(new Author(0, "Test author"));
        Genre testGenre = genreRepository.save(new Genre(0, "Test genre"));
        Book actualBook = new Book(0, testTitle, testAuthor, testGenre, new ArrayList<>());
        actualBook = bookRepository.save(actualBook);
        Long testId = actualBook.getId();
        Book expectedBook = new Book(testId, testTitle, testAuthor, testGenre, new ArrayList<>());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("корректно добавлять книгу")
    @Test
    void shouldInsert() {
        String testTitle = "Test title";
        Author testAuthor = authorRepository.save(new Author(0, "Test author"));
        Genre testGenre = genreRepository.save(new Genre(0, "Test genre"));
        Book actualBook = new Book(0, testTitle, testAuthor, testGenre, new ArrayList<>());
        actualBook = bookRepository.save(actualBook);
        Book expectedBook = new Book(actualBook.getId(), testTitle, testAuthor, testGenre, new ArrayList<>());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("удалять книгу по идентификатору")
    @Test
    void shouldDelete() {

        String testTitle = "Test title";
        Author testAuthor = authorRepository.save(new Author(0, "Test author"));
        Genre testGenre = genreRepository.save(new Genre(0, "Test genre"));
        Book actualBook = new Book(0, testTitle, testAuthor, testGenre, new ArrayList<>());
        actualBook = bookRepository.save(actualBook);
        Book insertedBook = new Book(actualBook.getId(), testTitle, testAuthor, testGenre, new ArrayList<>());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(insertedBook);
        bookRepository.delete(actualBook);
        assertThat(bookRepository.findByTitle(testTitle)).isEqualTo(null);
    }
}