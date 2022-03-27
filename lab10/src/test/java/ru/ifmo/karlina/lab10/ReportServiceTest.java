package ru.ifmo.karlina.lab10;

import org.junit.Assert;
import org.junit.Test;
import ru.ifmo.karlina.lab10.clock.Clock;
import ru.ifmo.karlina.lab10.clock.IterativeClock;
import ru.ifmo.karlina.lab10.exceptions.FitnessException;
import ru.ifmo.karlina.lab10.service.EntranceService;
import ru.ifmo.karlina.lab10.service.ManagerService;
import ru.ifmo.karlina.lab10.service.ReportService;
import ru.ifmo.karlina.lab10.storages.EventRepository;
import ru.ifmo.karlina.lab10.storages.ReadRepository;
import ru.ifmo.karlina.lab10.storages.WriteRepository;

import java.time.DayOfWeek;
import java.util.Map;

public class ReportServiceTest {
    private EntranceService entranceService;
    private ManagerService managerService;

    public void setUp(Clock clock){
        EventRepository repository = new EventRepository();
        ReadRepository readRepository = new ReadRepository(repository);
        WriteRepository writeRepository = new WriteRepository(repository);
        entranceService = new EntranceService(writeRepository, readRepository, clock);
        managerService = new ManagerService(writeRepository, readRepository, clock);
    }

    @Test
    public void checkEmptyStats() {
        Assert.assertEquals(0.0, ReportService.getDuration(), 0.01);
        Assert.assertEquals(0.0, ReportService.getFrequency(), 0.01);
    }

    @Test
    public void checkStats() {
        setUp(new IterativeClock());
        try {
            long subsId = managerService.createSubscription();
            entranceService.enter(subsId);
            entranceService.exit(subsId);
            Assert.assertEquals(1.0, ReportService.getDuration(), 0.01);
            Assert.assertEquals(0.14, ReportService.getFrequency(), 0.01);
            Map<DayOfWeek, Long> dailyStats = ReportService.getDailyStatistic();
            DayOfWeek fixedDay = DayOfWeek.THURSDAY;
            Assert.assertTrue(dailyStats.containsKey(fixedDay));
            Assert.assertEquals(new Long(1), dailyStats.get(fixedDay));
        } catch (FitnessException e) {
            Assert.assertNull(e);
        }
    }

    @Test
    public void checkStatsWithMoreData() {
        setUp(new IterativeClock());
        try {
            long subsId = managerService.createSubscription();
            entranceService.enter(subsId);
            entranceService.exit(subsId);
            entranceService.enter(subsId);
            long subsId2 = managerService.createSubscription();
            entranceService.enter(subsId2);
            entranceService.exit(subsId2);
            entranceService.exit(subsId);
            Assert.assertEquals(2.0, ReportService.getDuration(), 0.01);
            Assert.assertEquals(0.42, ReportService.getFrequency(), 0.01);
            Map<DayOfWeek, Long> dailyStats = ReportService.getDailyStatistic();
            DayOfWeek fixedDay = DayOfWeek.THURSDAY;
            Assert.assertTrue(dailyStats.containsKey(fixedDay));
            Assert.assertEquals(new Long(3), dailyStats.get(fixedDay));
        } catch (FitnessException e) {
            Assert.assertNull(e);
        }
    }
}
