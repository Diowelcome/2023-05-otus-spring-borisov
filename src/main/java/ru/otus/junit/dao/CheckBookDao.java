package ru.otus.junit.dao;

import ru.otus.junit.domain.CheckBook;

public interface CheckBookDao {
    CheckBook getCheckBook(String fileName);
}
