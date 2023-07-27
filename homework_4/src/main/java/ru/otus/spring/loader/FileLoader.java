package ru.otus.spring.loader;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileLoader implements Loader {
    @Override
    public List<String> getLines(String dataSource) {
        List<String> lines = new ArrayList<>();
        try {
            File file = new ClassPathResource(dataSource).getFile();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
