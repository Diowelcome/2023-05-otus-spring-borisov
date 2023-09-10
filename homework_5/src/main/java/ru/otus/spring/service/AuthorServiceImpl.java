package ru.otus.spring.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;

import java.util.List;

@Data
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;
    private final IOService ioService;

    public AuthorServiceImpl(AuthorDao authorDao, IOService ioService) {
        this.authorDao = authorDao;
        this.ioService = ioService;
    }

    @Override
    public Author getById(Long id) {
        Author author = authorDao.getById(id);
        return author;
    }

    @Override
    public Author getByName(String name) {
        Author author = authorDao.getByName(name);
        return author;
    }

    @Override
    public List<Author> getAll() {
        List<Author> authors = authorDao.getAll();
        return authors;
    }

    @Override
    public void showAll() {
        getAll().forEach(this::showAuthor);
    }

    @Override
    public Author update() {
        Long id = Long.valueOf(ioService.readStringWithPrompt("Enter author id:"));
        Author author = authorDao.getById(id);
        if (author != null) {
            showAuthor(author);
            String newAuthorName = getValue("Enter new author name:", author.getName());
            authorDao.update(new Author(author.getId(), newAuthorName));
            author = authorDao.getByName(newAuthorName);
            showAuthor(author);
        } else {
            ioService.outputString("Such author does not exist");
        }
        return author;
    }

    private void showAuthor(Author author) {
        ioService.outputString(author.toString());
    }

    private String getValue(String prompt, String defaultValue) {
        String result = ioService.readStringWithPrompt(prompt);
        return result.trim().length() > 0 ? result : defaultValue;
    }
}
