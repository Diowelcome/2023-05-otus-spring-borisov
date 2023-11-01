package ru.otus.spring.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.repository.GenreRepository;

import java.util.List;

@Data
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    public BookServiceImpl(AuthorRepository authorRepository, GenreRepository genreRepository, BookRepository bookRepository, CommentRepository commentRepository) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Book getById(Long id) {
        return bookRepository.getById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Book getByTitle(String title) {
        return bookRepository.getByTitle(title);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAll() {
        List<Book> books = bookRepository.getAll();
        return books;
    }

    @Override
    @Transactional
    public Book insert(Book book) {
        return bookRepository.insert(book);
    }

    @Override
    @Transactional
    public Book update(Book book) {
        return bookRepository.update(book);
    }

    @Override
    @Transactional
    public void delete(Book book) {
        commentRepository.deleteByBookId(book.getId());
        bookRepository.delete(book);
    }

}
