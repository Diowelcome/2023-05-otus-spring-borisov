package ru.otus.spring.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.mapper.GenreMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Genre getById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        List<Genre> authorList = jdbc.query("select id, name from genre where id = :id", params, new GenreMapper());
        return authorList.isEmpty() ? null : authorList.get(0);
    }

    @Override
    public Genre getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        List<Genre> genreList = jdbc.query("select id, name from genre where upper(name) = upper(:name)", params, new GenreMapper());
        return genreList.isEmpty() ? null : genreList.get(0);
    }

    @Override
    public Genre getByNameInsertNew(String name) {
        Genre genre = getByName(name);
        if (genre == null) {
            insertWithoutCheck(name);
            genre = getByName(name);
        }
        return genre;
    }

    @Override
    public List<Genre> getAll() {
        List<Genre> genreList = jdbc.query("select id, name from genre", new GenreMapper());
        return genreList;
    }

    @Override
    public void update(Genre genre) {
        jdbc.update("update genre set name = :name where id = :id",
                Map.of("name", genre.getName(), "id", genre.getId()));
    }

    private void insertWithoutCheck(String name) {
        Genre existingGenre = getByName(name);
        if (existingGenre == null) {
            jdbc.update("insert into genre (name) values (:name)",
                    Map.of("name", name));
        }
    }
}
