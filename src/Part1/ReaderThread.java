package Part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReaderThread extends Thread {
    String name;

    public ReaderThread(String name) {
        this.name = name;
    }

    public static List<Integer> getToSum() {
        return toSum;
    }

    private  static  List<Integer> toSum = new ArrayList();

    @Override
    public void run() {
        int linescounter = 1;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(name));
            while (reader.readLine() != null) {
                linescounter++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        toSum.add(linescounter);
    }
}


