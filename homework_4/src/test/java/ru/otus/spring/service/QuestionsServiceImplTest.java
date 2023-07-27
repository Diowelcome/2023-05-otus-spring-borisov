package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.dao.QuestionsDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Person;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.TestRun;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Класс QuestionsServiceImpl")
class QuestionsServiceImplTest {

    private List<Question> testQuestions = new ArrayList<>();
    private Person testPerson = new Person("Johnny", "Depp");
    private int rightAnswerIndex = 0;

    @MockBean
    private QuestionsDao questionsDao;

    @MockBean
    private IOService ioService;

    @MockBean
    private PersonService personService;

    @Autowired
    private QuestionsServiceImpl questionsService;

    @BeforeEach
    void setUp() {
        rightAnswerIndex = 2;
        Answer wrongAnswer = new Answer("WrongAnswer", 0);
        Answer rightAnswer = new Answer("rightAnswer", 1);
        Question testQuestion = new Question("TestQuestion", List.of(wrongAnswer, rightAnswer));
        testQuestions.add(testQuestion);
    }

    @DisplayName("метод getQuestions должен корректно возвращать список вопросов")
    @Test
    void shouldReturnCorrectQuestions() {
        given(questionsDao.getQuestions()).willReturn(testQuestions);
        List<Question> questions = questionsService.getQuestions();
        assertAll(
                "questions",
                () -> assertEquals(questions.size(), 1, "should be 1 question"),
                () -> assertEquals(questions.get(0).getAnswer(0).getRightFlag(), 0, "first question should be wrong"),
                () -> assertEquals(questions.get(0).getAnswer(1).getRightFlag(), 1, "second question should be right")
        );
    }

    @DisplayName("метод runQuestions должен корректно рассчитывать результат теста")
    @Test
    void shouldReturnCorrectResult() {
        given(questionsDao.getQuestions()).willReturn(testQuestions);
        given(personService.inputPerson()).willReturn(testPerson);
        given(ioService.readStringWithPrompt(anyString())).willReturn(Integer.toString(rightAnswerIndex));
        TestRun testRun = questionsService.runQuestions();
        assertAll(
                "testRun",
                () -> assertEquals(testRun.getPerson().getFirstName(), testPerson.getFirstName(), "first name should match test first name"),
                () -> assertEquals(testRun.getPerson().getLastName(), testPerson.getLastName(), "last name should match test last name"),
                () -> assertEquals(testRun.getPercentScore(), 100, "percent score should be 100%")
        );
    }

}