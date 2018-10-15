package lesson_16;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.PriorityBlockingQueue;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ThreadPoolTest {
    private ThreadPool threadPool;
    private ThreadPool threadPoolEmpty;

    @BeforeEach
    void setUp() {
        String url = "https://ru.wikipedia.org/wiki/Текстовый_файл";
        String[] words = new String[]{"файл"};
        threadPool = new ThreadPool(url, words, new PriorityBlockingQueue<>());
        threadPoolEmpty = new ThreadPool();
    }

    @Test
    void whenRunThreadThenGetThreadIsAlive() {
        threadPool.run();
        assert (Thread.currentThread().isAlive());
    }

    @Test
    void whenRunEmptyThreadThenGetNullPointerEx() {
        assertThrows(NullPointerException.class, () ->
                threadPoolEmpty.run());
    }
}