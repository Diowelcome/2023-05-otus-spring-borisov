package ru.otus.spring.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.GenreRepository;

import java.util.List;

@Data
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final IOService ioService;

    public BookServiceImpl(AuthorRepository authorRepository, GenreRepository genreRepository, BookRepository bookRepository, IOService ioService) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
        this.ioService = ioService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAll() {
        List<Book> books = bookRepository.getAll();
        return books;
    }

    @Override
    @Transactional
    public Book insert() {
        String title = ioService.readStringWithPrompt("Enter book title:");
        Book book = bookRepository.getByTitle(title);
        if (book == null) {
            String authorName = ioService.readStringWithPrompt("Enter author name:");
            String genreName = ioService.readStringWithPrompt("Enter genre name:");
            Author author = authorRepository.getByNameInsertNew(authorName);
            Genre genre = genreRepository.getByNameInsertNew(genreName);
            book = new Book(0, title, author, genre);
            book = bookRepository.insert(book);
            showBook(book);
        } else {
            ioService.outputString("Such book is already exists");
            showBook(book);
        }
        return book;
    }

    @Override
    @Transactional
    public Book update() {
        Long id = Long.valueOf(ioService.readStringWithPrompt("Enter book id:"));
        Book book = bookRepository.getById(id).orElse(null);
        if (book != null) {
            showBook(book);
            String title = getValue("Enter new book title or none for the current value:", book.getTitle());
            String authorName = getValue("Enter new author name or none for the current value:", book.getAuthor().getName());
            String genreName = getValue("Enter new genre name or none for the current value:", book.getGenre().getName());
            Author author = authorRepository.getByNameInsertNew(authorName);
            Genre genre = genreRepository.getByNameInsertNew(genreName);
            Book updatedBook = new Book(id, title, author, genre);

            book = bookRepository.update(updatedBook);
            showBook(book);
        } else {
            ioService.outputString("Such book does not exist");
        }
        return book;
    }

    @Override
    @Transactional
    public void delete() {
        Long id = Long.valueOf(ioService.readStringWithPrompt("Enter book id:"));
        Book book = bookRepository.getById(id).orElse(null);
        if (book != null) {
            showBook(book);
            if (ioService.readStringWithPrompt("Are you sure you want to delete this book (Y/N)?").equals("Y"))
                bookRepository.delete(id);
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
