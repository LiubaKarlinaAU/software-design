package ru.itmo.karlina.lab4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itmo.karlina.lab4.dao.TaskDao;
import ru.itmo.karlina.lab4.dao.TaskListDao;
import ru.itmo.karlina.lab4.model.Index;
import ru.itmo.karlina.lab4.model.Task;
import ru.itmo.karlina.lab4.model.TaskList;

import java.util.List;

@Controller
public class TaskListController {
    private final TaskDao taskDao;
    private final TaskListDao taskListDao;

    public TaskListController(TaskDao taskDao, TaskListDao taskListDao) {
        this.taskDao = taskDao;
        this.taskListDao = taskListDao;
    }

    @RequestMapping(value = "/get-task-lists", method = RequestMethod.GET)
    public String getTaskLists(ModelMap map) {
        prepareModelMap(map, taskListDao.getTaskLists());
        return "index";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String getErrorPage(ModelMap map) {
        return "error";
    }

    @RequestMapping(value = "/add-task-list", method = RequestMethod.POST)
    public String addQuestion(@ModelAttribute("tasklist") TaskList taskList) {
        taskListDao.addTaskList(taskList);
        return "redirect:/get-task-lists";
    }

    @RequestMapping(value = "/delete-task-list", method = RequestMethod.POST)
    public String addQuestion(@ModelAttribute("listId") Index listIndex) {
        int id = Integer.parseInt(listIndex.getIndex());
        taskListDao.deleteTaskList(id);
        return "redirect:/get-task-lists";
    }

    private void prepareModelMap(ModelMap map, List<TaskList> taskLists) {
        map.addAttribute("tasklists", taskLists);
        map.addAttribute("tasklist", new TaskList());
        map.addAttribute("listIndex", new Index());
    }
}
