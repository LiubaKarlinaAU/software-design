package ru.ifmo.karlina.lab10.clock;

import java.time.LocalDateTime;

public class IterativeClock implements Clock {
    private static int counter = 20;

    @Override
    public LocalDateTime now() {
        return LocalDateTime.of(2022, 2, 24, 4, counter++);
    }
}
