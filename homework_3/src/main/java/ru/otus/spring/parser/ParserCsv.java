package ru.otus.spring.parser;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Component
public class ParserCsv implements Parser {

    private final MessageSource messageSource;

    public ParserCsv(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public List<Question> parse(List<String> lines, String delimiter, String answerDelimiter, Locale locale) {
        List<Question> questions = new ArrayList<>();
        for (String line : lines) {
            Question question = parseLine(line, delimiter, answerDelimiter, locale);
            questions.add(question);
        }
        return questions;
    }

    private Question parseLine(String line, String delimiter, String answerDelimiter, Locale locale) {

        String[] rawData = line.split(delimiter);
        String question = messageSource.getMessage(getBundleCode(rawData[0]), new String[]{""}, rawData[0], locale);
        List<Answer> answers = parseAnswers(Arrays.copyOfRange(rawData, 1, rawData.length), answerDelimiter, locale);
        return new Question(question, answers);

    }

    private List<Answer> parseAnswers(String[] rawAnswers, String answerDelimiter, Locale locale) {
        List<Answer> answers = new ArrayList<>();
        for (String rawAnswer : rawAnswers) {
            answers.add(parseAnswer(rawAnswer, answerDelimiter, locale));
        }
        return answers;
    }

    private Answer parseAnswer(String line, String answerDelimiter, Locale locale) {
        String[] components = line.split(answerDelimiter);
        String answer = messageSource.getMessage(getBundleCode(components[0]), new String[]{""}, components[0], locale);
        int rightFlag = 0;
        if (components.length == 2 && components[1].equals("1")) {
            rightFlag = 1;
        }
        return new Answer(answer, rightFlag);
    }

    private String getBundleCode(String message) {
        return message.replaceAll(" ", ".");
    }

}
