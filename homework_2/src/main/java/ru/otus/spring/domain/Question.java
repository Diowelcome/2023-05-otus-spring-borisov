package ru.otus.spring.domain;

import java.util.List;

public class Question {

    private final String question;
    private final List<Answer> answers;

    public Question(String question, List<Answer> answers) {
        this.question = question;
        this.answers = answers;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public Answer getAnswer(int index) {
        return answers.get(index);
    }

    @Override
    public String toString() {
        String answerString = new String();
        for(Answer answer : this.getAnswers()) {
            answerString += answers.indexOf(answer) + 1 + ". " + answer.getAnswer() + '\n';
        }
        return this.question + '\n' + answerString;
    }
}
