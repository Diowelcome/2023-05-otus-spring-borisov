package ru.otus.spring.runners;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ru.otus.spring.service.QuestionsService;
import ru.otus.spring.service.QuestionsServiceImpl;

import java.util.Arrays;

@Component
public class ApplicationRunner implements CommandLineRunner {
    private final QuestionsService questionsService;

    public ApplicationRunner(QuestionsService questionsService) {
        this.questionsService = questionsService;
    }

    @Override
    public void run(String... args) {
        questionsService.showTestRunResults();
    }
}