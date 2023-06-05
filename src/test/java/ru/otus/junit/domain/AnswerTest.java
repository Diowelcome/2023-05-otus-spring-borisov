package ru.otus.junit.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Answer")
class AnswerTest {

    @DisplayName("корректное создание конструктором")
    @Test
    void shouldHaveCorrectConstructor() {

        String testAnswer = "OTUS";
        int testWeight = 12;

        Answer answer = new Answer(testAnswer, testWeight);
        assertEquals(testAnswer, answer.getAnswer());
        assertEquals(testWeight, answer.getWeight());
    }


}