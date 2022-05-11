package by.it_academy.lesson12;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Maxim Tereshchenko
 */
class Writing {

    public static void main(String[] args) {
        bytes();
        bytesBuffer();
        symbols();
        symbolsBuffer();
    }

    private static void symbolsBuffer() {
        try (var writer = new FileWriter("symbolsBuffer")) {
            var text = "Hello, world!";
            var chars = new char[text.length()];
            for (int i = 0; i < text.length(); i++) {
                chars[i] = text.charAt(i);
            }
            writer.write(chars);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void symbols() {
        try (var writer = new FileWriter("symbols")) {
            writer.write("Hello, world!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void bytes() {
        try (var stream = new FileOutputStream("bytes")) {
            var text = "Hello, world!";
            for (int i = 0; i < text.length(); i++) {
                stream.write(text.charAt(i));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void bytesBuffer() {
        try (var stream = new FileOutputStream("bytesBuffer")) {
            var text = "Hello, world!";
            stream.write(text.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
