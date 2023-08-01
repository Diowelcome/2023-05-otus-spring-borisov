package ru.otus.spring.loader;

import java.util.List;

public interface Loader {
    List<String> getLines(String dataSource);
}
