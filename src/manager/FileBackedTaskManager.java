package manager;

import tasks.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


public class FileBackedTaskManager extends InMemoryTaskManager {

    private File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
        loadFromFile();
    }

    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

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
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        try {
            if (file.exists()) {
                List<String> lines = Files.readAllLines(file.toPath());

                for (String line : lines) {
                    if (!line.isEmpty() && !line.startsWith("id,")) {
                        Task task = fromString(line);

                        if (!isDuplicate(task)) {
                            if (task instanceof SubTask) {
                                SubTask subTask = (SubTask) task;
                                addSubtask(subTask);
                            } else if (task instanceof Epic) {
                                Epic epic = (Epic) task;
                                addEpic(epic);
                            } else {
                                addTask(task);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    private boolean isDuplicate(Task task) {
        if (task instanceof SubTask) {
            return getSubTaskMap().containsKey(task.getId());
        } else if (task instanceof Epic) {
            return getEpicMap().containsKey(task.getId());
        } else {
            return getTaskMap().containsKey(task.getId());
        }
    }

    public static String toString(Task task) {
        if (task instanceof SubTask) {
            SubTask subTask = (SubTask) task;
            return subTask.getId() + "," + "SUBTASK" + "," + subTask.getName() + "," + subTask.getStatus() + "," + subTask.getDescription() + "," + subTask.getEpicId();
        } else if (task instanceof Epic) {
            Epic epic = (Epic) task;
            return epic.getId() + "," + "EPIC" + "," + epic.getName() + "," + epic.getStatus() + "," + epic.getDescription();
        } else {
            return task.getId() + "," + "TASK" + "," + task.getName() + "," + task.getStatus() + "," + task.getDescription();
        }
    }

    public static Task fromString(String value) {
        String[] fields = value.split(",");
        int id = Integer.parseInt(fields[0]);
        String type = fields[1];
        String name = fields[2];
        Progress status = Progress.valueOf(fields[3]);
        String description = fields[4];

        switch (type) {
            case "SUBTASK":
                int epicId = Integer.parseInt(fields[5]);
                return new SubTask(id, name, description, status, epicId);
            case "EPIC":
                return new Epic(id, name, description);
            case "TASK":
                return new Task(id, name, description, status);
            default:
                throw new IllegalArgumentException("Unknown task type: " + type);
        }
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
}
