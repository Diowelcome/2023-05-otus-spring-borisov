package ru.otus.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class QuestionsDaoSimple implements QuestionsDao {

    private String fileName;

    @Autowired
    public void setFileName(@Value("${file.path}") String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getTestName() {
        return fileName.substring(0, fileName.indexOf('.'));
    }

    @Override
    public List<Question> getQuestions() {

        char delimiter = ';';
        char answerDelimiter = ':';
        List<String> lines = getLines(fileName);
        List<Question> questions = parseLines(lines, delimiter, answerDelimiter);

        return questions;
    }

    private List<String> getLines(String fileName) {
        List<String> lines = new ArrayList<>();
        try {
            File file = new ClassPathResource(fileName).getFile();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private List<Question> parseLines(List<String> lines, char delimiter, char answerDelimiter) {
        List<Question> questions = new ArrayList<>();
        for (String line : lines) {
            Question question = parseLine(line, delimiter, answerDelimiter);
            questions.add(question);
        }
        return questions;
    }

    private Question parseLine(String line, char delimiter, char answerDelimiter) {
        Pattern pattern = Pattern.compile("[^" + delimiter + "|$]+");
        Matcher matcher = pattern.matcher(line);
        boolean firstMatch = true;
        String question = new String();
        List<Answer> answers = new ArrayList<>();
        while (matcher.find()) {
            if (firstMatch) {
                question = matcher.group();
            } else {
                answers.add(parseAnswer(matcher.group(), answerDelimiter));
            }
            firstMatch = false;
        }
        return new Question(question, answers);
    }

    private Answer parseAnswer(String line, char answerDelimiter) {
        Pattern answerPattern = Pattern.compile("[^" + answerDelimiter + "|$]+");
        Matcher answerMatcher = answerPattern.matcher(line);
        Pattern flagPattern = Pattern.compile(answerDelimiter + "[0|1]");
        Matcher flagMatcher = flagPattern.matcher(line);
        String answer = new String();
        int rightFlag = 0;
        String flag = new String();
        while (answerMatcher.find()) {
            answer = answerMatcher.group();
            break;
        }
        while (flagMatcher.find()) {
            flag = flagMatcher.group();
            if (flag.equals(answerDelimiter + "1")) {
                rightFlag = 1;
            }
            break;
        }
        return new Answer(answer, rightFlag);
    }

}
