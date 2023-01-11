## Part 1
![part1 diagram.png](part1%20diagram.png)

In this part we wanted to visualize the differences between 3 methods,running without threads,running with threads and running with threadpool.
all the methods doing the same actions but in different ways.
the first method is running the required program without any use of threads, as we can see this is obviously the longest application of the program.
this is because only 1 action is performed at a time.
the second method of running the program is using threads. this is faster than running without threads at all because few actions take place at a time. but still, this method is slower than threadpool.
the last method of running the program is using threadpool. this is the fastest way to do so and can be clearly seen 

## Part 2
![part 2 diagram.png](part%202%20diagram.png)
In this part we upgraded our ThreadPoolExecutor to also consider the tasks priorities in the queue.

We made a new class named Task which is our Factory of tasks and using Callable interface.

Our ThreadPoolExecuter is a custom executer which also holds the current task with the highest priority.

We also passed to the priority queue a comparator via the TypeComparator class.