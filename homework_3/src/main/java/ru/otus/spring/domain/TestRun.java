package ru.otus.spring.domain;

import java.time.LocalDateTime;
import java.util.List;

public class TestRun {
    private final String title;
    private final Person person;
    private final List<Question> questions;
    private final List<String> answers;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final int percentScore;

    public TestRun(String title, Person person, List<Question> questions, List<String> answers, LocalDateTime startTime, LocalDateTime endTime, int percentScore) {
        this.title = title;
        this.person = person;
        this.questions = questions;
        this.answers = answers;
        this.startTime = startTime;
        this.endTime = endTime;
        this.percentScore = percentScore;
    }

    public Person getPerson() {
        return person;
    }

    public int getPercentScore() {
        return percentScore;
    }

}
