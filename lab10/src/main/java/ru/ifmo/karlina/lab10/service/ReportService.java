package ru.ifmo.karlina.lab10.service;

import ru.ifmo.karlina.lab10.events.Event;
import ru.ifmo.karlina.lab10.events.EventType;
import ru.ifmo.karlina.lab10.exceptions.FitnessException;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class ReportService {
    private static final Map<Long, LocalDateTime> visitStart = new HashMap<>();
    private static final Map<DayOfWeek, Long> dailyStats = new HashMap<>();
    private static long totalDuration = 0L;
    private static long totalVisits = 0L;

    public static void handle(Event event) throws FitnessException {
        if (event.getType() == EventType.Enter) {
            visitStart.put(event.getSubscriptionId(), event.getTime());
            DayOfWeek day = event.getTime().getDayOfWeek();
            if (dailyStats.containsKey(day)) {
                dailyStats.put(day, dailyStats.get(day) + 1);
            } else {
                dailyStats.put(day, 1L);
            }
        } else if (event.getType() == EventType.Exit) {
            if (!visitStart.containsKey(event.getSubscriptionId())) {
                throw new FitnessException("This subscription should enter first");
            }
            LocalDateTime startTime = visitStart.get(event.getSubscriptionId());
            long duration = Duration.between(startTime, event.getTime()).get(ChronoUnit.SECONDS) / 60;
            totalDuration += duration;
            totalVisits += 1;
        }
    }

    public static Map<DayOfWeek, Long> getDailyStatistic() {
        return dailyStats;
    }

    public static double getFrequency() {
        double result = 0.;
        for(DayOfWeek day : dailyStats.keySet()) {
            result += dailyStats.get(day);
        }

        return result / 7;
    }

    public static double getDuration() {
        if (totalVisits == 0) {
            return 0;
        }
        return totalDuration * 1.0 / totalVisits;
    }
}
