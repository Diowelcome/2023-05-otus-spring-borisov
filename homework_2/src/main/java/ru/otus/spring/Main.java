package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.service.QuestionsService;

@PropertySource("classpath:application.properties")
@ComponentScan
public class Main {

    public static void main(String[] args) {
        // Домашнее задание 1
        // ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        // QuestionsService service = context.getBean(QuestionsService.class);
        // service.showQuestions();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        QuestionsService service = context.getBean(QuestionsService.class);
        service.showTestRunResults();

        // Данная операция, в принципе не нужна.
        // Мы не работаем пока что с БД, а Spring Boot сделает закрытие за нас
        // Подробности - через пару занятий
        context.close();
    }
}