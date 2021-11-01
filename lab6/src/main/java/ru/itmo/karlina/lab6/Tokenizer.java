package ru.itmo.karlina.lab6;

import ru.itmo.karlina.lab6.token.*;
import java.util.ArrayList;
import java.util.List;

import static ru.itmo.karlina.lab6.token.Operation.*;
import static ru.itmo.karlina.lab6.token.Brace.*;

public class Tokenizer {
    private enum State {
        START,
        NUMBER,
        ERROR,
        END
    }

    private State state;
    private final String input;
    private int number;
    private int currentIndex;

    public Tokenizer(String input) {
        this.input = input;
    }

    List<Token> parse() {
        List<Token> list = new ArrayList<>();
        number = 0;
        currentIndex = 0;
        state = State.START;
        char c;
        while (true) {
            if (currentIndex == input.length()) {
                if (state == State.NUMBER) {
                    list.add(new NumberToken(number));
                }
                // State.END;
                return list;
            }
            c = input.charAt(currentIndex);
            if (c == ' ') {
                currentIndex++;
                continue;
            }
            switch (state) {
                case START -> startProceedCharacter(c, list);
                case ERROR -> throw new IllegalArgumentException("Unknown character '" + c + "'.");
                case NUMBER -> numberProceedCharacter(c, list);
            }
        }
    }

    private void numberProceedCharacter(char c, List<Token> list) {
        if (c >= '0' && c <= '9') {
            number = number * 10 + c - '0';
            currentIndex++;
        } else {
            list.add(new NumberToken(number));
            number = 0;
            state = State.START;
        }
    }

    private void startProceedCharacter(char c, List<Token> list) {
        switch (c) {
            case '*' -> list.add(MULTIPLY);
            case '/' -> list.add(DIVIDE);
            case '+' -> list.add(PLUS);
            case '-' -> list.add(MINUS);
            case '(' -> list.add(LEFT);
            case ')' -> list.add(RIGHT);
            default -> {
                if (c >= '0' && c <= '9') {
                    state = State.NUMBER;
                } else {
                    state = State.ERROR;
                }
            }
        }
        if (state == State.START) {
            currentIndex++;
        }
    }
}
