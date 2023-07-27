package ru.otus.spring.runners;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.service.LocaleService;
import ru.otus.spring.service.QuestionsService;

import java.util.Locale;

@ShellComponent
public class ApplicationRunner {
    private final QuestionsService questionsService;
    private LocaleService localeService;

    public ApplicationRunner(QuestionsService questionsService, LocaleService localeService) {
        this.questionsService = questionsService;
        this.localeService = localeService;
    }

    @ShellMethod(value = "Show test questions", key = {"s", "show"})
    public void show() {
        questionsService.showQuestions();
    }

    @ShellMethod(value = " Run testing", key = {"r", "run"})
    public void run() {
        questionsService.showTestRunResults();
    }

    @ShellMethod(value = "Define language: english (en) or russian (ru)", key = {"l", "lang"})
    public void defineLanguage(@ShellOption(help = "language") String lang) {
        switch (lang) {
            case "ru":
                localeService.setLocale(new Locale("ru"));
                break;
            default:
                localeService.setLocale(Locale.ENGLISH);
        }
    }

}