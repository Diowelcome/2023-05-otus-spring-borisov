package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Person;

@Component
public class PersonServiceImpl implements PersonService {

    private final IOService ioService;

    @Autowired
    public PersonServiceImpl(IOService ioService) {
        this.ioService = ioService;
    }

    @Override
    public Person inputPerson() {
        String firstName = ioService.readStringWithPrompt("Enter your first name:");
        String lastName = ioService.readStringWithPrompt("Enter your last name:");
        return new Person(firstName, lastName);
    }
}
