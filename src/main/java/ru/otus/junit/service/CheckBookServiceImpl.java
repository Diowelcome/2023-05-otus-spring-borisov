package ru.otus.junit.service;

import ru.otus.junit.dao.CheckBookDao;
import ru.otus.junit.domain.CheckBook;


public class CheckBookServiceImpl implements CheckBookService {

    private final CheckBookDao dao;

    public CheckBook getCheckBook(String fileName) {
        return dao.getCheckBook(fileName);
    }

    public CheckBookServiceImpl(CheckBookDao dao) {
        this.dao = dao;
    }

}
