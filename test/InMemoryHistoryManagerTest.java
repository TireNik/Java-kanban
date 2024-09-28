import manager.InMemoryHistoryManager;
import tasks.Progress;
import tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class InMemoryHistoryManagerTest {

    InMemoryHistoryManager historyManager;

    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void addTaskToHistoryTest() {
        Task task = new Task(1, "Task 1", "Description 1", Progress.NEW);
        historyManager.add(task);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "Должна содержать одну задачу");
        assertEquals(task, history.get(0), "Должна ровняться");
    }

    @Test
    void addMultipleTasksToHistoryTest() {
        Task task1 = new Task(1, "Task 1", "Description 1", Progress.NEW);
        Task task2 = new Task(2, "Task 2", "Description 2", Progress.NEW);

        historyManager.add(task1);
        historyManager.add(task2);

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "Должна содержать две задачи");
        assertEquals(task1, history.get(0), "Должна быть task1");
        assertEquals(task2, history.get(1), "Должна быть task2");
    }

    @Test
    void removeTaskFromHistoryTest() {
        Task task1 = new Task(1, "Task 1", "Description 1", Progress.NEW);
        Task task2 = new Task(2, "Task 2", "Description 2", Progress.NEW);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.remove(1);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "Должна содержать одну задачу после удаления");
        assertEquals(task2, history.get(0), "Должна быть task2");
    }

    @Test
    void removeNonExistentTaskFromHistoryTest() {
        Task task1 = new Task(1, "Task 1", "Description 1", Progress.NEW);

        historyManager.add(task1);
        historyManager.remove(999);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "Должна содержать одну задачу после попытки удалить");
        assertEquals(task1, history.get(0), "Должна остаться без изменений");
    }

    @Test
    void addDuplicateTaskToHistoryTest() {
        Task task = new Task(1, "Task 1", "Description 1", Progress.NEW);

        historyManager.add(task);
        historyManager.add(task);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "Дубликаты не должны добавляться");
    }

    @Test
    void shouldRemoveTaskFromHistoryMiddle() {
        Task task1 = new Task(1, "Task 1", "Description 1", Progress.NEW);
        Task task2 = new Task(2, "Task 2", "Description 2", Progress.NEW);
        Task task3 = new Task(3, "Task 3", "Description 3", Progress.NEW);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        historyManager.remove(2);

        assertEquals(2, historyManager.getHistory().size(), "История должна содержать две задачи");
        assertEquals(task1, historyManager.getHistory().get(0), "Первая задача должна быть task1");
        assertEquals(task3, historyManager.getHistory().get(1), "Вторая задача должна быть task3");
    }

    @Test
    void shouldRemoveTaskFromHistoryEnd() {
        Task task1 = new Task(1, "Task 1", "Description 1", Progress.NEW);
        Task task2 = new Task(2, "Task 2", "Description 2", Progress.NEW);
        Task task3 = new Task(3, "Task 3", "Description 3", Progress.NEW);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        historyManager.remove(3);

        assertEquals(2, historyManager.getHistory().size(), "История должна содержать две задачи");
        assertEquals(task1, historyManager.getHistory().get(0), "Первая задача должна быть task1");
        assertEquals(task2, historyManager.getHistory().get(1), "Вторая задача должна быть task2");
    }
}