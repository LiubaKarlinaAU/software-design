package ru.itmo.karlina.lab7;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.itmo.karlina.lab7.aspect.LoggingStatisticAspect;
import ru.itmo.karlina.lab7.domain.Customer;
import ru.itmo.karlina.lab7.domain.CustomerManager;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private static final int NUMBER_OF_FIND_CUSTOMER_CALLS = 5;
    private static final int NUMBER_OF_ADD_CUSTOMER_CALLS = 3;
    private static final int NAME_LENGTH = 5;

    public static void main(String[] args) {
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(ContextConfiguration.class);
        CustomerManager customerManager = ctx.getBean(CustomerManager.class);

        findCustomers(customerManager, fillDatabase(customerManager));

        LoggingStatisticAspect aspect = ctx.getBean(LoggingStatisticAspect.class);
        aspect.printStatistic();
    }

    private static List<Integer> fillDatabase(CustomerManager customerManager) {
        List<Integer> ids = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < NUMBER_OF_ADD_CUSTOMER_CALLS; i++) {
            String name = generateString(random);
            int id = customerManager.addCustomer(new Customer(name));
            ids.add(id);
        }
        return ids;
    }

    private static void findCustomers(CustomerManager customerManager, List<Integer> ids) {
        Random rand = new Random();
        for (int i = 0; i < NUMBER_OF_FIND_CUSTOMER_CALLS; i++) {
            customerManager.findCustomer(ids.get(rand.nextInt(ids.size())));
        }
    }

    private static String generateString(Random random) {
        byte[] array = new byte[NAME_LENGTH];
        random.nextBytes(array);

        return new String(array, StandardCharsets.UTF_8);
    }
}
