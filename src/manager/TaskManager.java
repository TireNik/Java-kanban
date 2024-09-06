package manager;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.List;

public interface TaskManager {
    Task addTask(Task task);

    Task updateTask(Task updateTask);

    Boolean deleteTaskById(Integer id);

    Task getTaskById(Integer id);

    Epic addEpic(Epic epic);

    Epic updateEpic(Epic epic);

    Boolean deleteEpicById(Integer id);

    Epic getEpicById(Integer id);

    SubTask addSubtask(SubTask subTask);

    SubTask updateSubtask(SubTask subTask);

    Boolean deleteSubtaskForId(Integer id);

    SubTask getSubtaskById(Integer id);

    List<SubTask> getSubtasksByEpic(Integer epicId);

    void updateEpicStatus(Epic epic);

    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<SubTask> getAllSubTasks();

    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubTasks();

    List<Task> getHistory();

}