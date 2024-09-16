import manager.FileBackedTaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Progress;
import tasks.SubTask;
import tasks.Task;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {

    private FileBackedTaskManager taskManager;
    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = File.createTempFile("tasks", ".csv");
        tempFile.deleteOnExit();
        taskManager = new FileBackedTaskManager(tempFile);
    }

    @Test
    void shouldSaveAndLoadEmptyFile() {
        taskManager.save();
        FileBackedTaskManager loadedManager = new FileBackedTaskManager(tempFile);

        assertTrue(loadedManager.getAllTasks().isEmpty(), "Должен быть пустым");
        assertTrue(loadedManager.getAllEpics().isEmpty(), "Lолжен быть пустым");
        assertTrue(loadedManager.getAllSubTasks().isEmpty(), "Должен быть пустым");
    }

    @Test
    void shouldSaveAndLoadMultipleTasks() {
        Task task = new Task(1, "Task1", "Description Task1", Progress.NEW);
        Epic epic = new Epic(2, "Epic1", "Description pic1");
        SubTask subTask = new SubTask(3, "SubTask1", "Description SubTask1", Progress.NEW, epic.getId());

        taskManager.addTask(task);
        taskManager.addEpic(epic);
        taskManager.addSubtask(subTask);

        FileBackedTaskManager loadedManager = new FileBackedTaskManager(tempFile);

        assertEquals(1, loadedManager.getAllTasks().size(), "Должна быть одна задача");
        assertEquals(1, loadedManager.getAllEpics().size(), "Должен быть один эпик");
        assertEquals(1, loadedManager.getAllSubTasks().size(), "Должна быть одна подзадача");
    }
}