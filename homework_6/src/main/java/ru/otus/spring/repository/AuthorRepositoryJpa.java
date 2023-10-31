package ru.otus.spring.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryJpa implements AuthorRepository {
    @PersistenceContext
    private final EntityManager em;

    public AuthorRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Author> getById(Long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public Author getByName(String name) {
        TypedQuery<Author> query = em.createQuery("select a from Author a where upper(a.name) = upper(:name)",
                Author.class);
        query.setParameter("name", name);
        List<Author> authorList = query.getResultList();
        return authorList.isEmpty() ? null : authorList.get(0);
    }

    @Override
    public Author getByNameInsertNew(String name) {
        Author author = getByName(name);
        if (author == null) {
            author = new Author(0, name);
            em.persist(author);
        }
        return author;
    }

    @Override
    public List<Author> getAll() {
        TypedQuery<Author> query = em.createQuery("select a from Author a",
                Author.class);
        return query.getResultList();
    }

    @Override
    public Author update(Author author) {
        em.merge(author);
        return author;
    }
}
