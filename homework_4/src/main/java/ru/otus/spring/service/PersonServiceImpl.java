package ru.otus.spring.service;

import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Person;

@Component
public class PersonServiceImpl implements PersonService {

    private final IOService ioService;
    private final LocaleService localeService;

    public PersonServiceImpl(IOService ioService, LocaleService localeService) {
        this.ioService = ioService;
        this.localeService = localeService;
    }


    @Override
    public Person inputPerson() {
        String enterFirstName = "Enter your first name";
        String enterLastName = "Enter your last name";
        String firstName = ioService.readStringWithPrompt(localeService.getMessage("first.name", new String[]{""}, enterFirstName));
        String lastName = ioService.readStringWithPrompt(localeService.getMessage("last.name", new String[]{""}, enterLastName));
        return new Person(firstName, lastName);
    }
}
