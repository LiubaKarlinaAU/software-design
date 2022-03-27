package ru.ifmo.karlina.lab10;

import org.junit.Assert;
import org.junit.Test;
import ru.ifmo.karlina.lab10.clock.Clock;
import ru.ifmo.karlina.lab10.clock.RealClock;
import ru.ifmo.karlina.lab10.events.Event;
import ru.ifmo.karlina.lab10.exceptions.FitnessException;
import ru.ifmo.karlina.lab10.service.ManagerService;
import ru.ifmo.karlina.lab10.storages.EventRepository;
import ru.ifmo.karlina.lab10.storages.ReadRepository;
import ru.ifmo.karlina.lab10.storages.WriteRepository;

import java.util.List;

public class ManagerServiceTest {
    private ManagerService managerService;

    public void setUp(Clock clock){
        EventRepository repository = new EventRepository();
        ReadRepository readRepository = new ReadRepository(repository);
        WriteRepository writeRepository = new WriteRepository(repository);
        managerService = new ManagerService(writeRepository, readRepository, clock);
    }

    @Test
    public void extendInvalidSubscription(){
        setUp(new RealClock());
        try {
            Assert.assertFalse(managerService.extendSubscription(1L));
        } catch (FitnessException e) {
            Assert.assertNull(e);
        }
    }

    @Test
    public void extendValidSubscription(){
        setUp(new RealClock());
        try {
            long subsId =managerService.createSubscription();
            Assert.assertTrue(managerService.extendSubscription(subsId));
        } catch (FitnessException e) {
            Assert.assertNull(e);
        }
    }

    @Test
    public void getSubscriptionInfo(){
        setUp(new RealClock());
        try {
            long subsId = managerService.createSubscription();
            Assert.assertTrue(managerService.extendSubscription(subsId));
            List<Event> events = managerService.getInfo(subsId);
            Assert.assertEquals(2, events.size());
        } catch (FitnessException e) {
            Assert.assertNull(e);
        }
    }
}
