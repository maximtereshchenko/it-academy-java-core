package by.it_academy.lesson12;

import java.io.*;
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
        try (FileReader reader = new FileReader(path, StandardCharsets.UTF_8)) {
            int read = reader.read();
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
        try (FileReader reader = new FileReader(path, StandardCharsets.UTF_8)) {
            char[] buffer = new char[1000];
            int read;
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
        try (FileInputStream stream = new FileInputStream(path)) {
            byte[] buffer = new byte[1000];
            int read;
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
        try (FileInputStream stream = new FileInputStream(path)) {
            int read = stream.read();
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
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            char[] buffer = new char[1000];
            while (true) {
                int read = reader.read(buffer);
                StringBuilder builder = new StringBuilder();
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
