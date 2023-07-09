package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.spring.config.ApplicationProperties;
import ru.otus.spring.domain.Person;

@Component
public class PersonServiceImpl implements PersonService {

    private final IOService ioService;
    private final MessageSource messageSource;
    private final ApplicationProperties properties;

    public PersonServiceImpl(IOService ioService, MessageSource messageSource, ApplicationProperties properties) {
        this.ioService = ioService;
        this.messageSource = messageSource;
        this.properties = properties;
    }


    @Override
    public Person inputPerson() {
        String enterFirstName = "Enter your first name";
        String enterLastName = "Enter your last name";
        String firstName = ioService.readStringWithPrompt(messageSource.getMessage("first.name", new String[]{""}, enterFirstName, properties.getLocale()));
        String lastName = ioService.readStringWithPrompt(messageSource.getMessage("last.name", new String[]{""}, enterLastName, properties.getLocale()));
        return new Person(firstName, lastName);
    }
}
