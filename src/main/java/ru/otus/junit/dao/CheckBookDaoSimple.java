package ru.otus.junit.dao;

import org.springframework.core.io.ClassPathResource;
import ru.otus.junit.domain.Answer;
import ru.otus.junit.domain.CheckBook;
import ru.otus.junit.domain.CheckUnit;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckBookDaoSimple implements CheckBookDao {

    String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public CheckBook getCheckBook(String fileName) {

        char delimiter = ';';
        List<CheckUnit> checkUnits = new ArrayList<>();

        try {
            File file = new ClassPathResource(fileName).getFile();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    Pattern pattern = Pattern.compile("[^" + delimiter + "|$]+");
                    Matcher matcher = pattern.matcher(line);
                    int i = 0;
                    String question = new String();
                    List<Answer> answers = new ArrayList<>();
                    while (matcher.find()) {
                        if (i == 0) {
                            question = matcher.group();
                        } else {
                            answers.add(new Answer(matcher.group()));
                        }
                        i++;
                    }
                    checkUnits.add(new CheckUnit(question, answers));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        CheckBook checkBook = new CheckBook(checkUnits);
        return checkBook;
    }

}
