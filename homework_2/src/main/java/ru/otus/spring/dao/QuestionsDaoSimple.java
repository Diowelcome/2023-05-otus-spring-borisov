package ru.otus.spring.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Question;
import ru.otus.spring.loader.Loader;
import ru.otus.spring.parser.Parser;

import java.util.List;

@Component
public class QuestionsDaoSimple implements QuestionsDao {
    @Value("${data.load.source}")
    private String fileName;
    @Value("${data.parse.delimiter}")
    private String delimiter;
    @Value("${data.parse.answer.delimiter}")
    private String answerDelimiter;
    private Loader loader;
    private Parser parser;

    public QuestionsDaoSimple(Loader loader, Parser parser) {
        this.loader = loader;
        this.parser = parser;
    }

    @Override
    public String getTestName() {
        return fileName.substring(0, fileName.indexOf('.'));
    }

    @Override
    public List<Question> getQuestions() {

        List<String> lines = loader.getLines(fileName);
        List<Question> questions = parser.parse(lines, delimiter, answerDelimiter);

        return questions;
    }

}
