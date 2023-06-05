package ru.otus.junit.service;

import ru.otus.junit.domain.CheckBook;

public interface CheckBookService {
    CheckBook getCheckBook(String fileName);
}
