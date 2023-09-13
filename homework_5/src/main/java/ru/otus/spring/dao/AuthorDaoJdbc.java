package ru.otus.spring.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.mapper.AuthorMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Author getById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        List<Author> authorList = jdbc.query("select id, name from author where id = :id", params, new AuthorMapper());
        return authorList.isEmpty() ? null : authorList.get(0);
    }

    @Override
    public Author getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        List<Author> authorList = jdbc.query("select id, name from author where upper(name) = upper(:name)", params, new AuthorMapper());
        return authorList.isEmpty() ? null : authorList.get(0);
    }

    @Override
    public Author getByNameInsertNew(String name) {
        Author author = getByName(name);
        if (author == null) {
            insertWithoutCheck(name);
            author = getByName(name);
        }
        return author;
    }

    @Override
    public List<Author> getAll() {
        List<Author> authorList = jdbc.query("select id, name from author", new AuthorMapper());
        return authorList;
    }

    @Override
    public void update(Author author) {
        jdbc.update("update author set name = :name where id = :id",
                Map.of("name", author.getName(), "id", author.getId()));
    }

    private void insertWithoutCheck(String name) {
        jdbc.update("insert into author (name) values (:name)",
                Map.of("name", name));
    }

}
