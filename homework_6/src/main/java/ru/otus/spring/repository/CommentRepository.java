package ru.otus.spring.repository;

import ru.otus.spring.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Optional<Comment> getById(Long id);

    List<Comment> getByBookId(Long bookId);

    List<Comment> getAll();

    Comment insert(Comment name);

    Comment update(Comment comment);

    void delete(Comment comment);

}
