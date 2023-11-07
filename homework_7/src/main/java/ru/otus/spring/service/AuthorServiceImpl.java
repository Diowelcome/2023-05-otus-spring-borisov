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
    public Author getById(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.orElse(null);
    }

    @Override
    public Author getByName(String name) {
        Author author = authorRepository.findByName(name);
        return author;
    }

    @Override
    public List<Author> getAll() {
        List<Author> authors = authorRepository.findAll();
        return authors;
    }

    @Override
    @Transactional
    public Author update(Author author) {
        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public Author getAuthorByNameInsertNew(String authorName) {
        Author author = authorRepository.findByName(authorName);
        if (author == null) {
            author = authorRepository.save(new Author(0, authorName));
        }
        return author;
    }

}
