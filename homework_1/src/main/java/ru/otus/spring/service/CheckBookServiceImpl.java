package ru.otus.spring.service;

import ru.otus.spring.dao.CheckBookDao;
import ru.otus.spring.domain.CheckBook;


public class CheckBookServiceImpl implements CheckBookService {

    private final CheckBookDao dao;

    public CheckBook getCheckBook(String fileName) {
        return dao.getCheckBook(fileName);
    }

    public CheckBookServiceImpl(CheckBookDao dao) {
        this.dao = dao;
    }

}
