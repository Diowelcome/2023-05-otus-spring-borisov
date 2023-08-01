package ru.otus.spring.service;

import java.util.Locale;

public interface LocaleService {
    String getDataLoadSource();
    String getMessage(String messageKey, Object[] objects, String defaultMessage);
    void setLocale(Locale locale);
}
