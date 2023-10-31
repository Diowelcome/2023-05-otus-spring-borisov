package ru.otus.spring.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJpa implements BookRepository {
    @PersistenceContext
    private final EntityManager em;

    public BookRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Book> getById(Long id) {
        return Optional.ofNullable(em.find(Book.class, id));
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
        TypedQuery<Book> query = em.createQuery("select distinct b from Book b left join fetch b.author left join fetch b.genre", Book.class);
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
    public void delete(Long id) {
        deleteCommentCascade(id);
        Query query = em.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    private void deleteCommentCascade(Long id) {
        Query query = em.createQuery("delete from Comment c where c.book.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
