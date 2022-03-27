package ru.ifmo.karlina.lab10.storages;

import ru.ifmo.karlina.lab10.events.Event;
import ru.ifmo.karlina.lab10.events.EventType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ReadRepository {
    private final EventRepository repository;
    private static final long ONE_MONTH = 30 * 24 * 60 * 60;

    public ReadRepository(EventRepository repository) {
        this.repository = repository;
    }

    // checking whether subscription is valid for current time
    public boolean validSubscription(long subscriptionId, LocalDateTime now) {
        LocalDateTime subsStart = null;
        List<Event> events = getEvents(subscriptionId);
        for (Event  event : events) {
            if (event.getType() == EventType.CreateSubs) {
                subsStart = event.getTime();
            } else if (event.getType() == EventType.ExtendSubs && subsStart != null) {
                if (Duration.between(event.getTime(), subsStart).get(ChronoUnit.SECONDS) < ONE_MONTH) {
                    subsStart = event.getTime();
                }
            }
        }

        if (subsStart == null) {
            return false;
        }
        return Duration.between(subsStart, now).get(ChronoUnit.SECONDS) < ONE_MONTH;
    }

    public List<Event> getEvents(){
        List<Event> result = new ArrayList<>();
        for (Event  event : repository.getEvents()) {
            result.add(new Event(event));
        }

        return result;
    }

    public List<Event> getEvents(long subscriptionId){
        List<Event> result = new ArrayList<>();
        for (Event  event : repository.getEvents()) {
            if (event.getSubscriptionId() == subscriptionId) {
                result.add(new Event(event));
            }
        }

        return result;
    }
}
