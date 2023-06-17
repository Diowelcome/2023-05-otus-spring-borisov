package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Component
public class IOServiceImpl implements IOService {
    private final PrintStream output;
    private final Scanner input;

    @Autowired
    public IOServiceImpl() {
        output = System.out;
        input = new Scanner(System.in);
    }

    public IOServiceImpl(PrintStream outputStream, InputStream inputStream) {
        output = outputStream;
        input = new Scanner(inputStream);
    }

    @Override
    public void outputString(String s){
        output.println(s);
    }

    @Override
    public String readStringWithPrompt(String prompt){
        outputString(prompt);
        return input.nextLine();
    }
}

