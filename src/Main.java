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

        Task task1 = new Task(null,"ЗАДАЧА 1", "ОПИСАНИЕ 1", Progress.NEW,
                Duration.ofMinutes(20),
                LocalDateTime.now());
        Task task2 = new Task(null,"ЗАДАЧА 2", "ОПИСАНИЕ 2", Progress.NEW,
                Duration.ofMinutes(90),
                LocalDateTime.of(2024, 10, 27, 18, 18));

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Task task3 = new Task(2,"ЗАДАЧА 2", "ОПИСАНИЕ 2", Progress.NEW,
                Duration.ofMinutes(90),
                LocalDateTime.now());

        taskManager.updateTask(task3);

        Epic epic = new Epic(3,"ЭПИК 1", "ОПИСАНИЕ ЭПИК");
        taskManager.addEpic(epic);

        Epic epic1 = new Epic(4,"ЭПИК 1", "ОПИСАНИЕ ЭПИК");
        taskManager.addEpic(epic1);

        SubTask subTask1 = new SubTask(null,"ПОДЗАДАЧА 1", "Subtask ОПИСАНИЕ 1", Progress.NEW,
                3,
                Duration.ofMinutes(110),
                LocalDateTime.of(2024, 10, 22, 18, 18));

        taskManager.addSubtask(subTask1);

        SubTask subTask2 = new SubTask(null,"ПОДЗАДАЧА 2", "Subtask ОПИСАНИЕ 2", Progress.NEW,
                4,
                Duration.ofMinutes(110),
                LocalDateTime.of(2024, 10, 29, 18, 18));

        taskManager.addSubtask(subTask2);

        System.out.println(taskManager.getPrioritizedTasks());
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllSubTasks());
        System.out.println(taskManager.getAllEpics());
//        taskManager.deleteAllTasks();
        System.out.println(taskManager.getAllTasks());

    }
}