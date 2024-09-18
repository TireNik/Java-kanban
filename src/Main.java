import manager.Managers;
import manager.TaskManager;
import tasks.Epic;
import tasks.Progress;
import tasks.SubTask;
import tasks.Task;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        File file = new File("/Users/sviridovnikita/IdeaProjects/java-kanban/fileBacked/tasks.csv");
        TaskManager taskManager = Managers.getFileBackedTaskManager(file);

        Task task1 = new Task(null,"ЗАДАЧА 1", "ОПИСАНИЕ 1", Progress.NEW);
        Task task2 = new Task(null,"ЗАДАЧА 2", "ОПИСАНИЕ 2", Progress.NEW);

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        // Добавляем эпик и подзадачи
        Epic epic = new Epic(null,"ЭПИК 1", "ОПИСАНИЕ ЭПИК");
        taskManager.addEpic(epic);

        SubTask subTask1 = new SubTask(null,"ПОДЗАДАЧА 1", "Subtask ОПИСАНИЕ 1", Progress.NEW, epic.getId());
        SubTask subTask2 = new SubTask(null,"ПОДЗАДАЧА 2", "Subtask ОПИСАНИЕ 2", Progress.NEW, epic.getId());

        taskManager.addSubtask(subTask1);
        taskManager.addSubtask(subTask2);

    }
}