package ru.itmo.karlina.lab4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itmo.karlina.lab4.dao.TaskDao;
import ru.itmo.karlina.lab4.dao.TaskListDao;
import ru.itmo.karlina.lab4.model.Index;
import ru.itmo.karlina.lab4.model.Task;

import java.util.List;

@Controller
public class TaskController {
    private final TaskDao taskDao;
    private final TaskListDao taskListDao;
    private static int currentTaskListId = 0;

    public TaskController(TaskDao taskDao, TaskListDao taskListDao) {
        this.taskDao = taskDao;
        this.taskListDao = taskListDao;
    }

    @RequestMapping(value = "/get-tasks", method = RequestMethod.GET)
    public String getTasks(ModelMap map) {
        prepareModelMapTasks(map, taskDao.getTasks());
        return "tasks";
    }

    @RequestMapping(value = "/{id}/get-tasks", method = RequestMethod.GET)
    public String getTaskLists(@PathVariable("id") String id, ModelMap map) {
        int listId;
        listId = Integer.parseInt(id);
        currentTaskListId = listId;
        prepareModelMap(map, taskDao.getTasks(listId));
        return "tasklist";
    }

    @RequestMapping(value = "/add-task", method = RequestMethod.POST)
    public String addQuestion(@ModelAttribute("task") Task task) {
        task.setListId(currentTaskListId);
        taskDao.addTask(task);
        return "redirect:/" + currentTaskListId + "/get-tasks";
    }

    @RequestMapping(value = "/mark-task-as-done", method = RequestMethod.POST)
    public String addQuestion(@ModelAttribute("taskId") Index taskIndex) {
        int id = Integer.parseInt(taskIndex.getIndex());
        taskDao.markTaskAsDone(id);
        return "redirect:/get-tasks";
    }

    private void prepareModelMap(ModelMap map, List<Task> tasks) {
        map.addAttribute("list_tasks", tasks);
        map.addAttribute("task", new Task());
        map.addAttribute("taskIndex", new Index());
    }

    private void prepareModelMapTasks(ModelMap map, List<Task> tasks) {
        map.addAttribute("tasks", tasks);
        map.addAttribute("taskIndex", new Index());
    }
}
