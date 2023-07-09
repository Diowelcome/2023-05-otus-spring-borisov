package ru.otus.spring.dao;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.spring.config.ApplicationProperties;
import ru.otus.spring.domain.Question;
import ru.otus.spring.loader.Loader;
import ru.otus.spring.parser.Parser;

import java.util.List;

@Component
public class QuestionsDaoSimple implements QuestionsDao {
    private Loader loader;
    private Parser parser;
    private ApplicationProperties properties;

    public QuestionsDaoSimple(Loader loader, Parser parser, ApplicationProperties properties) {
        this.loader = loader;
        this.parser = parser;
        this.properties = properties;
    }

    @Override
    public String getTestName() {
        return properties.getDataLoadSource().substring(0, properties.getDataLoadSource().indexOf('.'));
    }

    @Override
    public List<Question> getQuestions() {

        List<String> lines = loader.getLines(properties.getDataLoadSource());
        List<Question> questions = parser.parse(lines, properties.getDelimiter(), properties.getAnswerDelimiter(), properties.getLocale());

        return questions;
    }

}
