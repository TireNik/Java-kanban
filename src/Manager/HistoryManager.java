package Manager;

import Tasks.Task;

import java.util.List;

public interface HistoryManager {
    List<Task> getHistory();

    void addInHistory(Task task);
}
