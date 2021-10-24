package ru.itmo.karlina.lab4.dao;


import ru.itmo.karlina.lab4.model.TaskList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskListInMemoryDao implements TaskListDao {
    private static int available_index = 0;
    private static final Map<Integer, TaskList> map = new HashMap<>();

    @Override
    public int addTaskList(TaskList list) {
        int id = available_index++;
        list.setId(id);
        map.put(id, list);
        return id;
    }

    @Override
    public int deleteTaskList(int id) {
        assert map.containsKey(id);
        map.remove(id);
        TaskInMemoryDao.deleteTasksByTaskListId(id);
        return id;
    }

    @Override
    public List<TaskList> getTaskLists() {
        return List.copyOf(map.values());
    }

    @Override
    public TaskList get(int id) {
        if (map.containsKey(id)) {
            return map.get(id);
        }
        return null;
    }
}
