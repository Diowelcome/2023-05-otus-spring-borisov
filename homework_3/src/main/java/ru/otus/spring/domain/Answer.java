package ru.otus.spring.domain;

public class Answer {

    private final String answer;
    private final int rightFlag;

    public Answer(String answer, int rightFlag) {
        this.answer = answer;
        this.rightFlag = rightFlag;
    }

    public Answer(String answer) {
        this.answer = answer;
        this.rightFlag = 0;
    }

    public String getAnswer() {
        return answer;
    }

    public int getRightFlag() {
        return rightFlag;
    }
}
