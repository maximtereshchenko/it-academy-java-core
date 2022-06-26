package by.it_academy.lesson12;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

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
        try (FileWriter writer = new FileWriter("symbolsBuffer")) {
            String text = "Hello, world!";
            writer.write(text.toCharArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void symbols() {
        try (FileWriter writer = new FileWriter("symbols")) {
            String text = "Hello, world!";
            for (int i = 0; i < text.length(); i++) {
                writer.write(text.charAt(i));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void bytes() {
        try (FileOutputStream stream = new FileOutputStream("bytes")) {
            String text = "Hello, world!";
            for (byte singleByte : text.getBytes()) {
                stream.write(singleByte);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void bytesBuffer() {
        try (FileOutputStream stream = new FileOutputStream("bytesBuffer")) {
            String text = "Hello, world!";
            stream.write(text.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
