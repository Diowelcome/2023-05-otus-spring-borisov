package ru.otus.spring.service;

import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

import java.util.List;

public interface CommentService {
    Comment getById(Long id);

    List<Comment> getByBook(Book book);

    List<Comment> getAll();

    Comment insert(Comment comment);

    Comment update(Comment comment);

    void delete(Comment comment);
}
