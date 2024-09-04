package Test;

import Manager.InMemoryTaskManager;
import Manager.Managers;
import Manager.TaskManager;
import Tasks.Epic;
import Tasks.Progress;
import Tasks.SubTask;
import Tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    InMemoryTaskManager taskManager;

    @BeforeEach
    void set() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    void addAndFindTaskTest() throws CloneNotSupportedException {
        Task task = new Task(1, "Task", "Description", Progress.NEW);
        Task task2 = new Task(1, "Task2", "Description2", Progress.NEW);
        taskManager.addTask(task);
        taskManager.addTask(task2);

        Task findTask = taskManager.getTaskById(1);

        assertEquals(task.getId(), findTask.getId(), "Id должен совпадать");
        assertEquals(task.getName(), findTask.getName(), "Название должно совпадать");
        assertEquals(task.getDescription(), findTask.getDescription(), "Описание должно совпадать");
        assertNotNull(findTask, "Кажишь не нашлась");
        assertNotEquals(task2, findTask, "Должно не равно");
        assertEquals(task, findTask, "Должно равно");

    }

    @Test
    void addAndFindEpicTest() throws CloneNotSupportedException {
        Epic epic = new Epic(1, "Epic", "Description");
        Epic epic2 = new Epic(1, "Epic2", "Description2");

        taskManager.addEpic(epic);

        Epic findEpic = taskManager.getEpicById(1);

        assertEquals(epic.getId(), findEpic.getId(), "Id должен совпадать");
        assertEquals(epic.getName(), findEpic.getName(), "Название должно совпадать");
        assertEquals(epic.getDescription(), findEpic.getDescription(), "Описание должно совпадать");
        assertNotNull(findEpic, "Кажишь не нашлась");
        assertNotEquals(epic2, findEpic, "Должно не равно");
        assertEquals(epic, findEpic, "Должно равно");


    }

    @Test
    void addAndFindSubtaskTest() throws CloneNotSupportedException {
        Epic epic = new Epic(1, "Epic", "Description");
        taskManager.addEpic(epic);

        SubTask subTask = new SubTask(1, "SubTask", "Description", Progress.NEW, 1);
        SubTask subTask2 = new SubTask(1, "SubTask2", "Description2", Progress.NEW, 1);

        taskManager.addSubtask(subTask);
        taskManager.addSubtask(subTask2);

        SubTask findSubtask = taskManager.getSubtaskById(2);

        assertEquals(subTask.getId(), findSubtask.getId(), "Id должен совпадать");
        assertEquals(subTask.getName(), findSubtask.getName(), "Название должно совпадать");
        assertEquals(subTask.getDescription(), findSubtask.getDescription(), "Описание должно совпадать");

        assertNotNull(findSubtask, "Кажишь не нашлась");
        assertNotEquals(subTask2, findSubtask, "Должно не равно");
        assertEquals(subTask, findSubtask, "Должно равно");

        List<SubTask> subTaskList = new ArrayList<>();
        subTaskList.add(subTask);
        subTaskList.add(subTask2);

        assertEquals(subTaskList, taskManager.getSubtasksByEpic(1), "Не совпадают");

    }

    @Test
    void managerGetDefaultTest() {
        TaskManager taskManager = Managers.getDefault();

        assertNotNull(taskManager, "Не должен быть Null");
    }

}