package ru.otus.spring.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final IOService ioService;

    public CommentServiceImpl(CommentRepository commentRepository, BookRepository bookRepository, IOService ioService) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
        this.ioService = ioService;
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getById(Long id) {
        Optional<Comment> comments = commentRepository.getById(id);
        return comments.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getByBookId(Long bookId) {
        List<Comment> comments = commentRepository.getByBookId(bookId);
        return comments;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAll() {
        List<Comment> comments = commentRepository.getAll();
        return comments;
    }

    @Override
    @Transactional
    public Comment insert(Comment comment) {
        return commentRepository.insert(comment);
    }

    @Override
    @Transactional
    public Comment update(Comment comment) {
        return commentRepository.update(comment);
    }

    @Override
    @Transactional
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    private void showBook(Book book) {
        ioService.outputString(book.toString());
    }

    private String getValue(String prompt, String defaultValue) {
        String result = ioService.readStringWithPrompt(prompt);
        return result.trim().length() > 0 ? result : defaultValue;
    }

}
