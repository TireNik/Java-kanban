import manager.Managers;
import manager.TaskManager;
import tasks.Epic;
import tasks.Progress;
import tasks.SubTask;
import tasks.Task;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {

        File file = new File("/Users/sviridovnikita/IdeaProjects/java-kanban/fileBacked/tasks.csv");
        TaskManager taskManager = Managers.getFileBackedTaskManager(file);

        Task task1 = new Task(null,"ЗАДАЧА 1", "ОПИСАНИЕ 1", Progress.NEW);
        Task task2 = new Task(null,"ЗАДАЧА 2", "ОПИСАНИЕ 2", Progress.NEW,
                Duration.ofMinutes(90),
                LocalDateTime.now());

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Epic epic = new Epic(1,"ЭПИК 1", "ОПИСАНИЕ ЭПИК");
        taskManager.addEpic(epic);

        SubTask subTask1 = new SubTask(null,"ПОДЗАДАЧА 1", "Subtask ОПИСАНИЕ 1", Progress.NEW,
                3,
                Duration.ofMinutes(110),
                LocalDateTime.now());
        SubTask subTask2 = new SubTask(null,"ПОДЗАДАЧА 2", "Subtask ОПИСАНИЕ 2", Progress.NEW, 1);

        taskManager.addSubtask(subTask1);
        taskManager.addSubtask(subTask2);

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllSubTasks());
        System.out.println(taskManager.getAllEpics());
    }
}