package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Genre;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;
    private final IOService ioService;

    public GenreServiceImpl(GenreDao genreDao, IOService ioService) {
        this.genreDao = genreDao;
        this.ioService = ioService;
    }

    @Override
    public Genre getById(Long id) {
        Genre genre = genreDao.getById(id);
        return genre;
    }

    @Override
    public List<Genre> getAll() {
        List<Genre> genres = genreDao.getAll();
        return genres;
    }
    @Override
    public void showAll() {
        getAll().forEach(this::showGenre);
    }

    @Override
    public Genre update() {
        Long id = Long.valueOf(ioService.readStringWithPrompt("Enter author id:"));
        Genre genre = genreDao.getById(id);
        if (genre != null) {
            showGenre(genre);
            String newGenreName = getValue("Enter new author name:", genre.getName());
            genreDao.update(new Genre(genre.getId(), newGenreName));
            genre = genreDao.getByName(newGenreName);
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
