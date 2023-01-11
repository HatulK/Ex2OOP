package Part1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;

public class ReaderTask implements Callable<Integer> {

    private String name;

    public ReaderTask(String name) {
        this.name = name;
    }

    @Override
    public Integer call() throws Exception {
        int linescounter = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(name));
            while (reader.readLine() != null) {
                linescounter++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linescounter;
    }
}
