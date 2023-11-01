package ru.otus.spring.ui;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.*;


@ShellComponent
public class ShellUi {

    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;
    private final BookService bookService;
    private final IOService ioService;

    public ShellUi(AuthorService authorService, GenreService genreService, CommentService commentService, IOService ioService, BookService bookService) {
        this.authorService = authorService;
        this.genreService = genreService;
        this.commentService = commentService;
        this.bookService = bookService;
        this.ioService = ioService;
    }

    @Transactional(readOnly = true)
    @ShellMethod(value = "List table content (l table_name or simply l - for table list)", key = {"l", "list"})
    public void list(@ShellOption(defaultValue = "null") String tableName) {
        if (tableName.equals("author")) {
            authorService.getAll().forEach(this::showAuthor);
        } else if (tableName.equals("genre")) {
            genreService.getAll().forEach(this::showGenre);
        } else if (tableName.equals("book")) {
            bookService.getAll().forEach(this::showBook);
        } else if (tableName.equals("comment")) {
            showComments();
        } else
            outputHelpString("l", new String[]{"author", "genre", "book", "comment"});
    }

    @ShellMethod(value = "Insert (insert table_name or simply i - for table list)", key = {"i", "insert"})
    public void insert(@ShellOption(defaultValue = "null") String tableName) {
        if (tableName.equals("book")) {
            insertBook();
        } else if (tableName.equals("comment")) {
            insertComment();
        } else
            outputHelpString("i", new String[]{"book", "comment"});
    }

    @ShellMethod(value = "Update (update table_name or simply u - for table list)", key = {"u", "update"})
    public void update(@ShellOption(defaultValue = "null") String tableName) {
        if (tableName.equals("author")) {
            updateAuthor();
        } else if (tableName.equals("genre")) {
            updateGenre();
        } else if (tableName.equals("comment")) {
            updateComment();
        } else if (tableName.equals("book")) {
            updateBook();
        } else
            outputHelpString("u", new String[]{"author", "genre", "book", "comment"});
    }

    @ShellMethod(value = "Delete (delete table_name or simply d - for table list)", key = {"d", "delete"})
    public void delete(@ShellOption(defaultValue = "null") String tableName) {
        if (tableName.equals("book")) {
            deleteBook();
        } else if (tableName.equals("comment")) {
            deleteComment();
        } else
            outputHelpString("d", new String[]{"book", "comment"});
    }

    private void updateAuthor() {
        Long id = Long.valueOf(ioService.readStringWithPrompt("Enter author id:"));
        Author author = authorService.getById(id);
        if (author != null) {
            ioService.outputString(author.toString());
            String newAuthorName = getValue("Enter new author name:", author.getName());
            author = authorService.update(new Author(author.getId(), newAuthorName));
            ioService.outputString(author.toString());
        } else {
            ioService.outputString("Such author does not exist");
        }
    }

    private void updateGenre() {
        Long id = Long.valueOf(ioService.readStringWithPrompt("Enter genre id:"));
        Genre genre = genreService.getById(id);
        if (genre != null) {
            ioService.outputString(genre.toString());
            String newGenreName = getValue("Enter new genre name:", genre.getName());
            genre = genreService.update(new Genre(genre.getId(), newGenreName));
            ioService.outputString(genre.toString());
        } else {
            ioService.outputString("Such genre does not exist");
        }
    }

    private void insertComment() {
        Comment comment = null;
        Long bookId = Long.valueOf(ioService.readStringWithPrompt("Enter book id:"));
        Book book = bookService.getById(bookId);
        if (book != null) {
            ioService.outputString(book.toString());
            String nickName = ioService.readStringWithPrompt("Enter nickname:");
            String commentText = ioService.readStringWithPrompt("Enter comment:");
            comment = new Comment(0, nickName, commentText, book);
            comment = commentService.insert(comment);
            ioService.outputString(comment.toString());
        } else {
            ioService.outputString("Such book does not exist");
        }
    }

    private void updateComment() {
        Long id = Long.valueOf(ioService.readStringWithPrompt("Enter comment id:"));
        Comment comment = commentService.getById(id);
        if (comment != null) {
            ioService.outputString(comment.toString());
            String newCommentText = getValue("Enter new comment or none for the current value:", comment.getText());
            Comment updatedComment = new Comment(comment.getId(), comment.getNickname(), newCommentText, comment.getBook());
            updatedComment = commentService.update(updatedComment);
            ioService.outputString(updatedComment.toString());
        } else {
            ioService.outputString("Such comment does not exist");
        }

    }

    private void deleteComment() {
        Long id = Long.valueOf(ioService.readStringWithPrompt("Enter comment id:"));
        Comment comment = commentService.getById(id);
        if (comment != null) {
            showComment(comment);
            if (ioService.readStringWithPrompt("Are you sure you want to delete this comment (Y/N)?").equals("Y"))
                commentService.delete(comment);
        } else {
            ioService.outputString("Such comment does not exist");
        }
    }

    private void insertBook() {
        String title = ioService.readStringWithPrompt("Enter book title:");
        Book book = bookService.getByTitle(title);
        if (book == null) {
            String authorName = ioService.readStringWithPrompt("Enter author name:");
            String genreName = ioService.readStringWithPrompt("Enter genre name:");
            Author author = authorService.getAuthorByNameInsertNew(authorName);
            Genre genre = genreService.getGenreByNameInsertNew(genreName);
            book = new Book(0, title, author, genre);
            book = bookService.insert(book);
            ioService.outputString(book.toString());
        } else {
            ioService.outputString("Such book is already exists");
            ioService.outputString(book.toString());
        }
    }

    private void updateBook() {
        Long id = Long.valueOf(ioService.readStringWithPrompt("Enter book id:"));
        Book book = bookService.getById(id);
        if (book != null) {
            ioService.outputString(book.toString());
            String title = getValue("Enter new book title or none for the current value:", book.getTitle());
            String authorName = getValue("Enter new author name or none for the current value:", book.getAuthor().getName());
            String genreName = getValue("Enter new genre name or none for the current value:", book.getGenre().getName());
            Author author = authorService.getAuthorByNameInsertNew(authorName);
            Genre genre = genreService.getGenreByNameInsertNew(genreName);
            Book updatedBook = new Book(id, title, author, genre);

            book = bookService.update(updatedBook);
            ioService.outputString(book.toString());
        } else {
            ioService.outputString("Such book does not exist");
        }
    }

    private void deleteBook() {
        Long id = Long.valueOf(ioService.readStringWithPrompt("Enter book id:"));
        Book book = bookService.getById(id);
        if (book != null) {
            showBook(book);
            if (ioService.readStringWithPrompt("Are you sure you want to delete this book (Y/N)?").equals("Y"))
                bookService.delete(book);
        } else {
            ioService.outputString("Such book does not exist");
        }
    }

    private void outputHelpString(String operation, String[] tableList) {
        String helpString = String.format("type %s table_name \nvalid table names: ", operation);
        for (String table : tableList) {
            helpString += table + ", ";
        }
        ioService.outputString(helpString.substring(0, helpString.length() - 2));
    }

    private String getValue(String prompt, String defaultValue) {
        String result = ioService.readStringWithPrompt(prompt);
        return result.trim().length() > 0 ? result : defaultValue;
    }

    public void showComments() {
        String inputId = ioService.readStringWithPrompt("Enter book id for exact book comments or null for all comments:");
        Book book = getBookByInputId(inputId);
        if (book != null) {
            commentService.getByBookId(book.getId()).forEach(this::showComment);
        } else {
            commentService.getAll().forEach(this::showComment);
        }
    }

    private Book getBookByInputId(String inputId) {
        Book book = null;
        if (inputId.trim().length() > 0) {
            try {
                Long bookId = Long.valueOf(inputId);
                book = bookService.getById(bookId);
            } catch (NumberFormatException e) {
                ioService.outputString(e.getMessage());
            }
        }
        return book;
    }

    private void showAuthor(Author author) {
        ioService.outputString(author.toString());
    }

    private void showBook(Book book) {
        ioService.outputString(book.toString());
    }

    private void showComment(Comment comment) {
        ioService.outputString(comment.toString());
    }

    private void showGenre(Genre genre) {
        ioService.outputString(genre.toString());
    }
}
