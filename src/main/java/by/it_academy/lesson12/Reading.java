package by.it_academy.lesson12;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author Maxim Tereshchenko
 */
class Reading {

    public static void main(String[] args) {
        String path = "src/main/resources/text.txt";

        bytes(path);
        bytesBuffer(path);
        symbols(path);
        symbolsBuffer(path);

        console();
    }

    private static void symbols(String path) {
        try (var reader = new FileReader(path, StandardCharsets.UTF_8)) {
            var read = reader.read();
            while (read != -1) {
                System.out.print((char) read);
                read = reader.read();
            }
            System.out.println();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void symbolsBuffer(String path) {
        try (var reader = new FileReader(path, StandardCharsets.UTF_8)) {
            var buffer = new char[1000];
            var read = -1;
            do {
                read = reader.read(buffer);
                for (int i = 0; i < read; i++) {
                    System.out.print(buffer[i]);
                }
            } while (read != -1);
            System.out.println();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void bytesBuffer(String path) {
        try (var stream = new FileInputStream(path)) {
            var buffer = new byte[1000];
            var read = -1;
            do {
                read = stream.read(buffer);
                for (int i = 0; i < read; i++) {
                    System.out.print((char) buffer[i]);
                }
            } while (read != -1);
            System.out.println();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void bytes(String path) {
        try (var stream = new FileInputStream(path)) {
            var read = stream.read();
            while (read != -1) {
                System.out.print((char) read);
                read = stream.read();
            }
            System.out.println();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void console() {
        try (var reader = new BufferedReader(new InputStreamReader(System.in))) {
            var buffer = new char[1000];
            while (true) {
                var read = reader.read(buffer);
                var builder = new StringBuilder();
                for (int i = 0; i < read; i++) {
                    builder.append(buffer[i]);
                }
                if (builder.toString().isBlank()) {
                    break;
                }
                System.out.println(builder);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}