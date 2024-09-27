package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtaskIds;
    private Duration duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Epic(Integer id, String name, String description) {
        super(id, name, description, Progress.NEW);
        this.duration = Duration.ZERO;
        this.startTime = null;
        this.endTime = null;
        this.subtaskIds = new ArrayList<>();
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public Duration getDuration() {
        return duration;
    }

    @Override
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Override
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public TypeTask getType() {
        return TypeTask.EPIC;
    }

    public ArrayList<Integer> getSubtaskIds() {
        return new ArrayList<>(subtaskIds);
    }

    public void addSubtaskId(int subtaskId) {
        subtaskIds.add(subtaskId);
    }

    public void removeSubtaskId(Integer subtaskId) {
        subtaskIds.remove(subtaskId);
    }

    public void clearSubtaskId() {
        subtaskIds.clear();
    }

    @Override
    public String toString() {
        return getId() + "," + getType() + "," + getName() + "," +
                getStatus() + "," + getDescription() + "," +
                getDuration() + "," + getStartTime() + "," + getEndTime();
    }
}
