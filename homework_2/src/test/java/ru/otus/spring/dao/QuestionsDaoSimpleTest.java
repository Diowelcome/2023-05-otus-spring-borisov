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
        assertEquals(answer.getRightFlag(), 1);
    }

    @DisplayName("разбор ответа: неправильный ответ")
    @Test
    void shouldReturnWrongFlag() {
        String answerWithWrongFlag = "Answer with wrong flag:0";
        Answer answer = QuestionsDaoSimple.parseAnswer(answerWithWrongFlag, ':');
        assertEquals(answer.getRightFlag(), 0);
    }

    @DisplayName("разбор ответа: отсутствие признака правильного ответа")
    @Test
    void shouldReturnDefaultWrongFlag() {
        String answerWithoutFlag = "Answer without flag";
        Answer answer = QuestionsDaoSimple.parseAnswer(answerWithoutFlag, ':');
        assertEquals(answer.getRightFlag(), 0);
    }

}