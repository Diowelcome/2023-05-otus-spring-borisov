package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.QuestionsDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Person;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.TestRun;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DisplayName("Класс QuestionsServiceImpl")
@ExtendWith(MockitoExtension.class)
class QuestionsServiceImplTest {

    private static final List<Question> testQuestions = new ArrayList<>();
    private static final Person testPerson = new Person("Johnny", "Depp");
    private static final int rightAnswerIndex = 1;

    @Mock
    private IOService ioService;

    @Mock
    private PersonService personService;

    @Mock
    private QuestionsDao questionsDao;

    @InjectMocks
    private QuestionsServiceImpl questionsService;

    @BeforeEach
    void setUp() {
        int rightAnswerIndex = 1;
        Answer rightAnswer = new Answer("rightAnswer",1);
        Answer wrongAnswer = new Answer("WrongAnswer",0);
        Question testQuestion = new Question("TestQuestion",List.of(rightAnswer, wrongAnswer));
        testQuestions.add(testQuestion);
    }

    @DisplayName("метод getQuestions должен корректно возвращать список вопросов")
    @Test
    void shouldReturnCorrectQuestions() {
        when(questionsDao.getQuestions()).thenReturn(testQuestions);
        List<Question> questions = questionsService.getQuestions();
        assertAll(
                "questions",
                () -> assertEquals(questions.size(), 1, "should be 1 question"),
                () -> assertEquals(questions.get(0).getAnswer(0).getRightFlag(), 1, "first question should be right"),
                () -> assertEquals(questions.get(0).getAnswer(1).getRightFlag(), 0, "first question should be wrong")
        );
    }

    @DisplayName("метод runQuestions должен корректно рассчитывать результат теста")
    @Test
    void shouldReturnCorrectResult() {
        when(questionsDao.getQuestions()).thenReturn(testQuestions);
        when(personService.inputPerson()).thenReturn(testPerson);
        when(ioService.readStringWithPrompt(anyString())).thenReturn(Integer.toString(rightAnswerIndex));
        TestRun testRun = questionsService.runQuestions();
        assertAll(
                "testRun",
                () -> assertEquals(testRun.getPerson().getFirstName(), testPerson.getFirstName(), "first name should match test first name"),
                () -> assertEquals(testRun.getPerson().getLastName(), testPerson.getLastName(), "last name should match test first name"),
                () -> assertEquals(testRun.getPercentScore(), 100, "percent score should be 100%")
        );
    }
}