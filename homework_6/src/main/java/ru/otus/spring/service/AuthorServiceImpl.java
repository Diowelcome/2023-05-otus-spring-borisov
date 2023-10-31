package ru.otus.spring.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final IOService ioService;

    public AuthorServiceImpl(AuthorRepository authorRepository, IOService ioService) {
        this.authorRepository = authorRepository;
        this.ioService = ioService;
    }

    @Override
    @Transactional(readOnly = true)
    public Author getById(Long id) {
        Optional<Author> author = authorRepository.getById(id);
        return author.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Author getByName(String name) {
        Author author = authorRepository.getByName(name);
        return author;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> getAll() {
        List<Author> authors = authorRepository.getAll();
        return authors;
    }

    @Override
    public void showAll() {
        getAll().forEach(this::showAuthor);
    }

    @Override
    @Transactional
    public Author update() {
        Long id = Long.valueOf(ioService.readStringWithPrompt("Enter author id:"));
        Author author = authorRepository.getById(id).orElse(null);
        if (author != null) {
            showAuthor(author);
            String newAuthorName = getValue("Enter new author name:", author.getName());
            author = authorRepository.update(new Author(author.getId(), newAuthorName));
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
