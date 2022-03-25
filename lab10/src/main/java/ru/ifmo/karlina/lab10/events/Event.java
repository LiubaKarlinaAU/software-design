package ru.ifmo.karlina.lab10.events;

import ru.ifmo.karlina.lab10.clock.Clock;

import java.time.LocalDateTime;

public class Event{
    private final long subscriptionId;
    private final LocalDateTime time;
    private final EventType type;

    public Event(long subscriptionId, Clock clock, EventType type) {
        this.subscriptionId = subscriptionId;
        time = clock.now();
        this.type = type;
    }

    public Event(long subscriptionId, LocalDateTime time, EventType type) {
        this.subscriptionId = subscriptionId;
        this.time = time;
        this.type = type;
    }

    public Event(Event other) {
        this.subscriptionId = other.subscriptionId;
        this.time = other.time;
        this.type = other.type;
    }

    public long getSubscriptionId() {
        return subscriptionId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public EventType getType() {
        return type;
    }
}
