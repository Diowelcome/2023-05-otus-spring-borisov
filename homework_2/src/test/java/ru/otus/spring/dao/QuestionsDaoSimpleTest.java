package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.domain.Answer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс QuestionsDaoSimple")
class QuestionsDaoSimpleTest {

    @DisplayName("разбор ответа: правильный ответ")
    @Test
    void shouldReturnRightFlag() {
        String answerWithRightFlag = "Answer with right flag:1";
        Answer answer = QuestionsDaoSimple.parseAnswer(answerWithRightFlag, ':');
        assertEquals(answer.getAnswer(), answerWithRightFlag.substring(0, answerWithRightFlag.indexOf(':')));
        assertEquals(answer.getRightFlag(), 1);
    }

    @DisplayName("разбор ответа: неправильный ответ")
    @Test
    void shouldReturnWrongFlag() {
        String answerWithWrongFlag = "Answer with wrong flag:0";
        Answer answer = QuestionsDaoSimple.parseAnswer(answerWithWrongFlag, ':');
        assertEquals(answer.getAnswer(), answerWithWrongFlag.substring(0, answerWithWrongFlag.indexOf(':')));
        assertEquals(answer.getRightFlag(), 0);
    }

    @DisplayName("разбор ответа: неверное задание признака правильного ответа")
    @Test
    void shouldReturnCorrectWrongFlag() {
        String answerWithIncorrectFlag = "Answer with wrong flag value:2";
        Answer answer = QuestionsDaoSimple.parseAnswer(answerWithIncorrectFlag, ':');
        assertEquals(answer.getAnswer(), answerWithIncorrectFlag.substring(0, answerWithIncorrectFlag.indexOf(':')));
        assertEquals(answer.getRightFlag(), 0);
    }

    @DisplayName("разбор ответа: отсутствие признака правильного ответа")
    @Test
    void shouldReturnDefaultWrongFlag() {
        String answerWithoutFlag = "Answer with false wrong flag";
        Answer answer = QuestionsDaoSimple.parseAnswer(answerWithoutFlag, ':');
        assertEquals(answer.getAnswer(), answerWithoutFlag);
        assertEquals(answer.getRightFlag(), 0);
    }

}