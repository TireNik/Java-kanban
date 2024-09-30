package tasks;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task {

    private Integer epicId;

    public SubTask(Integer id, String name, String description, Progress status, Integer epicId) {
        super(id, name, description, status);
        this.epicId = epicId;
    }

    public SubTask(Integer id, String name, String description, Progress status, Integer epicId,
                   Duration duration, LocalDateTime startTime) {
        super(id, name, description, status, duration, startTime);
        this.epicId = epicId;
    }

    @Override
    public TypeTask getType() {
        return TypeTask.SUBTASK;
    }

    public Integer getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {

        return getId() + "," + getType() + "," + getName() + "," +
                getStatus() + "," + getDescription() + "," + getEpicId() + "," +
                getDuration() + "," + getStartTime() + "," + getEndTime();
    }
}
