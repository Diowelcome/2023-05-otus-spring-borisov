package ru.otus.spring.domain;

import java.time.LocalDateTime;
import java.util.List;

import static java.lang.Math.round;

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

    public String getBriefResult() {
        int maxScore = 5;
        Boolean testPassed = percentScore >= 50;
        Boolean goodResult = percentScore >= 70;
        String greetingString = getGreetingString(person, testPassed, goodResult);
        String scoreString = getScoreString(percentScore, maxScore);
        String passedString = getPassedString(testPassed);
        return greetingString + '\n' + scoreString + '\n' + passedString;
    }

    private String getScoreString(int percentScore, int maxScore) {
        int score = round((float) maxScore * percentScore / 100);
        String scoreString = String.format("Your score: %d ( %d%% )", score, percentScore);
        return scoreString;
    }

    private String getGreetingString(Person person, Boolean testPassed, Boolean goodResult) {
        String greetingFormat = new String();
        if (goodResult) {
            greetingFormat = "Congratulations, %s!";
        } else if (!testPassed) {
            greetingFormat = "Sorry, %s,";
        } else {
            greetingFormat = "%s,";
        }
        String greetingString = String.format(greetingFormat, person.getFirstName());
        return greetingString;
    }

    private String getPassedString(Boolean testPassed) {
        String passedString = new String();
        if (testPassed) {
            passedString = "Test is passed.";
        } else {
            passedString = "Test is not passed.";
        }
        return passedString;
    }


}
