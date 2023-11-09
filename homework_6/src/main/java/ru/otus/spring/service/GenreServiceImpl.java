package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository, IOService ioService) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Genre getById(Long id) {
        Optional<Genre> genre = genreRepository.getById(id);
        return genre.orElse(null);
    }

    @Override
    public List<Genre> getAll() {
        List<Genre> genres = genreRepository.getAll();
        return genres;
    }

    @Override
    @Transactional
    public Genre update(Genre genre) {
        return genreRepository.update(genre);
    }

    @Override
    @Transactional
    public Genre getGenreByNameInsertNew(String genreName) {
        Genre genre = genreRepository.getByName(genreName);
        if (genre == null) {
            genre = genreRepository.insertByNameWithoutCheck(genreName);
        }
        return genre;
    }
}
