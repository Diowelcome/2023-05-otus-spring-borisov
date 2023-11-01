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

    public AuthorServiceImpl(AuthorRepository authorRepository, IOService ioService) {
        this.authorRepository = authorRepository;
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
    @Transactional
    public Author update(Author author) {
        return authorRepository.update(author);
    }

    @Override
    @Transactional
    public Author getAuthorByNameInsertNew(String authorName) {
        Author author = authorRepository.getByName(authorName);
        if (author == null) {
            author = authorRepository.insertByNameWithoutCheck(authorName);
        }
        return author;
    }

}
