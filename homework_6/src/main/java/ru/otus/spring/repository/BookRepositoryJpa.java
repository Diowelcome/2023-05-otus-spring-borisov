package ru.otus.spring.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

@Component
public class BookRepositoryJpa implements BookRepository {
    @PersistenceContext
    private final EntityManager em;

    public BookRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Book> getById(Long id) {
        Book book = em.find(Book.class, id);
        return Optional.ofNullable(book);
    }

    @Override
    public Book getByTitle(String title) {
        TypedQuery<Book> query = em.createQuery("select b from Book b where upper(b.title) = upper(:title)",
                Book.class);
        query.setParameter("title", title);
        List<Book> bookList = query.getResultList();
        return bookList.isEmpty() ? null : bookList.get(0);
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        return query.getResultList();
    }

    @Override
    public Book insert(Book book) {
        em.persist(book);
        return book;
    }

    @Override
    public Book update(Book book) {
        em.merge(book);
        return book;
    }

    @Override
    public void delete(Book book) {
        book = getById(book.getId()).orElse(null);
        if (book != null) {
            em.remove(book);
        }
    }

}
