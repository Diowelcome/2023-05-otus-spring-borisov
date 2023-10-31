package ru.otus.spring.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepositoryJpa implements GenreRepository {
    @PersistenceContext
    private final EntityManager em;

    public GenreRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Genre> getById(Long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public Genre getByName(String name) {
        TypedQuery<Genre> query = em.createQuery("select a from Genre a where upper(a.name) = upper(:name)",
                Genre.class);
        query.setParameter("name", name);
        List<Genre> genreList = query.getResultList();
        return genreList.isEmpty() ? null : genreList.get(0);
    }

    @Override
    public Genre getByNameInsertNew(String name) {
        Genre genre = getByName(name);
        if (genre == null) {
            genre = new Genre(0, name);
            em.persist(genre);
        }
        return genre;
    }

    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g",
                Genre.class);
        return query.getResultList();
    }

    @Override
    public Genre update(Genre genre) {
        em.merge(genre);
        return genre;
    }
}
