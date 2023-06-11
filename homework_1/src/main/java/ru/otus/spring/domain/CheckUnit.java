package ru.otus.spring.domain;

import java.util.List;

public class CheckUnit {

    private final String question;
    private final List<Answer> answers;

    public CheckUnit(String question, List<Answer> answers) {
        this.question = question;
        this.answers = answers;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    @Override
    public String toString() {
        String answerString = new String();
        for(Answer answer : this.getAnswers()) {
            answerString += "- " + answer.getAnswer() + '\n';
        }
        return this.question + '\n' + answerString;
    }
}
