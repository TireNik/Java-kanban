package Manager;

import Tasks.Epic;
import Tasks.SubTask;
import Tasks.Task;

import java.util.List;

public interface TaskManager {
    Task addTask(Task task);

    Task updateTask(Task updateTask);

    Boolean deleteTaskById(Integer id);

    Task getTaskById(Integer id) throws CloneNotSupportedException;

    Epic addEpic(Epic epic);

    Epic updateEpic(Epic epic);

    Boolean deleteEpicById(Integer id);

    Epic getEpicById(Integer id) throws CloneNotSupportedException;

    SubTask addSubtask(SubTask subTask);

    SubTask updateSubtask(SubTask subTask);

    Boolean deleteSubtaskForId(Integer id);

    SubTask getSubtaskById(Integer id) throws CloneNotSupportedException;

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
