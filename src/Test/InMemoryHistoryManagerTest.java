package Test;

import Manager.Managers;
import Manager.TaskManager;
import Tasks.Progress;
import Tasks.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class InMemoryHistoryManagerTest {

    TaskManager taskManager = Managers.getDefault();


    @Test
    void savedOldVercionInHistoryTest() throws CloneNotSupportedException {
        Task task = new Task(1, "Task", "Description", Progress.NEW);
        Task taskUpdate = new Task(1, "Task2", "Description2", Progress.NEW);

        taskManager.addTask(task);
        taskManager.getTaskById(1);

        taskManager.updateTask(taskUpdate);
        taskManager.getTaskById(1);

        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        taskList.add(taskUpdate);

         assertEquals(taskList, taskManager.getHistory(), "Должны совпадать");
    }

    @Test
    void managerGetDefaultTest() {
        assertNotNull(taskManager, "Не должен быть Null");
    }
}