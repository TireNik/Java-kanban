package manager;

import exeptions.ManagerSaveException;
import tasks.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


public class FileBackedTaskManager extends InMemoryTaskManager {

    private File file;
    private static final String HEADER = "id,type,name,status,description,epic,duration,startTime,endTime";

    public FileBackedTaskManager(File file) {
        this.file = file;
        loadFromFile();
    }

    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(HEADER);
            writer.newLine();

            for (Task task : getAllTasks()) {
                writer.write(toString(task));
                writer.newLine();
            }
            for (Epic epic : getAllEpics()) {
                writer.write(toString(epic));
                writer.newLine();
            }
            for (SubTask subTask : getAllSubTasks()) {
                writer.write(toString(subTask));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Данные не сохранены");
        }
    }

    private void loadFromFile() {
        try {
            if (file.exists()) {
                List<String> lines = Files.readAllLines(file.toPath());
                int maxId = 0;

                for (String line : lines) {
                    if (!line.isEmpty() && !line.startsWith("id,")) {
                        Task task = fromString(line);

                        if (task.getType().equals(TypeTask.SUBTASK)) {
                            if (!getSubTaskMap().containsKey(task.getId())) {
                                addSubTaskDirectly((SubTask) task);
                                }
                        } else if (task.getType().equals(TypeTask.EPIC)) {
                            if (!getEpicMap().containsKey(task.getId())) {
                                addEpicDirectly((Epic) task);
                            }
                        } else {
                            if (!getTaskMap().containsKey(task.getId())) {
                                addTaskDirectly(task);
                            }
                        }
                        maxId = Math.max(maxId, task.getId());
                    }
                }
                updateIdCounter(maxId);
                restoreEpicSubtasks();
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка чтения файла: " + e.getMessage());
        }
    }

    private void restoreEpicSubtasks() {
        for (SubTask subTask : getSubTaskMap().values()) {
            Epic epic = getEpicMap().get(subTask.getEpicId());
            if (epic != null) {
                epic.addSubtaskId(subTask.getId());
                updateEpicStatus(epic);
                updateEpicTime(epic);
            }
        }
    }


    private static Task fromString(String value) {
        String[] fields = value.split(",");
        int id = Integer.parseInt(fields[0]);
        TypeTask type = TypeTask.valueOf(fields[1]);
        String name = fields[2];
        Progress status = Progress.valueOf(fields[3]);
        String description = fields[4];

        switch (type) {
            case SUBTASK:
                int epicId = Integer.parseInt(fields[5]);
                return new SubTask(id, name, description, status, epicId);
            case EPIC:
                return new Epic(id, name, description);
            case TASK:
                return new Task(id, name, description, status);
            default:
                throw new IllegalArgumentException("Неизвестный тип: " + type);
        }
    }

    private static String toString(Task task) {
        TypeTask type = task.getType();

        return switch (type) {
            case SUBTASK -> {
                SubTask subTask = (SubTask) task;
                yield subTask.getId() + "," + subTask.getType() + "," + subTask.getName() + "," +
                        subTask.getStatus() + "," + subTask.getDescription() + "," + subTask.getEpicId();
            }
            case EPIC -> {
                Epic epic = (Epic) task;
                yield epic.getId() + "," + epic.getType() + "," + epic.getName() + "," +
                        epic.getStatus() + "," + epic.getDescription() + ",," +
                        epic.getDuration() + "," + epic.getStartTime() + "," + epic.getEndTime();
            }
            case TASK -> task.getId() + "," + task.getType() + "," + task.getName() + "," +
                    task.getStatus() + "," + task.getDescription() + ",," +
                    task.getDuration() + "," + task.getStartTime() + "," + task.getEndTime();
        };
    }

    @Override
    public Task addTask(Task task) {
        Task newTask = super.addTask(task);
        save();
        return newTask;
    }

    @Override
    public SubTask addSubtask(SubTask subTask) {
        SubTask newSubTask = super.addSubtask(subTask);
        save();
        return newSubTask;
    }

    @Override
    public Task updateTask(Task updateTask) {
        Task updatedTask = super.updateTask(updateTask);
        if (updatedTask != null) {
            save();
        }
        return updatedTask;
    }

    @Override
    public SubTask updateSubtask(SubTask subTask) {
        SubTask updatedSubtask = super.updateSubtask(subTask);
        if (updatedSubtask != null) {
            save();
        }
        return updatedSubtask;
    }

    @Override
    public Epic updateEpic(Epic epic) {
        Epic updatedEpic = super.updateEpic(epic);
        if (updatedEpic != null) {
            save();
        }
        return updatedEpic;
    }

    @Override
    public Boolean deleteTaskById(Integer id) {
        Boolean isDeleted = super.deleteTaskById(id);
        if (isDeleted) {
            save();
        }
        return isDeleted;
    }

    @Override
    public Boolean deleteSubtaskForId(Integer id) {
        Boolean isDeleted = super.deleteSubtaskForId(id);
        if (isDeleted) {
            save();
        }
        return isDeleted;
    }

    @Override
    public Boolean deleteEpicById(Integer id) {
        Boolean isDeleted = super.deleteEpicById(id);
        if (isDeleted) {
            save();
        }
        return isDeleted;
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteAllSubTasks() {
        super.deleteAllSubTasks();
        save();
    }

}
