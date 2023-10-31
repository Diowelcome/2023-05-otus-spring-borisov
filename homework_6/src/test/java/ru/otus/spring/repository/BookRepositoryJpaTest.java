package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Repository для работы с книгами должно")
@DataJpaTest
@Import({BookRepositoryJpa.class, AuthorRepositoryJpa.class, GenreRepositoryJpa.class})
class BookRepositoryJpaTest {

    @Autowired
    private BookRepositoryJpa bookRepository;

    @Autowired
    private AuthorRepositoryJpa authorRepository;

    @Autowired
    private GenreRepositoryJpa genreRepository;

    @DisplayName("возвращать книгу по идентификатору")
    @Test
    void shouldGetById() {
        String testTitle = "Test title";
        Author testAuthor = authorRepository.getByNameInsertNew("Test Author");
        Genre testGenre = genreRepository.getByNameInsertNew("Test Genre");
        Book actualBook = new Book(0, testTitle, testAuthor, testGenre);
        actualBook = bookRepository.insert(actualBook);
        Long testId = actualBook.getId();
        Book expectedBook = new Book(testId, testTitle, testAuthor, testGenre);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("возвращать книгу по заголовку")
    @Test
    void shouldGetByTitle() {
        String testTitle = "Test title";
        Author testAuthor = authorRepository.getByNameInsertNew("Test Author");
        Genre testGenre = genreRepository.getByNameInsertNew("Test Genre");
        Book actualBook = new Book(0, testTitle, testAuthor, testGenre);
        actualBook = bookRepository.insert(actualBook);
        Long testId = actualBook.getId();
        Book expectedBook = new Book(testId, testTitle, testAuthor, testGenre);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("возвращать список всех книг")
    @Test
    void shouldGetAll() {
        String testTitle1 = "Test title 1";
        String testTitle2 = "Test title 2";
        Author testAuthor = authorRepository.getByNameInsertNew("Test Author");
        Genre testGenre = genreRepository.getByNameInsertNew("Test Genre");
        Book book1 = new Book(0, testTitle1, testAuthor, testGenre);
        Book book2 = new Book(0, testTitle2, testAuthor, testGenre);
        book1 = bookRepository.insert(book1);
        book2 = bookRepository.insert(book2);
        Book testBook1 = bookRepository.getByTitle("Test title 1");
        Book testBook2 = bookRepository.getByTitle("Test title 2");
        List<Book> testList = Arrays.asList(testBook1, testBook2);
        List<Book> actualList = bookRepository.getAll();
        assertThat(actualList).isEqualTo(testList);
    }

    @DisplayName("корректно добавлять книгу")
    @Test
    void shouldInsert() {
        String testTitle = "Test title";
        Author testAuthor = authorRepository.getByNameInsertNew("Test Author");
        Genre testGenre = genreRepository.getByNameInsertNew("Test Genre");
        Book actualBook = new Book(0, testTitle, testAuthor, testGenre);
        actualBook = bookRepository.insert(actualBook);
        Book expectedBook = new Book(actualBook.getId(), testTitle, testAuthor, testGenre);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("корректно изменять название, автора и жанр книги")
    @Test
    void update() {
        String testTitle = "Test title";
        String updatedTitle = "Updated title";
        Author testAuthor1 = authorRepository.getByNameInsertNew("Test author 1");
        Author testAuthor2 = authorRepository.getByNameInsertNew("Test author 2");
        Genre testGenre1 = genreRepository.getByNameInsertNew("Test genre 1");
        Genre testGenre2 = genreRepository.getByNameInsertNew("Test genre 2");
        Book originalBook = new Book(0, testTitle, testAuthor1, testGenre1);
        originalBook = bookRepository.insert(originalBook);
        Book updatedBook = new Book(originalBook.getId(), updatedTitle, testAuthor2, testGenre2);
        bookRepository.update(updatedBook);
        Book actualBook = bookRepository.getByTitle(updatedTitle);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(updatedBook);
    }

    @DisplayName("удалять книгу по идентификатору")
    @Test
    void shouldDelete() {

        String testTitle = "Test title";
        Author testAuthor = authorRepository.getByNameInsertNew("Test Author");
        Genre testGenre = genreRepository.getByNameInsertNew("Test Genre");
        Book actualBook = new Book(0, testTitle, testAuthor, testGenre);
        actualBook = bookRepository.insert(actualBook);
        Book insertedBook = new Book(actualBook.getId(), testTitle, testAuthor, testGenre);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(insertedBook);
        bookRepository.delete(actualBook.getId());
        assertThat(bookRepository.getByTitle(testTitle)).isEqualTo(null);
    }
}