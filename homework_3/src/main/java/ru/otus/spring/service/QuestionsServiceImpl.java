package ru.otus.spring.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.ApplicationProperties;
import ru.otus.spring.dao.QuestionsDao;
import ru.otus.spring.domain.Person;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.TestRun;

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
    private final ApplicationProperties properties;
    private final MessageSource messageSource;

    public QuestionsServiceImpl(QuestionsDao dao, IOService ioService, PersonService personService, ApplicationProperties properties, MessageSource messageSource) {
        this.dao = dao;
        this.ioService = ioService;
        this.personService = personService;
        this.properties = properties;
        this.messageSource = messageSource;
    }

    @Override
    public List<Question> getQuestions() {
        return dao.getQuestions();
    }

    @Override
    public void showQuestions() {
        getQuestions().forEach(this::showQuestion);
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
        return new TestRun(dao.getTestName(), person, questions, ioAnswers, startTime, endTime, percentScore);
    }

    @Override
    public void showTestRunResults() {
        TestRun testRun = runQuestions();
        ioService.outputString(getBriefResult(testRun.getPerson(), testRun.getPercentScore()));
    }


    private void showQuestion(Question question) {
        ioService.outputString(question.toString());
    }

    private int calculatePercentScore(Question question, String ioAnswer) {
        int rightQuestionCount = question.getRightAnswerCount();
        int totalQuestionCount = question.getTotalAnswerCount();
        float rightQuestionWeight = (float) 100 / rightQuestionCount;
        float wrongQuestionWeight = (float) 100 / (totalQuestionCount - rightQuestionCount);

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(ioAnswer);
        int answerIndex = 0;
        int ioRightCount = 0;
        int ioWrongCount = 0;
        while (matcher.find()) {
            answerIndex = Integer.parseInt(matcher.group()) - 1;
            if (answerIndex >= 0 && answerIndex < totalQuestionCount) {
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
        String typeDefaultString = "Type right number or numbers, separated by commas (for example: 1 or 1,3):";
        String typeLocaleString = messageSource.getMessage("type.answer", new String[]{""}, typeDefaultString, properties.getLocale());
        return ioService.readStringWithPrompt(String.format("%s\n%s", question.toString(), typeLocaleString));
    }

    private String getBriefResult(Person person, int percentScore) {
        Boolean testPassed = percentScore >= properties.getMinPassedPercent();
        Boolean goodResult = percentScore >= properties.getMinGoodPercent();
        String greetingString = getGreetingString(person, testPassed, goodResult);
        String scoreString = getScoreString(percentScore, properties.getMaxScore());
        String passedString = getPassedString(testPassed);
        return greetingString + '\n' + scoreString + '\n' + passedString;
    }

    private String getScoreString(int percentScore, int maxScore) {
        int score = round((float) maxScore * percentScore / 100);
        String scoreString = messageSource.getMessage("score", new String[]{Integer.toString(score), Integer.toString(percentScore)}, "Your score: {0} ( {1}% )", properties.getLocale());
        return scoreString;
    }

    private String getGreetingString(Person person, Boolean testPassed, Boolean goodResult) {
        String greetingCode = goodResult ? "congratulations" : testPassed ? "name" : "sorry";
        String greetingString = messageSource.getMessage(greetingCode, new String[]{person.getFirstName()}, "{0}", properties.getLocale());
        return greetingString;
    }

    private String getPassedString(Boolean testPassed) {
        String passed = messageSource.getMessage("passed", new String[]{""}, "Test is passed", properties.getLocale());
        String notPassed = messageSource.getMessage("not.passed", new String[]{""}, "Test is not passed", properties.getLocale());
        return testPassed ? passed : notPassed;
    }
}
