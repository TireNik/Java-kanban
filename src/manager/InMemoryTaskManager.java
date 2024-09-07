package manager;

import tasks.Epic;
import tasks.Progress;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    private Map<Integer, Task> taskMap = new HashMap<>();
    private Map<Integer, SubTask> subTaskMap = new HashMap<>();
    private Map<Integer, Epic> epicMap = new HashMap<>();
    private int idCounter = 0;

    private final HistoryManager historyManager;

    public InMemoryTaskManager() {
        this.historyManager = Managers.getDefaultHistory();
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public Task addTask(Task task) {
        int id = ++idCounter;
        task.setId(id);
        taskMap.put(id, task);
        return task;
    }

    @Override
    public Task updateTask(Task updateTask) {
        int id = updateTask.getId();
        if (taskMap.containsKey(id)) {
            taskMap.put(id, updateTask);
            return updateTask;
        }
        return null;
    }

    @Override
    public Boolean deleteTaskById(Integer id) {
        if (taskMap.containsKey(id)) {
            Task removeTask = taskMap.remove(id);
            historyManager.remove(id);
            return removeTask != null;
        }
        return false;
    }

    @Override
    public Task getTaskById(Integer id) {
        Task task = taskMap.get(id);
        if (task != null) {
            historyManager.add(task);
            return task;
        }
        return null;
    }

    @Override
    public Epic addEpic(Epic epic) {
        int id = ++idCounter;
        epic.setId(id);
        epicMap.put(id, epic);
        return epic;
    }

    @Override
    public Epic updateEpic(Epic epic) {
        int id = epic.getId();
        if (epicMap.containsKey(id)) {
            Epic epic1 = epicMap.get(id);
            epic1.setName(epic.getName());
            epic1.setDescription(epic.getDescription());
            return epic;
        }
        return null;
    }

    @Override
    public Boolean deleteEpicById(Integer id) {
        Epic epic = epicMap.get(id);
        if (epic != null) {
            List<Integer> subTaskIds = epic.getSubtaskIds();
            for (Integer subTaskId : subTaskIds) {
                subTaskMap.remove(subTaskId);
                historyManager.remove(subTaskId);

            }
            epicMap.remove(id);
            historyManager.remove(id);

            return true;
        }
        return false;
    }

    @Override
    public Epic getEpicById(Integer id) {
        Epic epic = epicMap.get(id);
        if (epic != null) {
            historyManager.add(epic);
            return epic;
        }
        return null;
    }

    @Override
    public SubTask addSubtask(SubTask subTask) {
        Epic epic = epicMap.get(subTask.getEpicId());
        if (epic != null) {
            int id = ++idCounter;
            subTask.setId(id);
            subTaskMap.put(id, subTask);
            epic.addSubtaskId(id);
            updateEpicStatus(epic);

            return subTask;
        }
        return null;
    }

    @Override
    public SubTask updateSubtask(SubTask subTask) {
        int id = subTask.getId();
        if (subTaskMap.containsKey(id)) {
            SubTask currentSubtaskId = subTaskMap.get(id);
            if (currentSubtaskId.getEpicId().equals(subTask.getEpicId())) {
                subTaskMap.put(id, subTask);
                Epic epic = epicMap.get(subTask.getEpicId());
                if (epic != null) {
                    updateEpicStatus(epic);
                }
                return subTask;
            }
        }
        return null;
    }

    @Override
    public Boolean deleteSubtaskForId(Integer id) {
        SubTask subTask = subTaskMap.remove(id);
        if (subTask != null) {
            Epic epic = epicMap.get(subTask.getEpicId());
            if (epic != null) {
                historyManager.remove(id);
                epic.removeSubtaskId(id);
                updateEpicStatus(epic);
            }
            return true;
        }
        return false;
    }

    @Override
    public SubTask getSubtaskById(Integer id) {
        SubTask subTask = subTaskMap.get(id);
        if (subTask != null) {
            historyManager.add(subTask);
            return subTask;
        }
        return null;
    }

    @Override
    public ArrayList<SubTask> getSubtasksByEpic(Integer epicId) {
        ArrayList<SubTask> subTasks = new ArrayList<>();
        Epic epic = epicMap.get(epicId);
        if (epic != null) {
            List<Integer> subtaskIds = epic.getSubtaskIds();
            for (Integer subtaskId : subtaskIds) {
                SubTask subTask = subTaskMap.get(subtaskId);
                historyManager.add(subTask);
                if (subTask != null) {
                    subTasks.add(subTask);
                }
            }
        }

        return subTasks;
    }

    @Override
    public void updateEpicStatus(Epic epic) {
        List<SubTask> subTasks = getSubtasksByEpic(epic.getId());
        boolean isAllNew = true;
        boolean isAllDone = true;

        for (SubTask subTask : subTasks) {
            if (subTask.getStatus() != Progress.NEW) {
                isAllNew = false;
            }
            if (subTask.getStatus() != Progress.DONE) {
                isAllDone = false;
            }
        }

        if (subTasks.isEmpty() || isAllNew) {
            epic.setStatus(Progress.NEW);
        } else if (isAllDone) {
            epic.setStatus(Progress.DONE);
        } else {
            epic.setStatus(Progress.IN_PROGRESS);
        }
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(taskMap.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epicMap.values());
    }

    @Override
    public List<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTaskMap.values());
    }

    @Override
    public void deleteAllTasks() {
        List<Integer> taskIds = new ArrayList<>(taskMap.keySet());
        taskMap.clear();
        for (Integer id : taskIds) {
            historyManager.remove(id);
        }
    }

    @Override
    public void deleteAllEpics() {
        List<Integer> epicIds = new ArrayList<>(epicMap.keySet());
        List<Integer> subtaskIds = new ArrayList<>(subTaskMap.keySet());

        epicMap.clear();
        subTaskMap.clear();

        for (Integer id : epicIds) {
            historyManager.remove(id);
        }

        for (Integer id : subtaskIds) {
            historyManager.remove(id);
        }
    }

    @Override
    public void deleteAllSubTasks() {
        List<Integer> subTaskIds = new ArrayList<>(subTaskMap.keySet());

        for (Integer id : subTaskIds) {
            historyManager.remove(id);
        }

        subTaskMap.clear();

        for (Epic epic : epicMap.values()) {
            epic.clearSubtaskId();
            updateEpicStatus(epic);
        }
    }

}