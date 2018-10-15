package lesson_16;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;
import java.util.concurrent.PriorityBlockingQueue;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ReaderFromURLTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private ReaderFromURL readerFromURL;

    @BeforeEach
    void setUp() {
        readerFromURL = new ReaderFromURL();
    }

    @Test
    void whenAddNullUrlThenGetNullPointerEx() {
        assertThrows(NullPointerException.class, () ->
                readerFromURL.readFromURLToArray(null, new String[]{"1"}, new PriorityBlockingQueue<>()));
    }

    @Test
    void whenAddNullArrayThenGetFileNotFoundEx() {
        String url = "C:\\temp";
        expectedException.expect(FileNotFoundException.class);
        expectedException.expectMessage("File not found");
        readerFromURL.readFromURLToArray(url, new String[]{}, new PriorityBlockingQueue<>());
    }

    @Test
    void whenAddNullArrayThenGetUnknownHostEx() {
        String url = "https://easyjava.ru/";
        expectedException.expect(FileNotFoundException.class);
        expectedException.expectMessage("No connection to URL");
        readerFromURL.readFromURLToArray(url, new String[]{}, new PriorityBlockingQueue<>());
    }

    @Test
    void whenAddRealUrlAndRealArrayThenGetMatch() {
        String url = "https://ru.wikipedia.org/wiki/Текстовый_файл";
        String[] words = new String[]{"файл"};
        readerFromURL.readFromURLToArray(url, words, new PriorityBlockingQueue<>());
        assert (readerFromURL.count > 0);
    }
}