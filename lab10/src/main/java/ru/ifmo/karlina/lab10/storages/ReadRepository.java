package ru.ifmo.karlina.lab10.storages;

import ru.ifmo.karlina.lab10.events.Event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReadRepository {
    private final EventRepository repository;

    public ReadRepository(EventRepository repository) {
        this.repository = repository;
    }

    public boolean validSubscription(long subscriptionId, LocalDateTime now) {
        return true; //TODO
    }

    public List<Event> getEvents(){
        List<Event> result = new ArrayList<>();
        for (Event  event : repository.getEvents()) {
            result.add(new Event(event));
        }

        return result;
    }
}
