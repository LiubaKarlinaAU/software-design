package ru.ifmo.karlina.lab10.storages;

import ru.ifmo.karlina.lab10.clock.Clock;
import ru.ifmo.karlina.lab10.events.Event;

public class WriteRepository {
    private final EventRepository repository;

    public WriteRepository(EventRepository repository) {
        this.repository = repository;
    }

    public void addEvent(Event event) {
        repository.addEvent(event);
    }

    public long createSubscription(Clock clock) {
        return repository.createSubscription(clock);
    }
}
