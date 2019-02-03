package ch.study.hsr.lecture12;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Pattern;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Regular Expressions - Ausdrücke, die Grammatiken in sogenannten regulären Sprachen beschreiben.
 * - keine Rekursion ist abbildbar (Klammerungen, Schachtelungen - da brauchen wir Stack, als Gedächtnis der Tiefe) - Kontext-freie Sprachen
 *
 * 1{2,5} - Min bis Max
 * 1{2,} - Min bis beliebig
 * 1{3} - exakte Anzahl
 *
 * reserved characters: () [] {} * + ? | \
 * -> "\\*"
 *
 * () - capturing group
 * (?<name>) - named capturing group
 */
class RegexpTest {

    private static String GRAMMAR = "([0-1]?[0-9]|2[0-3]):[0-5][0-9]";

    @ParameterizedTest
    @CsvSource({
        "12:12, true",
        "22:22, true",
        "1:11, true",
        "2:22, true",
        "3:33, true",
        "9:51, true",
        "30:00, false",
        "9:60, false"
    })
    void timeRegex(String inputStr, boolean isMatchExpected) {
        assertEquals(isMatchExpected, Pattern.matches(GRAMMAR, inputStr));
    }

    @Test
    @DisplayName("Block comment: Schachtelung ist eine Rekursion")
    void blockComment() {
        // Schachtelung ist eine Rekursion
        assertTrue(Pattern.matches("/\\*(.|\\n)*\\*/", "/* ABC */ SUB */ ABC */"));
        assertFalse(Pattern.matches("/\\*([^*]|\\*[^/])*\\*/", "/* ABC */ SUB */ ABC */"));
    }

    @Test
    @DisplayName("Parsing Values")
    void parsing() {
        var pattern = Pattern.compile("(?<HOURS>[0-1]?[0-9]|2[0-3]):([0-5][0-9])");
        var matcher = pattern.matcher("01:45");

        assertTrue(matcher.matches());
        assertEquals("01", matcher.group("HOURS"));
        assertEquals("45", matcher.group(2));
    }

}
