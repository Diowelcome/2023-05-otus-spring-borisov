package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.domain.CheckBook;
import ru.otus.spring.domain.CheckUnit;
import ru.otus.spring.service.CheckBookService;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        CheckBookService service = context.getBean(CheckBookService.class);
        CheckBook checkBook = service.getCheckBook("test.csv");
        for(CheckUnit checkUnit : checkBook.getCheckUnits() ) {
            System.out.println(checkUnit.toString());
        }

        // Данная операция, в принципе не нужна.
        // Мы не работаем пока что с БД, а Spring Boot сделает закрытие за нас
        // Подробности - через пару занятий
        context.close();
    }
}