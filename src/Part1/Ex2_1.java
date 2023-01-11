package Part1;

import java.io.*;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Ex2_1 {

    public static String[] createTextFiles(int n, int seed, int bound) {
        String[] textArray = new String[n];
        for (int i = 0; i < n; i++) {
            String name = "file_" + (i + 1);
            textArray[i] = name;
            FileWriter writer = null;
            Random rand = new Random(seed);
            int random = rand.nextInt(bound);
            try {
                writer = new FileWriter(name, true);
                for (int j = 0; j < random; j++) {
                    writer.write("Amit Kabalo :D \n");
                }
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return textArray;
    }


    public static int getNumOfLines(String[] fileNames) {
        int linescounter = 0;
        for (int i = 0; i < fileNames.length; i++) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileNames[i]));
                int character;
                while (reader.readLine() != null) {
                    linescounter++;
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return linescounter;
    }

    public static int getNumOfLinesThreads(String[] fileNames) {
        int total = 0;
        for (int i = 0; i < fileNames.length; i++) {
            ReaderThread thread = new ReaderThread(fileNames[i]);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for (int num : ReaderThread.getToSum()) {
            total += num;
        }
        return total;
    }

    public static int getNumOfLinesThreadPool(String[] fileNames) {
        int total = 0;
        ExecutorService threadPool = Executors.newFixedThreadPool(fileNames.length);
        Future<Integer>[] futureResult = new Future[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            futureResult[i] = threadPool.submit(new ReaderTask(fileNames[i]));
        }

        for (Future<Integer> future : futureResult) {
            try {
                total += future.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        threadPool.shutdown();
        return total;
    }

    public static void main(String[] args) {
        String[] arr = createTextFiles(1000, 5, 99999);
        long start = System.currentTimeMillis();
        getNumOfLines(arr);
        long end = System.currentTimeMillis();
        System.out.println("Using without thread(s) : " + (end - start) + "ms");
        start = System.currentTimeMillis();
        getNumOfLinesThreads(arr);
        end = System.currentTimeMillis();
        System.out.println("Using threads :  " + (end - start) + "ms");
        start = System.currentTimeMillis();
        getNumOfLinesThreadPool(arr);
        end = System.currentTimeMillis();
        System.out.println("Using threadpool :  " + (end - start) + "ms");

    }

}
