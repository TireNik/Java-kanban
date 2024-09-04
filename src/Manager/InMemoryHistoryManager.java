package Manager;

import Tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private List<Task> historyList = new ArrayList<>();

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(historyList);
    }

    @Override
    public void addInHistory(Task task) {
        if (historyList.size() > 10) {
            historyList.removeFirst();
        }
            historyList.add(task);
    }
}
