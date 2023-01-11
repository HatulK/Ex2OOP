package Part2;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class Task<V> implements Callable<V> {
    private TaskType taskType;
    private FutureTask<V> futureTask;
    private Callable callable;

    /**
     * Creates a new task to be executed.
     * @param callable a task
     * @param taskType the tasks priority
     * @return a Task object
     */
    public static <V>Task <V> createTask(Callable<V> callable, TaskType taskType) {
        return new Task(callable,taskType);
    }

    /**
     * Creates a new task to be executed.
     * @param callable a task
     * @return a Task object
     */
    public static <V>Task <V> createTask(Callable<V> callable) {
        return new Task(callable);
    }

    /**
     * A private constructor (we are using Factory design pattern).
     * @param callable a task
     * @param taskType the tasks priority
     */
    private <V>Task(Callable<V> callable, TaskType taskType) {
        this.taskType=taskType;
        this.callable =callable;
        futureTask =new FutureTask<>(this.callable);
        //thread.start();
    }

    /**
     * A private constructor (we are using Factory design pattern).
     * @param callable a task
     */
    private <V>Task(Callable<V> callable) {
        this(callable, TaskType.OTHER);
    }

    /**
     * A call method
     * @return something
     * @throws Exception
     */
    @Override
    public V call() throws Exception {
        Thread thread = new Thread(futureTask);
        thread.start();
        return futureTask.get();
    }

    /**
     * Eqauls method
     * @param o other object
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task<?> task = (Task<?>) o;
        return taskType == task.taskType;
    }

    /**
     * Hashcode function
     * @return hashcode of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(taskType, futureTask, callable);
    }

    /**
     * String representation of the object
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "Task{" +
                "taskType=" + taskType +
                ", futureTask=" + futureTask +
                ", callable=" + callable +
                '}';
    }

    /**
     * Get type of task
     * @return type of task
     */
    public TaskType getTaskType() {
        return taskType;
    }

    /**
     * returns future task object
     * @return future task object
     */
    public FutureTask<V> getFutureTask() {
        return futureTask;
    }
}

