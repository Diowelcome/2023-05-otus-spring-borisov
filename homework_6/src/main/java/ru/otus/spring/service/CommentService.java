package ru.otus.spring.service;

import ru.otus.spring.domain.Comment;

import java.util.List;

public interface CommentService {
    Comment getById(Long id);

    List<Comment> getByBookId(Long bookId);

    List<Comment> getAll();

    void showComments();

    Comment insert();

    Comment update();

    void deleteById();
}
