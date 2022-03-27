package ru.ifmo.karlina.lab10;

import org.junit.Assert;
import org.junit.Test;
import ru.ifmo.karlina.lab10.clock.Clock;
import ru.ifmo.karlina.lab10.clock.RealClock;
import ru.ifmo.karlina.lab10.exceptions.FitnessException;
import ru.ifmo.karlina.lab10.service.EntranceService;
import ru.ifmo.karlina.lab10.service.ManagerService;
import ru.ifmo.karlina.lab10.storages.EventRepository;
import ru.ifmo.karlina.lab10.storages.ReadRepository;
import ru.ifmo.karlina.lab10.storages.WriteRepository;

public class EntranceServiceTest {
    private EntranceService entranceService;
    private  ReadRepository readRepository;
    private WriteRepository writeRepository;

    public void setUp(Clock clock){
        EventRepository repository = new EventRepository();
        readRepository = new ReadRepository(repository);
        writeRepository = new WriteRepository(repository);
        entranceService = new EntranceService(writeRepository, readRepository, clock);
    }

    @Test
    public void enterWithInvalidSubscription(){
       setUp(new RealClock());
        try {
            Assert.assertFalse(entranceService.enter(1L));
        } catch (FitnessException e) {
            Assert.assertNull(e);
        }
    }

    @Test
    public void enterWithValidSubscription(){
        Clock clock = new RealClock();
        setUp(clock);
        ManagerService managerService = new ManagerService(writeRepository, readRepository, clock);
        try {
            long subsId = managerService.createSubscription();
            Assert.assertTrue(entranceService.enter(subsId));
        } catch (FitnessException e) {
            Assert.assertNull(e);
        }
    }
}
