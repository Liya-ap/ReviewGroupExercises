package app;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskList <T extends Task>{
    private List<T> taskLists = new ArrayList<>();

    public void addTask(T task) {
        taskLists.add(task);
    }

    public List<T> filteredTasksByTitle(String title) {
        return taskLists.stream().filter(task -> task.getTitle().toLowerCase().contains(title.toLowerCase())).toList();
    }

    public List<T> filteredTasksByDescription(String description) {
        return taskLists.stream().filter(task -> task.getDescription().toLowerCase().contains(description.toLowerCase())).toList();
    }

    public List<T> sortTasksByDueDate() {
        return taskLists.stream().sorted(Comparator.comparing(Task::getDueDate).reversed()).toList();
    }

    public List<T> getTasksDueToday(LocalDate currentDate) {
        return taskLists.stream().filter(task -> task.getDueDate().equals(currentDate)).toList();
    }

    public List<T> getOverdueTasks(LocalDate currentDate) {
        return taskLists.stream().filter(task -> task.getDueDate().isAfter(currentDate)).toList();
    }

    public void printTasks(List<T> tasks) {
        tasks.forEach(System.out::println);
        System.out.println();
    }
}
