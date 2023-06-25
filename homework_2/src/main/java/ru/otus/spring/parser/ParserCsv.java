package ru.otus.spring.parser;

import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ParserCsv implements Parser {
    @Override
    public List<Question> parse(List<String> lines, String delimiter, String answerDelimiter) {
        List<Question> questions = new ArrayList<>();
        for (String line : lines) {
            Question question = parseLine(line, delimiter, answerDelimiter);
            questions.add(question);
        }
        return questions;
    }

    private Question parseLine(String line, String delimiter, String answerDelimiter) {

        String[] rawData = line.split(delimiter);
        String question = rawData[0];
        List<Answer> answers = parseAnswers(Arrays.copyOfRange(rawData, 1, rawData.length), answerDelimiter);
        return new Question(question, answers);

    }

    private List<Answer> parseAnswers(String[] rawAnswers, String answerDelimiter) {
        List<Answer> answers = new ArrayList<>();
        for (String rawAnswer : rawAnswers) {
            answers.add(parseAnswer(rawAnswer, answerDelimiter));
        }
        return answers;
    }

    private Answer parseAnswer(String line, String answerDelimiter) {
        String[] components = line.split(answerDelimiter);
        String answer = components[0];
        int rightFlag = 0;
        if (components.length == 2 && components[1].equals("1")) {
            rightFlag = 1;
        }
        return new Answer(answer, rightFlag);
    }

}
