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
    private final IOService ioService;

    public GenreServiceImpl(GenreRepository genreRepository, IOService ioService) {
        this.genreRepository = genreRepository;
        this.ioService = ioService;
    }

    @Override
    @Transactional(readOnly = true)
    public Genre getById(Long id) {
        Optional<Genre> genre = genreRepository.getById(id);
        return genre.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> getAll() {
        List<Genre> genres = genreRepository.getAll();
        return genres;
    }
    @Override
    public void showAll() {
        getAll().forEach(this::showGenre);
    }

    @Override
    @Transactional
    public Genre update() {
        Long id = Long.valueOf(ioService.readStringWithPrompt("Enter author id:"));
        Genre genre = genreRepository.getById(id).orElse(null);
        if (genre != null) {
            showGenre(genre);
            String newGenreName = getValue("Enter new author name:", genre.getName());
            genre = genreRepository.update(new Genre(genre.getId(), newGenreName));
            showGenre(genre);
        } else {
            ioService.outputString("Such author does not exist");
        }
        return genre;
    }

    private void showGenre(Genre genre) {
        ioService.outputString(genre.toString());
    }

    private String getValue(String prompt, String defaultValue) {
        String result = ioService.readStringWithPrompt(prompt);
        return result.trim().length() > 0 ? result : defaultValue;
    }
}
