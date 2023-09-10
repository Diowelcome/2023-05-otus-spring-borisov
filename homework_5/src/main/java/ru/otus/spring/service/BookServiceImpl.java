package ru.otus.spring.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;

@Data
@Service
public class BookServiceImpl implements BookService {
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final BookDao bookDao;
    private final IOService ioService;

    public BookServiceImpl(AuthorDao authorDao, GenreDao genreDao, BookDao bookDao, IOService ioService) {
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.bookDao = bookDao;
        this.ioService = ioService;
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = bookDao.getAll();
        return books;
    }

    @Override
    public Book insert() {
        String title = ioService.readStringWithPrompt("Enter book title:");
        Book book = bookDao.getByTitle(title);
        if (book == null) {
            String authorName = ioService.readStringWithPrompt("Enter author name:");
            String genreName = ioService.readStringWithPrompt("Enter genre name:");
            Author author = authorDao.getByNameInsertNew(authorName);
            Genre genre = genreDao.getByNameInsertNew(genreName);
            bookDao.insert(title, author.getId(), genre.getId());
            book = bookDao.getByTitle(title);
            showBook(book);
        } else {
            ioService.outputString("Such book is already exists");
            showBook(book);
        }
        return book;
    }

    @Override
    public Book update() {
        Long id = Long.valueOf(ioService.readStringWithPrompt("Enter book id:"));
        Book book = bookDao.getById(id);
        if (book != null) {
            showBook(book);
            String title = getValue("Enter new book title or none for the current value:", book.getTitle());
            String authorName = getValue("Enter new author name or none for the current value:", book.getAuthor().getName());
            String genreName = getValue("Enter new genre name or none for the current value:", book.getGenre().getName());
            Author author = authorDao.getByNameInsertNew(authorName);
            Genre genre = genreDao.getByNameInsertNew(genreName);
            Book updatedBook = new Book(id, title, author, genre);
            bookDao.update(updatedBook);
            book = bookDao.getByTitle(title);
            showBook(book);
        } else {
            ioService.outputString("Such book does not exist");
        }
        return book;
    }

    @Override
    public void delete() {
        Long id = Long.valueOf(ioService.readStringWithPrompt("Enter book id:"));
        Book book = bookDao.getById(id);
        if (book != null) {
            showBook(book);
            if (ioService.readStringWithPrompt("Are you sure you want to delete this book (Y/N)?").equals("Y"))
                bookDao.delete(id);
        } else {
            ioService.outputString("Such book does not exist");
        }
    }

    @Override
    public void showAll() {
        getAll().forEach(this::showBook);
    }

    private void showBook(Book book) {
        ioService.outputString(book.toString());
    }

    private String getValue(String prompt, String defaultValue) {
        String result = ioService.readStringWithPrompt(prompt);
        return result.trim().length() > 0 ? result : defaultValue;
    }
}
