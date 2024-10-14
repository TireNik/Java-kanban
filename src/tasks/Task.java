package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {
    private Integer id;
    private String name;
    private String description;
    private Progress status;
    private String duration;
    private String startTime;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    public Task(Integer id, String name, String description, Progress status,
                Duration duration, LocalDateTime startTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = duration.toString();
        this.startTime = startTime.format(formatter);
    }

    public Task(Integer id, String name, String description, Progress status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public LocalDateTime getEndTime() {
        if (startTime != null && duration != null) {
            LocalDateTime start = LocalDateTime.parse(startTime, formatter);
            Duration dur = Duration.parse(duration);

            return start.plus(dur);
        }
        return null;
    }

    public Duration getDuration() {
        return Duration.parse(duration);
    }

    public void setDuration(Duration duration) {
        this.duration = duration.toString();
    }

    public LocalDateTime getStartTime() {
        return LocalDateTime.parse(startTime, formatter);
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime.format(formatter);
    }

    public TypeTask getType() {
        return TypeTask.TASK;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setStatus(Progress status) {
        this.status = status;
    }

    public Progress getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Task task = (Task) object;
        return Objects.equals(id, task.id) && Objects.equals(name, task.name) && Objects.equals(description, task.description) && status == task.status;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(description);
        result = 31 * result + Objects.hashCode(status);
        return result;
    }

    @Override
    public String toString() {
        return getId() + "," + getType() + "," + getName() + "," +
                getStatus() + "," + getDescription() + "," +
                getDuration() + "," + getStartTime() + "," + getEndTime();
    }

}
