package ru.itmo.karlina.lab4.dao;

import ru.itmo.karlina.lab4.model.Task;

import java.util.List;

public interface TaskDao {
    int addTask(Task task);
    void markTaskAsDone(int id);

    List<Task> getTasks(int listIndex);
    List<Task> getTasks();
}
