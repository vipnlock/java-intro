package ch.study.hsr.lecture12;

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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class InputOutputTest {

    public static final String UTF_8_FILE = "src/test/resources/lecture12/test_utf8.txt";
    public static final String UTF_16_FILE = "src/test/resources/lecture12/test_utf16.txt";

    @Test
    @DisplayName("Binary file input")
    void fileInput() throws IOException {
        // try-with-resources - auto-close, since 1.7
        try (var in = new FileInputStream("target/test-classes/ch/hsr/lecture12/InputOutputTest.class")) {
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

    @Test
    @DisplayName("Binary file output")
    void fileOutput() throws IOException {
        try (var out = new FileOutputStream("target/test.bin")) {
            for (int i = 0; i < 1000; i++) {
                out.write(i % 256);         // integer instead of byte: no cast on file copying
            }
        }
    }

    // since 11
    @Test
    @DisplayName("File Reader")
    void fileReader() throws IOException {
        try (var reader = new FileReader(UTF_16_FILE, Charset.forName("UTF-16"))) { // system dependent character set
            System.out.println("Default encoding: " + reader.getEncoding());

            int value;
            while ((value = reader.read()) >= 0) {  // -1 = EOF
                char c = (char) value;  // 16-bit character, unsigned

                System.out.print(c);  // use character
            }
        }
    }

    // older java versions: bridge class: InputStreamReader <- FileInputStream
    @Test
    @DisplayName("Byte Stream File Reader")
    void byteStreamFileReader() throws IOException {
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

    @Test
    @DisplayName("File Writer")
    void fileWriter() throws IOException {
        try (var writer = new FileWriter("target/test.txt", true)) {
            writer.write("Hello!");
            writer.write('\n');
        }
    }

    // buffering: BufferedReader
    @Test
    @DisplayName("Buffered reader")
    void linewiseRead() throws IOException {
        try (var reader = new BufferedReader(new FileReader(UTF_16_FILE, Charset.forName("UTF-16")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    // since java 8
    @Test
    @DisplayName("Read all")
    void ReadAll() throws IOException {
        for (String line : Files.readAllLines(Path.of(UTF_16_FILE), Charset.forName("UTF-16"))) {
            System.out.println(line);
        }
    }

    // since 11
    @Test
    @DisplayName("Read all with Stream API")
    void ReadAllWithStreamApi() throws IOException {
        Files.lines(Path.of(UTF_16_FILE), Charset.forName("UTF-16"))
             .forEach(System.out::println);
    }

}
