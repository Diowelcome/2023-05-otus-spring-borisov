package ru.otus.spring.service;

import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.util.Scanner;

@Component
public class IOServiceImpl implements IOService {
    private final PrintStream output;
    private final Scanner input;

    public IOServiceImpl() {
        output = System.out;
        input = new Scanner(System.in);
    }

    @Override
    public void outputString(String s) {
        output.println(s);
    }

    @Override
    public String readStringWithPrompt(String prompt) {
        outputString(prompt);
        return input.nextLine();
    }
}

