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
    @Transactional(readOnly = true)
    public void showComments() {
        String inputId = ioService.readStringWithPrompt("Enter book id for exact book comments or null for all comments:");
        Book book = getBookByInputId(inputId);
        if (book != null) {
            getByBookId(book.getId()).forEach(this::showComment);
        } else {
            getAll().forEach(this::showComment);
        }
    }

    @Override
    @Transactional
    public Comment insert() {
        Comment comment = null;
        Long bookId = Long.valueOf(ioService.readStringWithPrompt("Enter book id:"));
        Book book = bookRepository.getById(bookId).orElse(null);
        if (book != null) {
            showBook(book);
            String nickName = ioService.readStringWithPrompt("Enter nickname:");
            String commentText = ioService.readStringWithPrompt("Enter comment:");
            comment = new Comment(0,nickName, commentText, book);
            comment = commentRepository.insert(comment);
        } else {
            ioService.outputString("Such book does not exist");
        }
        return comment;
    }

    @Override
    @Transactional
    public Comment update() {
        Comment updatedComment = null;
        Long id = Long.valueOf(ioService.readStringWithPrompt("Enter comment id:"));
        Comment comment = commentRepository.getById(id).orElse(null);
        if (comment != null) {
            showComment(comment);
            String newCommentText = getValue("Enter new comment or none for the current value:", comment.getText());
            updatedComment = new Comment(comment.getId(), comment.getNickname(), newCommentText, comment.getBook());
            updatedComment = commentRepository.update(updatedComment);
        } else {
            ioService.outputString("Such comment does not exist");
        }
        return updatedComment;
    }

    @Override
    @Transactional
    public void deleteById() {
        Long id = Long.valueOf(ioService.readStringWithPrompt("Enter comment id:"));
        Comment comment = commentRepository.getById(id).orElse(null);
        if (comment != null) {
            showComment(comment);
            if (ioService.readStringWithPrompt("Are you sure you want to delete this comment (Y/N)?").equals("Y"))
                commentRepository.deleteById(id);
        } else {
            ioService.outputString("Such comment does not exist");
        }
    }

    private void showComment(Comment comment) {
        ioService.outputString(comment.toString());
    }

    private void showBook(Book book) {
        ioService.outputString(book.toString());
    }

    private String getValue(String prompt, String defaultValue) {
        String result = ioService.readStringWithPrompt(prompt);
        return result.trim().length() > 0 ? result : defaultValue;
    }

    private Book getBookByInputId(String inputId) {
        Book book = null;
        if (inputId.trim().length() > 0) {
            try {
                Long bookId = Long.valueOf(inputId);
                book = bookRepository.getById(bookId).orElse(null);
            }
            catch (NumberFormatException e) {
                ioService.outputString(e.getMessage());
            }
        }
        return book;
    }
}
