package ru.otus.spring.domain;

public class Answer {

    private final String answer;
    private final int weight;

    public Answer(String answer, int weight) {
        this.answer = answer;
        this.weight = weight;
    }

    public Answer(String answer) {
        this.answer = answer;
        this.weight = 0;
    }

    public String getAnswer() {
        return answer;
    }

    public int getWeight() {
        return weight;
    }
}
