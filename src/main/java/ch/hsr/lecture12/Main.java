package ch.hsr.lecture12;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static final String UTF_8_FILE = "src/main/resources/lecture12/test_utf8.txt";
    public static final String UTF_16_FILE = "src/main/resources/lecture12/test_utf16.txt";

    public static void main(String[] argv) {
        var main = new Main();

        try {
//            main.test1_fileInput();
//            main.test2_fileOutput();
//            main.test3_fileReader();
//            main.test3_byteStreamFileReader();
//            main.test4_fileWriter();
//            main.test5_LinewiseRead();
//            main.test6_ReadAll();
            main.test7_ReadAllWithStreamApi();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    private void test1_fileInput() throws IOException {
        System.out.println("=== test1_fileInput");

        try (var in = new FileInputStream(
                "target/classes/ch/hsr/lecture12/Main.class")) {    // try-with-resources - auto-close, since 1.7
            int i = 0;

            int value;
            while ((value = in.read()) >= 0) {  // read the next byte [0, 255], unsigned kodiert
                // byte in memory is signed: [-128, 127]
                // -1 - spezielle Semantik, EOF, sentinel value
                byte b = (byte) value;  // least significant 8 bits -> to signed byte, no Datenverlust

                if (i % 16 == 0) {
                    System.out.printf("%n%04X:", i);
                }
                System.out.printf(" %02X", b);  // work with b

                i++;
            }
        }
    }

    private void test2_fileOutput() throws IOException {
        System.out.println("=== test2_fileOutput");

        try (var out = new FileOutputStream("target/test.bin")) {
            for (int i = 0; i < 1000; i++) {
                out.write(i % 256);         // integer instead of byte: no cast on file copying
            }
        }
    }

    // since 11
    private void test3_fileReader() throws IOException {
        System.out.println("=== test3_fileReader");
        try (var reader = new FileReader(UTF_16_FILE,
                Charset.forName("UTF-16"))) { // system dependent character set
            System.out.println("Default encoding: " + reader.getEncoding());

            int value;
            while ((value = reader.read()) >= 0) {  // -1 = EOF
                char c = (char) value;  // 16-bit character, unsigned

                System.out.print(c);  // use character
            }
        }
    }

    // older java versions: bridge class: InputStreamReader <- FileInputStream
    private void test3_byteStreamFileReader() throws IOException {
        System.out.println("=== test3_byteStreamFileReader");

        try (var reader = new InputStreamReader(
                new FileInputStream(UTF_16_FILE), StandardCharsets.UTF_16)) { // system dependent character set
            System.out.println("Default encoding: " + reader.getEncoding());

            int value;
            while ((value = reader.read()) >= 0) {  // -1 = EOF
                char c = (char) value;  // 16-bit character, unsigned

                System.out.print(c);  // use character
            }
        }
    }

    private void test4_fileWriter() throws IOException {
        System.out.println("=== test4_fileWriter");
        try (var writer = new FileWriter("target/test.txt", true)) {
            writer.write("Hello!");
            writer.write('\n');
        }
    }

    // buffering: BufferedReader
    private void test5_LinewiseRead() throws IOException {
        System.out.println("=== test5_LinewiseRead");
        try (var reader = new BufferedReader(new FileReader(UTF_16_FILE, Charset.forName("UTF-16")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    // since java 8
    private void test6_ReadAll() throws IOException {
        System.out.println("=== test6_ReadAll");
        for (String line : Files.readAllLines(Path.of(UTF_16_FILE), Charset.forName("UTF-16"))) {
            System.out.println(line);
        }
    }

    // since 11
    private void test7_ReadAllWithStreamApi() throws IOException {
        System.out.println("=== test7_ReadAllWithStreamApi");

        Files.lines(Path.of(UTF_16_FILE), Charset.forName("UTF-16"))
             .forEach(System.out::println);
    }

}
