import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import tasks.Epic;
import tasks.Progress;
import tasks.SubTask;
import tasks.Task;
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
    void addAndFindTaskTest() {
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
    void addAndFindEpicTest() {
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
    void addAndFindSubtaskTest() {
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
    void testTaskUpdateIntegrity() {
        Task task = new Task(1, "Task", "Description", Progress.NEW);
        taskManager.addTask(task);

        task.setName("Изменил имя");
        task.setDescription("Изменил описание");
        task.setStatus(Progress.DONE);

        Task updatedTask = taskManager.getTaskById(task.getId());
        assertEquals("Изменил имя", updatedTask.getName(), "Название задачи должно быть обновлено");
        assertEquals("Изменил описание", updatedTask.getDescription(), "Описание задачи должно быть обновлено");
        assertEquals(Progress.DONE, updatedTask.getStatus(), "Статус задачи должен быть обновлен");
    }

    @Test
    void testEpicAndSubtaskIntegrityAfterDeletion() {
        Epic epic = new Epic(1, "Epic", "Description");
        taskManager.addEpic(epic);

        SubTask subTask1 = new SubTask(1, "SubTask1", "Description1", Progress.NEW, epic.getId());
        SubTask subTask2 = new SubTask(2, "SubTask2", "Description2", Progress.NEW, epic.getId());

        taskManager.addSubtask(subTask1);
        taskManager.addSubtask(subTask2);

        taskManager.deleteSubtaskForId(subTask1.getId());

        List<SubTask> remainingSubtasks = taskManager.getSubtasksByEpic(epic.getId());
        assertEquals(1, remainingSubtasks.size(), "Должен содержать 1 оставшуюся подзадачу");
        assertEquals(subTask2.getId(), remainingSubtasks.get(0).getId(), "Должна быть subTask2");

        taskManager.deleteEpicById(epic.getId());

        assertTrue(taskManager.getAllSubTasks().isEmpty(), "Все должны быть удалены");
    }

    @Test
    void managerGetDefaultTest() {
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager, "Не должен быть Null");
    }

    @Test
    void shouldSetEpicStatusNewIfAllSubtasksNew() {
        Epic epic = new Epic(1, "Epic", "Description");
        taskManager.addEpic(epic);

        SubTask subTask1 = new SubTask(2, "Subtask 1", "Description", Progress.NEW, epic.getId());
        SubTask subTask2 = new SubTask(3, "Subtask 2", "Description", Progress.NEW, epic.getId());
        taskManager.addSubtask(subTask1);
        taskManager.addSubtask(subTask2);

        assertEquals(Progress.NEW, epic.getStatus(), "Epic статус должен быть NEW");
    }

    @Test
    void shouldSetEpicStatusDoneIfAllSubtasksDone() {
        Epic epic = new Epic(1, "Epic", "Description");
        taskManager.addEpic(epic);

        SubTask subTask1 = new SubTask(2, "Subtask 1", "Description", Progress.DONE, epic.getId());
        SubTask subTask2 = new SubTask(3, "Subtask 2", "Description", Progress.DONE, epic.getId());
        taskManager.addSubtask(subTask1);
        taskManager.addSubtask(subTask2);

        assertEquals(Progress.DONE, epic.getStatus(), "Epic статус должен быть DONE");
    }

    @Test
    void shouldSetEpicStatusInProgressIfSubtasksNewAndDone() {
        Epic epic = new Epic(1, "Epic", "Description");
        taskManager.addEpic(epic);

        SubTask subTask1 = new SubTask(2, "Subtask 1", "Description", Progress.NEW, epic.getId());
        SubTask subTask2 = new SubTask(3, "Subtask 2", "Description", Progress.DONE, epic.getId());
        taskManager.addSubtask(subTask1);
        taskManager.addSubtask(subTask2);

        assertEquals(Progress.IN_PROGRESS, epic.getStatus(), "Epic статус должен быть IN_PROGRESS");
    }

    @Test
    void shouldSetEpicStatusInProgressIfAnySubtaskInProgress() {
        Epic epic = new Epic(1, "Epic", "Description");
        taskManager.addEpic(epic);

        SubTask subTask1 = new SubTask(2, "Subtask 1", "Description",
                Progress.IN_PROGRESS, epic.getId());
        taskManager.addSubtask(subTask1);

        assertEquals(Progress.IN_PROGRESS, epic.getStatus(), "Epic статус должен быть IN_PROGRESS");
    }

}