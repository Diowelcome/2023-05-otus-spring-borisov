package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionsDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Person;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.TestRun;
import ru.otus.spring.service.PersonService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.round;

@Service
public class QuestionsServiceImpl implements QuestionsService {

    private final QuestionsDao dao;
    private final IOService ioService;
    private final PersonService personService;

    public QuestionsServiceImpl(QuestionsDao dao, IOService ioService, PersonService personService) {
        this.dao = dao;
        this.ioService = ioService;
        this.personService = personService;
    }

    @Override
    public List<Question> getQuestions() {
        return dao.getQuestions();
    }

    @Override
    public void showQuestions() {
        getQuestions().forEach(this::showQuestion);
    }

    private void showQuestion(Question question) {
        ioService.outputString(question.toString());
    }

    @Override
    public TestRun runQuestions() {
        Person person = personService.inputPerson();
        List<Question> questions = getQuestions();
        List<String> ioAnswers = new ArrayList<>();
        int cumulativeScore = 0;
        LocalDateTime startTime = LocalDateTime.now();
        for (Question question : questions) {
            String ioAnswer = runQuestion(question);
            ioAnswers.add(ioAnswer);
            cumulativeScore += calculatePercentScore(question, ioAnswer);
        }
        int percentScore = round((float) cumulativeScore / questions.size());
        LocalDateTime endTime = LocalDateTime.now();
        return new TestRun(dao.getTestName(),person,questions,ioAnswers,startTime,endTime,percentScore);
    }

    @Override
    public void showTestRunResults() {
        ioService.outputString(runQuestions().getBriefResult());
    }

    private int calculatePercentScore(Question question, String ioAnswer) {
        int totalQuestionCount = 0;
        int rightQuestionCount = 0;
        for (Answer answer : question.getAnswers()) {
            rightQuestionCount += answer.getRightFlag();
            totalQuestionCount++;
        }
        float rightQuestionWeight = (float) 100 / rightQuestionCount;
        float wrongQuestionWeight = (float) 100 / (totalQuestionCount - rightQuestionCount);

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(ioAnswer);
        int answerIndex = 0;
        int ioRightCount = 0;
        int ioWrongCount = 0;
        while (matcher.find()) {
            answerIndex = Integer.parseInt(matcher.group()) - 1;
            if (totalQuestionCount > answerIndex) {
                if (question.getAnswer(answerIndex).getRightFlag() == 1) {
                    ioRightCount++;
                } else {
                    ioWrongCount++;
                }
            }
        }
        double rawScore = ioRightCount * rightQuestionWeight - ioWrongCount * wrongQuestionWeight;
        int score = (int) round(rawScore > 0 ? rawScore : 0);

        return score;
    }

    private String runQuestion(Question question) {
        String typeYourAnswerString = "Type right number or numbers, separated by commas (for example: 1 or 1,3):";
        return ioService.readStringWithPrompt(String.format("%s\n%s", question.toString(), typeYourAnswerString));
    }

}
