package ru.otus.spring.dao;

import org.springframework.stereotype.Component;
import ru.otus.spring.config.ApplicationProperties;
import ru.otus.spring.domain.Question;
import ru.otus.spring.loader.Loader;
import ru.otus.spring.parser.Parser;
import ru.otus.spring.service.LocaleService;

import java.util.List;

@Component
public class QuestionsDaoSimple implements QuestionsDao {
    private Loader loader;
    private Parser parser;
    private ApplicationProperties properties;
    private final LocaleService localeService;

    public QuestionsDaoSimple(Loader loader, Parser parser, ApplicationProperties properties, LocaleService localeService) {
        this.loader = loader;
        this.parser = parser;
        this.properties = properties;
        this.localeService = localeService;
    }

    @Override
    public String getTestName() {
        return localeService.getDataLoadSource().substring(0, localeService.getDataLoadSource().indexOf('.'));
    }

    @Override
    public List<Question> getQuestions() {
        List<String> lines = loader.getLines(localeService.getDataLoadSource());
        List<Question> questions = parser.parse(lines, properties.getDelimiter(), properties.getAnswerDelimiter());
        return questions;
    }

}
