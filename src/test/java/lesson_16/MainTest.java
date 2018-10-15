package lesson_16;

import org.junit.jupiter.api.Test;

import java.io.File;

class MainTest {
    Main mainTest = new Main();

    @Test
    void getFIleNames() {
        String path = new File(".").getAbsolutePath();
        String[] array;
        array = mainTest.getFIleNames(path);
        assert (array.length > 0);
    }
}