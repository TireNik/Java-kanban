import manager.Managers;
import manager.TaskManager;
import tasks.Progress;
import tasks.Task;

import java.io.File;

public class Main {

    public static void main(String[] args) {


        File file1 = new File("/Users/sviridovnikita/Desktop/tasks1.csv");
        File file = new File("/Users/sviridovnikita/Desktop/tasks.csv");
        TaskManager taskManager = Managers.getFileBackedTaskManager(file1);

        Task task1 = new Task(null, "1 Задача", "1 описание", Progress.NEW);
        Task task2 = new Task(null, "2 Задача", "2 описание", Progress.NEW);

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        System.out.println(taskManager.getAllTasks());


//        Epic epic1 = new Epic(3, "1 Эпик", "1 эпик описание");
//        Epic epic2 = new Epic(4, "2 Эпик", "2 эпик описание");
//
//        taskManager.addEpic(epic1);
//        taskManager.addEpic(epic2);
//
//        SubTask subTask1  = new SubTask(5, "1_1 подзадача", "1_1 описание подзадачи", Progress.DONE, 3);
//        SubTask subTask2 = new SubTask(6, "1_2 подзадача", "1_2 описание подзадачи", Progress.DONE, 3);
//
//        taskManager.addSubtask(subTask1);
//        taskManager.addSubtask(subTask2);
//
//        SubTask subTask3 = new SubTask(7, "2_1 подзадача", "2_1 описание подхадачи", Progress.IN_PROGRESS, 4);
//
//        taskManager.addSubtask(subTask3);
//
//        System.out.println(taskManager.getAllTasks());
//        System.out.println(taskManager.getAllEpics());
//        System.out.println(taskManager.getAllSubTasks());
//
//        System.out.println("___________");
//
//        System.out.println(taskManager.getSubtaskById(7));
//        System.out.println(taskManager.getEpicById(3));
//        taskManager.getSubtasksByEpic(3);
//        System.out.println("История просмотров: " + taskManager.getHistory());
//        System.out.println(taskManager.getTaskById(1));
//
//        System.out.println("___________");
//        System.out.println("___________");
//        System.out.println(taskManager.getTaskById(1));
//        System.out.println(taskManager.getTaskById(2));
//        System.out.println(taskManager.getEpicById(3));
//        System.out.println(taskManager.getSubtaskById(7));
//        System.out.println(taskManager.getHistory());

//        taskManager.deleteTaskById(1);
//        taskManager.deleteTaskById(2);
//        taskManager.deleteEpicById(3);
//        taskManager.deleteSubtaskForId(7);
//
//
//        System.out.println(taskManager.getHistory());
//
//        System.out.println("___________");
//        System.out.println("___________");
//
//
//        Task updateTask1 = new Task(1, "1 Задача", "1 описание", Progress.IN_PROGRESS);
//        Task updateTask2 = new Task(2, "2 Задача", "2 описание", Progress.IN_PROGRESS);
//
//        taskManager.updateTask(updateTask1);
//        taskManager.updateTask(updateTask2);
//
//        Epic updateEpic1 = new Epic(3, "1 Эпик", "1 Изменил описание");
//        Epic updateEpic2 = new Epic(4, "2 Эпик", "2 Изменил описание");
//
//        taskManager.updateEpic(updateEpic1);
//        taskManager.updateEpic(updateEpic2);
//
//        SubTask updateSubTask1 = new SubTask(5, "1_1 подзадача", "1_1 описание подзадачи", Progress.NEW, 3);
//        SubTask updateSubTask2 = new SubTask(6, "1_2 подзадача", "1_2 описание подзадачи", Progress.NEW, 3);
//
//        taskManager.updateSubtask(updateSubTask1);
//        taskManager.updateSubtask(updateSubTask2);
//
//        SubTask updateSubTask3 = new SubTask(7, "2_1 должна удалиться подзадача", "2_1 описание подхадачи", Progress.DONE, 4);

//        taskManager.updateSubtask(updateSubTask3);
//
//        System.out.println(taskManager.getAllTasks());
//        System.out.println(taskManager.getAllEpics());
//        System.out.println(taskManager.getAllSubTasks());
//        System.out.println("________");
//
//        System.out.println(taskManager.getSubtasksByEpic(3));
//
//        System.out.println(taskManager.deleteTaskById(1));
//        System.out.println(taskManager.deleteEpicById(4));
//        System.out.println(taskManager.deleteSubtaskForId(5));
//        System.out.println(taskManager.deleteSubtaskForId(7));
//
//        SubTask updateSubTask4 = new SubTask(5, "333", "1_1 описание подзадачи", Progress.NEW, 4);
//        taskManager.addSubtask(updateSubTask4);


//        System.out.println(taskManager.getAllEpics());
//        System.out.println(taskManager.getAllSubTasks());

    }
}