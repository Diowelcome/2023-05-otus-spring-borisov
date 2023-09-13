package ru.otus.spring.ui;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.GenreService;
import ru.otus.spring.service.IOService;


@ShellComponent
public class ShellUi {

    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;
    private final IOService ioService;

    public ShellUi(AuthorService authorService, GenreService genreService, IOService ioService, BookService bookService) {
        this.authorService = authorService;
        this.genreService = genreService;
        this.bookService = bookService;
        this.ioService = ioService;
    }

    @ShellMethod(value = "List table content (l table_name or simply l - for table list)", key = {"l", "list"})
    public void list(@ShellOption(defaultValue = "null") String tableName) {
        if (tableName.equals("author")) {
            authorService.showAll();
        } else if (tableName.equals("genre")) {
            genreService.showAll();
        } else if (tableName.equals("book")) {
            bookService.showAll();
        } else
            outputHelpString("l", new String[]{"author", "genre", "book"});
    }

    @ShellMethod(value = "Insert (insert table_name or simply i - for table list)", key = {"i", "insert"})
    public void insert(@ShellOption(defaultValue = "null") String tableName) {
        if (tableName.equals("book")) {
            bookService.insert();
        } else
            outputHelpString("i", new String[]{"book"});
    }

    @ShellMethod(value = "Update (update table_name or simply u - for table list)", key = {"u", "update"})
    public void update(@ShellOption(defaultValue = "null") String tableName) {
        if (tableName.equals("author")) {
            authorService.update();
        } else if (tableName.equals("genre")) {
            genreService.update();
        } else if (tableName.equals("book")) {
            bookService.update();
        } else
            outputHelpString("u", new String[]{"author", "genre", "book"});
    }

    @ShellMethod(value = "Delete (delete table_name or simply d - for table list)", key = {"d", "delete"})
    public void delete(@ShellOption(defaultValue = "null") String tableName) {
        if (tableName.equals("book")) {
            bookService.delete();
        } else
            outputHelpString("d", new String[]{"book"});
    }

    public void outputHelpString(String operation, String[] tableList) {
        String helpString = String.format("type %s table_name \nvalid table names: ", operation);
        for (String table : tableList) {
            helpString += table + ", ";
        }
        ioService.outputString(helpString.substring(0, helpString.length() - 2));
    }

}
