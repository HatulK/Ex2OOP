package Part2;


import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;


public class CustomExecutor extends Thread {
    private int currentMax = 0;
    private boolean work = true;
    private final int min = (Runtime.getRuntime().availableProcessors()) / 2;
    private final int max = (Runtime.getRuntime().availableProcessors()) - 1;

    private int openThreads = 0;

    private final FutureTask[] threadArray = new FutureTask[max];

    PriorityBlockingQueue<Task> pqueue = new PriorityBlockingQueue(min, new TypeComparator());

    /**
     *
     */
    public CustomExecutor() {
        for (int i = 0; i < min; i++) {
            threadArray[i] = new FutureTask<>(() -> null);
            Thread thread = new Thread(threadArray[i]);
            thread.start();
            openThreads++;
        }
        this.start();
    }

    /**
     * Sumbits a task to the pool
     * @param callable a task
     * @param taskType task priority
     * @return Future object
     */
    public <V> FutureTask<V> submit(Callable<V> callable, TaskType taskType) {
        Task task = Task.createTask(callable, taskType);
        return submit(task);
    }

    /**
     * Sumbits a task to the pool
     * @param callable a task
     * @return Future object
     */
    public <V> FutureTask<V> submit(Callable<V> callable) {
        Task task = Task.createTask(callable);
        return submit(task);
    }

    /**
     * Sumbits a task to the pool
     * @param task a task
     * @return Future object
     */
    public <V> FutureTask<V> submit(Task<V> task) {
        if (openThreads < max) {
            pqueue.add(task);
//            threadArray[openThreads++] = task.getFutureTask();
        }
        System.out.println("New Task Added");
        if (pqueue.peek() != null) currentMax = pqueue.peek().getTaskType().getPriorityValue();
        return task.getFutureTask();
    }

    /**
     * Returns the current max priority of the tasks
     * @return max priority
     */
    public int getCurrentMax() {
        return currentMax;
    }

    /**
     * Shutdown the pool
     */
    public void gracefullyTerminate() {
        work = false;
        synchronized (pqueue) {
            while (openThreads > min) {
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            while (openThreads != 0) {
                for (int i = 0; i < threadArray.length; i++) {
                    if (threadArray[i] != null && threadArray[i].isDone()) {
                        threadArray[i] = null;
                        openThreads--;
                    }
                }
            }
        }
    }

    /**
     * a run method.
     */
    @Override
    public void run() {
        while (work) {
            if (!pqueue.isEmpty()) {
                Task temp = pqueue.poll();
                for (int i = 0; i < threadArray.length; i++) {
                    if (threadArray[i] != null && threadArray[i].isDone()) {
                        threadArray[i] = temp.getFutureTask();
                        break;
                    } else if (threadArray[i] == null) {
                        threadArray[i] = temp.getFutureTask();
                        openThreads++;
                        break;
                    }
                }
                try {
                    Thread thread = new Thread(temp.getFutureTask());
                    thread.start();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            for (int i = 0; i < threadArray.length; i++) {
                if (openThreads > min) {
                    if (threadArray[i] != null && threadArray[i].isDone()) {
                        threadArray[i] = null;
                        openThreads--;
                    }
                }
            }

        }
    }
}