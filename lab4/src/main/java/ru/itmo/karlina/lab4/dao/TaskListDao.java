package ru.itmo.karlina.lab4.dao;

import ru.itmo.karlina.lab4.model.TaskList;

import java.util.List;

public interface TaskListDao {
    int addTaskList(TaskList list);
    int deleteTaskList(int id);

    List<TaskList> getTaskLists();
    TaskList get(int id);
}
