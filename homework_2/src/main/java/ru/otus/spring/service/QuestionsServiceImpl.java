package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionsDao;
import ru.otus.spring.domain.Answer;
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
    @Value("${test.max.score}")
    private int maxScore;
    @Value("${test.min.passed}")
    private int minPassedPercent;
    @Value("${test.min.good}")
    private int minGoodPercent;

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
        String typeYourAnswerString = "Type right number or numbers, separated by commas (for example: 1 or 1,3):";
        return ioService.readStringWithPrompt(String.format("%s\n%s", question.toString(), typeYourAnswerString));
    }

    private String getBriefResult(Person person, int percentScore) {
        Boolean testPassed = percentScore >= minPassedPercent;
        Boolean goodResult = percentScore >= minGoodPercent;
        String greetingString = getGreetingString(person, testPassed, goodResult);
        String scoreString = getScoreString(percentScore, maxScore);
        String passedString = getPassedString(testPassed);
        return greetingString + '\n' + scoreString + '\n' + passedString;
    }

    private String getScoreString(int percentScore, int maxScore) {
        int score = round((float) maxScore * percentScore / 100);
        String scoreString = String.format("Your score: %d ( %d%% )", score, percentScore);
        return scoreString;
    }

    private String getGreetingString(Person person, Boolean testPassed, Boolean goodResult) {
        String greetingFormat = goodResult ? "Congratulations, %s!" : testPassed ? "%s," : "Sorry, %s,";
        String greetingString = String.format(greetingFormat, person.getFirstName());
        return greetingString;
    }

    private String getPassedString(Boolean testPassed) {
        return testPassed ? "Test is passed." : "Test is not passed.";
    }

}
