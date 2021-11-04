package ru.itmo.karlina.lab7;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.itmo.karlina.lab7.aspect.LoggingStatisticAspect;
import ru.itmo.karlina.lab7.dao.CustomerInMemoryDao;
import ru.itmo.karlina.lab7.domain.CustomerManager;
import ru.itmo.karlina.lab7.domain.CustomerManagerImpl;

@Configuration
@EnableAspectJAutoProxy
public class ContextConfiguration {
    @Bean
    public CustomerManager customerManager() {
        return new CustomerManagerImpl(new CustomerInMemoryDao());
    }

    @Bean
    public LoggingStatisticAspect aspect() {
        return new LoggingStatisticAspect();
    }
}
