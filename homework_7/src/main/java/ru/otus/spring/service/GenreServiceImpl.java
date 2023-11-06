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
        Optional<Genre> genre = genreRepository.findById(id);
        return genre.orElse(null);
    }

    @Override
    public List<Genre> getAll() {
        List<Genre> genres = genreRepository.findAll();
        return genres;
    }

    @Override
    @Transactional
    public Genre update(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    @Transactional
    public Genre getGenreByNameInsertNew(String genreName) {
        Genre genre = genreRepository.findByName(genreName);
        if (genre == null) {
            genre = genreRepository.save(new Genre(0, genreName));
        }
        return genre;
    }
}
