package ru.otus.spring.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.domain.Question;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс ParserCsv")
class ParserCsvTest {
    private List<String> lines = new ArrayList<>();
    private String delimiter = ";";
    private String answerDelimiter = ":";
    private String question = "Question";

    @BeforeEach
    void setUp() {
        String answerWithWrongFlag = "Answer with wrong flag:0";
        String answerWithRightFlag = "Answer with right flag:1";
        String answerWithIncorrectFlag = "Answer with wrong flag value:2";
        String answerWithoutFlag = "Answer without flag";

        String line = question + delimiter +
                answerWithWrongFlag + delimiter +
                answerWithRightFlag + delimiter +
                answerWithIncorrectFlag + delimiter +
                answerWithoutFlag;

        lines.add(line);
    }

    @DisplayName("должен возвращать корректный объект")
    @Test
    void shouldReturnRightObject() {
        List<Question> questions = new ParserCsv().parse(lines, delimiter, answerDelimiter);
        assertAll(
                "questions",
                () -> assertEquals(questions.size(), 1, "should be 1 question"),
                () -> assertEquals(questions.get(0).getQuestion(), question, "question should match test question"),
                () -> assertEquals(questions.get(0).getAnswers().size(), 4, "should be 4 answers"),
                () -> assertEquals(questions.get(0).getAnswer(0).getRightFlag(), 0, "first question should be wrong"),
                () -> assertEquals(questions.get(0).getAnswer(1).getRightFlag(), 1, "second question should be right"),
                () -> assertEquals(questions.get(0).getAnswer(2).getRightFlag(), 0, "third question should be wrong"),
                () -> assertEquals(questions.get(0).getAnswer(3).getRightFlag(), 0, "fourth question should be wrong")
        );
    }

}