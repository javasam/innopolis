package lesson_16;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadPool implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(ThreadPool.class.getName());
    ReaderFromURL readerFromURL = new ReaderFromURL();
    PriorityBlockingQueue<String> resultList = new PriorityBlockingQueue<>();
    private String url;
    private String[] words;

    public ThreadPool() {
    }

    public ThreadPool(String url, String[] words, PriorityBlockingQueue<String> resultList) {
        this.url = url;
        this.words = words;
        this.resultList = resultList;
    }

    @Override
    public void run() {
        readerFromURL.readFromURLToArray(url, words, resultList);
        LOGGER.log(Level.INFO, "Thread search: " + Thread.currentThread().getName());
        LOGGER.log(Level.INFO, () -> "Matches count: " + readerFromURL.count);
    }
}
