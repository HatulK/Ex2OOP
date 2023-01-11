package Part2;

import java.util.Comparator;

public class TypeComparator implements Comparator<Task>{

    /**
     *A comperator.
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return
     */
    @Override
    public int compare(Task o1, Task o2) {
        return (Integer.compare(o1.getTaskType().getPriorityValue(),o2.getTaskType().getPriorityValue()));
    }
}
