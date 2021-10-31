package ru.itmo.karlina.lab4.dao;

import ru.itmo.karlina.lab4.model.Task;

import java.util.*;
import java.util.stream.Collectors;

public class TaskInMemoryDao implements TaskDao {
    private static int available_index = 0;
    private static final Map<Integer, Task> tasks = new HashMap<>();
    private static final Map<Integer, Set<Integer>> taskListIdToTaskId = new HashMap<>();

    static void deleteTasksByTaskListId(int listId) {
        if (!taskListIdToTaskId.containsKey(listId)) {
            return;
        }
        for (int taskId : taskListIdToTaskId.get(listId)) {
            tasks.remove(taskId);
        }
    }

    @Override
    public int addTask(Task task) {
        int id = available_index++;
        task.setId(id);
        tasks.put(id, task);
        int listId = task.getListId();
        if (!taskListIdToTaskId.containsKey(listId)) {
            taskListIdToTaskId.put(listId, new HashSet<>());
        }
        taskListIdToTaskId.get(listId).add(id);
        return id;
    }

    @Override
    public void markTaskAsDone(int id) {
        assert tasks.containsKey(id);
        tasks.get(id).setDone(true);
    }

    @Override
    public List<Task> getTasks(int listId) {
        return List.copyOf(TaskInMemoryDao.tasks.values().stream().filter(task -> task.getListId() == listId).collect(Collectors.toList()));
    }

    @Override
    public List<Task> getTasks() {
        return List.copyOf(TaskInMemoryDao.tasks.values());
    }
}
