package lesson_16;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final String PATH_TO_RESULT_FILE = "C:\\projects\\inno\\src\\lesson_16\\res.txt";
    private static final String PATH_TO_TEST_FILES_FOLDER = "d:/temp/testset1/";


    public static String[] getFIleNames(String path) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        List<String> results = new ArrayList<>();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                results.add(path + listOfFiles[i].getName());
            }
        }
        return results.toArray(new String[0]);
    }

    public static void main(String[] args) {
        String[] urls = getFIleNames(PATH_TO_TEST_FILES_FOLDER);
        String[] words = {"da", "ae", "did"};
        PriorityBlockingQueue<String> resultList = new PriorityBlockingQueue<>();
        List<Future<?>> futures = new ArrayList<>();

        ExecutorService service =
                Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1); //create worker threads in Thread Pool

        long time = System.currentTimeMillis();
        for (String url : urls) {
            futures.add(service.submit(new ThreadPool(url, words, resultList)));
        }
        service.shutdown();
        Thread thread = new Thread(() -> {
            String path = PATH_TO_RESULT_FILE;
            File file;
            try (FileWriter fileWr = new FileWriter(path);
                 BufferedWriter bw = new BufferedWriter(fileWr)) {
                file = new File(path);
                if (!file.exists()) {
                    file.createNewFile();
                }
                while (!service.isTerminated()) {
                    String take = resultList.take();
                    bw.write(take);
                    bw.write(System.lineSeparator());
                }
            } catch (IOException | InterruptedException ex) {
                LOGGER.log(Level.INFO, ex.toString()); //выводит Interrupted Exception так как поток прерывается вручную.
            }
        });
        thread.start();
        for (Future future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                LOGGER.log(Level.INFO, e.toString() + " in futures");
            }
        }
        LOGGER.log(Level.INFO, "Written to file: " + PATH_TO_RESULT_FILE);
        LOGGER.log(Level.INFO, () -> "Passed time: " + (System.currentTimeMillis() - time));
        thread.interrupt();
    }
}
