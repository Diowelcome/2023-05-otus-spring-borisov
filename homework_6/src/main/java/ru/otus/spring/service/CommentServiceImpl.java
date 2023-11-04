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

    public CommentServiceImpl(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Comment getById(Long id) {
        Optional<Comment> comments = commentRepository.getById(id);
        return comments.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getByBook(Book book) {
        return book.getComments();
    }

    @Override
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

}
