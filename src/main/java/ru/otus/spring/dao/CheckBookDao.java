package ru.otus.spring.dao;

import ru.otus.spring.domain.CheckBook;

public interface CheckBookDao {
    CheckBook getCheckBook(String fileName);
}
