package lesson_16;

import java.io.*;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

//Input Sources[]
public class ReaderFromURL {
    private static final Logger LOGGER = Logger.getLogger(ReaderFromURL.class.getName());
    private static Pattern pattern = Pattern.compile("\\.\\.\\.|!|\\.|\\?");
    int count;
    InputStream inputStream;

    public ReaderFromURL() {
    }

    public void readFromURLToArray(String url, String[] words, PriorityBlockingQueue<String> resultList) {
        try (InputStream is = getInputStream(url);
             InputStreamReader ins = new InputStreamReader(is);
             BufferedReader reader = new BufferedReader(ins)) {
            String sentence;
            String[] sentences;
            while ((sentence = reader.readLine()) != null) {
                sentences = pattern.split(sentence);
                for (String word : words) {                                 // ищем среди предложений слово из массива words
                    for (String string : sentences) {
                        if (string.contains(word)) {                        // если находим слово в строке, то добавляем
                            resultList.add(string);                         // его в коллекцию
                            count++;
                        }
                    }
                }
            }
        } catch (UnknownHostException e) {
            LOGGER.log(Level.INFO, "No connection to URL: " + url);
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.INFO, "File not found: " + url);
        } catch (IOException e) {
            LOGGER.log(Level.INFO, e.toString());
        }
    }

    private InputStream getInputStream(String url) throws IOException {
        if (url.contains("http") || (url.contains("ftp"))) { //если ссылка WEB, то URL openStream
            inputStream = new URL(url).openStream();
        } else {                                             // иначе FileInputStream
            inputStream = new FileInputStream(url);
        }
        return inputStream;
    }
}