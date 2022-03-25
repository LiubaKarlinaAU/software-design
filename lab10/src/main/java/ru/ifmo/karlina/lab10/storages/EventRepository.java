package ru.ifmo.karlina.lab10.storages;

import ru.ifmo.karlina.lab10.clock.Clock;
import ru.ifmo.karlina.lab10.events.Event;
import ru.ifmo.karlina.lab10.events.EventType;
import ru.ifmo.karlina.lab10.service.ReportService;

import java.util.ArrayList;
import java.util.List;

public class EventRepository {
    private final List<Event> events = new ArrayList<>();
    private long lastSubscriptionId = 1;

    public void addEvent(Event event) {
        events.add(event);
        ReportService.handle(event);
    }

    public long createSubscription(Clock clock){
        long current = lastSubscriptionId;
        lastSubscriptionId++;
        Event event = new Event(current, clock, EventType.CreateSubs);
        addEvent(event);
        return current;
    }

    public List<Event> getEvents(){
        return events;
    }
}
