package ru.otus.spring.service;

import ru.otus.spring.domain.CheckBook;

public interface CheckBookService {
    CheckBook getCheckBook(String fileName);
}
