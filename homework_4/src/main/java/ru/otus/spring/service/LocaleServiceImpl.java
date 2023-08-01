package ru.otus.spring.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.ApplicationProperties;

import java.util.Locale;

@Service
public class LocaleServiceImpl implements LocaleService {

    private String language;
    private Locale locale;
    private final ApplicationProperties properties;
    private final MessageSource messageSource;

    public LocaleServiceImpl(ApplicationProperties properties, MessageSource messageSource) {
        this.properties = properties;
        this.messageSource = messageSource;
        defineLocale();
    }

    @Override
    public String getDataLoadSource() {
        return locale.getLanguage() == "ru" ? properties.getDataLoadSourceRu() : properties.getDataLoadSource();
    }

    @Override
    public String getMessage(String messageKey, Object[] objects, String defaultString) {
        return messageSource.getMessage(messageKey, objects, defaultString, locale);
    }

    @Override
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    private void defineLocale() {
        if (language == null || language.isEmpty()) {
            locale = properties.getLocale();
        } else if (language.equals("en")) {
            locale = Locale.ENGLISH;
        } else {
            locale = new Locale("ru");
        }
    }
}
