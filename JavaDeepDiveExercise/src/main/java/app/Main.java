package app;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
         TaskList<Task> taskList = new TaskList<>();

         LocalDate currentDate = LocalDate.of(2024,8,23);
         Task buyMilk = new Task("Milk", "Buy Whole Milk in Netto", currentDate);
         GardenTask mowLawn = new GardenTask("Lawn", "Mow the lawn", currentDate, "In front of the house");

        LocalDate dayAfterCurrentDate = LocalDate.of(2024,9,25);
        Task readBook = new Task("Book", "Read a new book", dayAfterCurrentDate);

        LocalDate dayBeforeCurrentDate = LocalDate.of(2024,7,13);
        Task discardBook = new Task("Discard book", "Throw out old books", dayBeforeCurrentDate);
        GardenTask collectHerbs = new GardenTask("Herbs", "Collect some herbs", dayBeforeCurrentDate, "In the backyard");

        //Added all task to list
        taskList.addTask(buyMilk);
        taskList.addTask(mowLawn);
        taskList.addTask(readBook);
        taskList.addTask(discardBook);
        taskList.addTask(collectHerbs);

        //Filter taskList by title
        List<Task> filterByTitle = taskList.filteredTasksByTitle("Book");
        System.out.println("Tasks filtered by title: ");
        taskList.printTasks(filterByTitle);

        //Filter taskList by description
        List<Task> filteredByDescription = taskList.filteredTasksByDescription("Read");
        System.out.println("Tasks filtered by description: ");
        taskList.printTasks(filteredByDescription);

        //Sort by Due date
        List<Task> sortedByDueDate = taskList.sortTasksByDueDate();
        System.out.println("Sorted by due date: ");
        taskList.printTasks(sortedByDueDate);

        //Get task due today
        List<Task> tasksDueToday = taskList.getTasksDueToday(currentDate);
        System.out.println("Tasks due today: ");
        taskList.printTasks(tasksDueToday);

        //Get overdue tasks
        LocalDate date = LocalDate.of(2024,9,23);
        List<Task> overdueTasks = taskList.getOverdueTasks(date);
        System.out.println("Overdue tasks: ");
        taskList.printTasks(overdueTasks);
    }
}