package ru.itmo.karlina.lab4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.itmo.karlina.lab4.dao.TaskDao;
import ru.itmo.karlina.lab4.dao.TaskInMemoryDao;
import ru.itmo.karlina.lab4.dao.TaskListDao;
import ru.itmo.karlina.lab4.dao.TaskListInMemoryDao;

@Configuration
public class InMemoryDaoContextConfiguration {
    @Bean
    public TaskDao taskDao() {
        return new TaskInMemoryDao();
    }
    @Bean
    public TaskListDao taskListDao() {
        return new TaskListInMemoryDao();
    }
}
