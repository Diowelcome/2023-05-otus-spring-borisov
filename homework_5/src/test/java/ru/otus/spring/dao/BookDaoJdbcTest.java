package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("Dao для работы с книгами должно")
@JdbcTest
@Import({BookDaoJdbc.class, AuthorDaoJdbc.class, GenreDaoJdbc.class})
class BookDaoJdbcTest {

    @Autowired
    private BookDaoJdbc bookDao;

    @Autowired
    private AuthorDaoJdbc authorDao;

    @Autowired
    private GenreDaoJdbc genreDao;

    @DisplayName("возвращать книгу по идентификатору")
    @Test
    void shouldGetById() {
        String testTitle = "Test title";
        Author testAuthor = authorDao.getByNameInsertNew("Test Author");
        Genre testGenre = genreDao.getByNameInsertNew("Test Genre");
        bookDao.insert(testTitle, testAuthor.getId(), testGenre.getId());
        Long testId = bookDao.getByTitle(testTitle).getId();
        Book expectedBook = new Book(testId, testTitle, testAuthor, testGenre);
        Book actualBook = bookDao.getById(testId);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("возвращать книгу по заголовку")
    @Test
    void shouldGetByTitle() {
        String testTitle = "Test title";
        Author testAuthor = authorDao.getByNameInsertNew("Test Author");
        Genre testGenre = genreDao.getByNameInsertNew("Test Genre");
        bookDao.insert(testTitle, testAuthor.getId(), testGenre.getId());
        Long testId = bookDao.getByTitle(testTitle).getId();
        Book expectedBook = new Book(testId, testTitle, testAuthor, testGenre);
        Book actualBook = bookDao.getByTitle(testTitle);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("возвращать список всех книг")
    @Test
    void shouldGetAll() {
        String testTitle1 = "Test title 1";
        String testTitle2 = "Test title 2";
        Author testAuthor = authorDao.getByNameInsertNew("Test Author");
        Genre testGenre = genreDao.getByNameInsertNew("Test Genre");
        bookDao.insert(testTitle1, testAuthor.getId(), testGenre.getId());
        bookDao.insert(testTitle2, testAuthor.getId(), testGenre.getId());
        Book testBook1 = bookDao.getByTitle("Test title 1");
        Book testBook2 = bookDao.getByTitle("Test title 2");
        List<Book> testList = Arrays.asList(testBook1, testBook2);
        List<Book> actualList = bookDao.getAll();
        assertThat(actualList).isEqualTo(testList);
    }

    @DisplayName("корректно добавлять книгу")
    @Test
    void shouldInsert() {
        String testTitle = "Test title";
        Author testAuthor = authorDao.getByNameInsertNew("Test Author");
        Genre testGenre = genreDao.getByNameInsertNew("Test Genre");
        bookDao.insert(testTitle, testAuthor.getId(), testGenre.getId());
        Book actualBook = bookDao.getByTitle(testTitle);
        Book expectedBook = new Book(actualBook.getId(), testTitle, testAuthor, testGenre);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("корректно изменять название, автора и жанр книги")
    @Test
    void update() {
        String testTitle = "Test title";
        String updatedTitle = "Updated title";
        Author testAuthor1 = authorDao.getByNameInsertNew("Test author 1");
        Author testAuthor2 = authorDao.getByNameInsertNew("Test author 2");
        Genre testGenre1 = genreDao.getByNameInsertNew("Test genre 1");
        Genre testGenre2 = genreDao.getByNameInsertNew("Test genre 2");
        bookDao.insert(testTitle, testAuthor1.getId(), testGenre1.getId());
        Book updatedBook = new Book(bookDao.getByTitle(testTitle).getId(), updatedTitle, testAuthor2, testGenre2);
        bookDao.update(updatedBook);
        Book actualBook = bookDao.getByTitle(updatedTitle);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(updatedBook);
    }

    @DisplayName("удалять книгу по идентификатору")
    @Test
    void shouldDelete() {

        String testTitle = "Test title";
        Author testAuthor = authorDao.getByNameInsertNew("Test Author");
        Genre testGenre = genreDao.getByNameInsertNew("Test Genre");
        bookDao.insert(testTitle, testAuthor.getId(), testGenre.getId());
        Book actualBook = bookDao.getByTitle(testTitle);
        Book insertedBook = new Book(actualBook.getId(), testTitle, testAuthor, testGenre);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(insertedBook);
        assertThatCode(() -> bookDao.getById(actualBook.getId()))
                .doesNotThrowAnyException();
        bookDao.delete(actualBook.getId());
        assertThat(bookDao.getById(actualBook.getId())).isEqualTo(null);
    }
}