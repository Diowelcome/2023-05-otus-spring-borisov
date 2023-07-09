package ru.otus.spring.service;

import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.TestRun;

import java.util.List;

public interface QuestionsService {
    List<Question> getQuestions();
    TestRun runQuestions();
    void showQuestions();
    void showTestRunResults();

}
