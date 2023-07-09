package ru.otus.spring.parser;

import ru.otus.spring.domain.Question;

import java.util.List;
import java.util.Locale;

public interface Parser {
    List<Question> parse(List<String> lines, String delimiter, String answerDelimiter, Locale locale);
}
