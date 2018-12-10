package ch.hsr.lecture12;

import java.util.Scanner;
import java.util.regex.Pattern;

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
public class RegexDemo {

    private static Scanner scanner = new Scanner(System.in);

    private static String GRAMMAR = "([0-1]?[0-9]|2[0-3]):[0-5][0-9]";

    public static void main(String[] argv) {
        var main = new RegexDemo();

//        main.test1_TimeRegex();
//        main.test2_BlockComment();
        main.test3_Parsing();
    }

    private void test1_TimeRegex() {
        System.out.println("=== test1_TimeRegex");

        String input = scanner.nextLine();

        if (Pattern.matches(GRAMMAR, input)) {
            System.out.println("Matching input");
        } else {
            System.out.println("Mismatch");
        }
    }

    private void test2_BlockComment() {
        System.out.println("=== test2_BlockComment");
        System.out.println(Pattern.matches("/\\*(.|\\n)*\\*/", "/* ABC */ SUB */ ABC */")); // Schachtelung ist eine Rekursion
        System.out.println(Pattern.matches("/\\*([^*]|\\*[^/])*\\*/", "/* ABC */ SUB */ ABC */")); // Schachtelung ist eine Rekursion
    }

    private void test3_Parsing() {
        System.out.println("=== test3_Parsing");
        var pattern = Pattern.compile("(?<HOURS>[0-1]?[0-9]|2[0-3]):([0-5][0-9])");
        var matcher = pattern.matcher("01:45");
        if (matcher.matches()) {
            System.out.println("Hours: " + matcher.group("HOURS")); // named capturing group
            System.out.println("Minutes: " + matcher.group(2));
        }

    }

}
