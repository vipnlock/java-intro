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

/**
 * Byte streams: 8-bit data: InputStream, OutputStream
 * Character streams: 16-bit text characters (UTF-16): Reader, Writer
 */
public class InputOutputTest {

    private static final String BINARY_FILE = "target/test-classes/ch/study/hsr/lecture12/InputOutputTest.class";
    private static final String UTF_8_FILE = "src/test/resources/hsr/lecture12/test_utf8.txt";
    private static final String UTF_16_FILE = "src/test/resources/hsr/lecture12/test_utf16.txt";

    @Test
    @DisplayName("Byte stream read: Binary file input")
    void fileInput() throws IOException {
        // try-with-resources - auto-close, since 1.7
        try (var in = new FileInputStream(BINARY_FILE)) {
            int byteNumber = 0;

            int value;
            // read:
            // - the next byte [0, 255], unsigned kodiert
            // - -1 - spezielle Semantik, EOF, sentinel value
            while ((value = in.read()) >= 0) {
                // byte in memory is signed: [-128, 127]
                byte b = (byte) value;  // least significant 8 bits -> to signed byte, no Datenverlust

                if (byteNumber % 16 == 0) {
                    System.out.printf("%n%04X:", byteNumber);
                }
                System.out.printf(" %02X", b);  // work with b

                byteNumber++;
            }
        }
    }

    @Test
    @DisplayName("Byte stream write: Binary file output")
    void fileOutput() throws IOException {
        try (var out = new FileOutputStream("target/test.bin")) {
            for (int i = 0; i < 1000; i++) {
                // integer instead of byte: no cast on file copying
                out.write(i % 256);
            }
        }
    }

    // since 11
    @Test
    @DisplayName("Character stream: File Reader")
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
    @DisplayName("Character stream: Byte Stream File Reader")
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
    @DisplayName("Character stream: File Writer")
    void fileWriter() throws IOException {
        try (var writer = new FileWriter("target/test.txt", true)) {
            writer.write("Hello!");
            writer.write('\n');
        }
    }

    // buffering: BufferedReader
    @Test
    @DisplayName("Character stream: Buffered reader")
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
