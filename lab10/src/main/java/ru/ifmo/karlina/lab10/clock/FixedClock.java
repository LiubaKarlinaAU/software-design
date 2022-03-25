package ru.ifmo.karlina.lab10.clock;

import java.time.LocalDateTime;

public class FixedClock implements Clock{
    @Override
    public LocalDateTime now() {
        return LocalDateTime.of(2022, 2, 24, 4, 20);
    }
}
