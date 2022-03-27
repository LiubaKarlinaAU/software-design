package ru.ifmo.karlina.lab10.service;

import ru.ifmo.karlina.lab10.clock.Clock;
import ru.ifmo.karlina.lab10.events.Event;
import ru.ifmo.karlina.lab10.events.EventType;
import ru.ifmo.karlina.lab10.exceptions.FitnessException;
import ru.ifmo.karlina.lab10.storages.ReadRepository;
import ru.ifmo.karlina.lab10.storages.WriteRepository;

import java.time.LocalDateTime;

public class EntranceService {
    private final WriteRepository writeRepository;
    private final ReadRepository readRepository;
    private final Clock clock;

    public EntranceService(WriteRepository writeRepository, ReadRepository readRepository, Clock clock) {
        this.writeRepository = writeRepository;
        this.readRepository = readRepository;
        this.clock = clock;
    }


    public boolean enter(long subscriptionId) throws FitnessException {
        LocalDateTime now = clock.now();
        if (!readRepository.validSubscription(subscriptionId, now)) {
            return false;
        }

        writeRepository.addEvent(new Event(subscriptionId, now, EventType.Enter));
        return true;
    }

    public void exit(long subscriptionId) throws FitnessException {
        writeRepository.addEvent(new Event(subscriptionId, clock, EventType.Exit));
    }
}
