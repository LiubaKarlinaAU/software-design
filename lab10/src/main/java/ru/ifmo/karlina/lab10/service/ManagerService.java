package ru.ifmo.karlina.lab10.service;

import ru.ifmo.karlina.lab10.clock.Clock;
import ru.ifmo.karlina.lab10.events.Event;
import ru.ifmo.karlina.lab10.events.EventType;
import ru.ifmo.karlina.lab10.storages.ReadRepository;
import ru.ifmo.karlina.lab10.storages.WriteRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ManagerService {
    private final WriteRepository writeRepository;
    private final ReadRepository readRepository;
    private final Clock clock;

    public ManagerService(WriteRepository writeRepository, ReadRepository readRepository, Clock clock) {
        this.writeRepository = writeRepository;
        this.readRepository = readRepository;
        this.clock = clock;
    }

    public long createSubscription() {
        return writeRepository.createSubscription(clock);
    }

    public boolean extendSubscription(long subscriptionId) {
        LocalDateTime now = clock.now();
        if (!readRepository.validSubscription(subscriptionId, now)) {
            return false;
        }

        writeRepository.addEvent(new Event(subscriptionId, now, EventType.ExtendSubs));
        return true;
    }

    public List<Event> getInfo(long subscriptionId){
        return readRepository.getEvents().stream().filter(e -> e.getSubscriptionId() == subscriptionId).collect(Collectors.toList());
    }
}
