import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class SpellCheckerTest {

    @Test
    public void constructorTest1() {
        // Given
        File f = new File("words_alpha.txt");
        Scanner scan = FileScanner.newFileScanner(f);
        HashSet<String> expected = new HashSet<String>();
        while(scan.hasNext()) {
            expected.add(scan.next());
        }

        // When
        SpellChecker sc = new SpellChecker();

        // Then
        Assert.assertEquals(expected.size(), sc.getCorrectWords().size());
    }

    @Test
    public void constructorTest2() {
        // Given
        File f = new File("letter_from_gandhi.rtf");

        // When
        SpellChecker sc = new SpellChecker(f);

        // Then
        Assert.assertEquals(f, sc.getFileToBeChecked());
    }

    SpellChecker sc;
    File f;
    @Before
    public void initialize() {
        sc = new SpellChecker();
    }

    @Test
    public void getCorrectWordsTest() {
        File f = new File("words_alpha.txt");
        Scanner scan = FileScanner.newFileScanner(f);
        HashSet<String> expected = new HashSet<String>();
        while(scan.hasNext()) {
            expected.add(scan.next());
        }

        // When
        Set<String> actual = sc.getCorrectWords();

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getMisspelledWordsTest() {
        // Given
        HashSet<String> expected = new HashSet<String>();
        expected.add("rghehev");
        expected.add("tgywcaa");
        expected.add("badd");
        sc.setFileToBeChecked(new File("TestFile.txt"));
        sc.scanFileForMisspellings();

        // When
        Set<String> actual = sc.getMisspelledWords();

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getFileToBeCheckedTest() {
        // Given
        File expected = f;

        // When
        File actual = sc.getFileToBeChecked();

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void setFileToBeCheckedTest() {
        // Given
        File expected = new File("TestFile.txt");

        // When
        sc.setFileToBeChecked(expected);
        File actual = sc.getFileToBeChecked();

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void scanFileForMisspellingsTest() {
        // Given
        HashSet<String> expected = new HashSet<String>();
        expected.add("rghehev");
        expected.add("tgywcaa");
        expected.add("badd");
        sc.setFileToBeChecked(new File("TestFile.txt"));

        // When
        sc.scanFileForMisspellings();
        Set<String> actual = sc.getMisspelledWords();

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getMisspelledWordSuggestionsTest() {
        sc.setFileToBeChecked(new File("TestFile2.txt"));
        sc.scanFileForMisspellings();
        String expected = "redily: readily, redely, redly, reedily\n" +
                "stxy: sexy, stay, stey, sty, styx\n" +
                "codlrs: coders\n" +
                "difefrently: differently";

        // When
        String actual = sc.getMisspelledWordSuggestions();

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getSuggestionsTest() {
        sc.setFileToBeChecked(new File("TestFile2.txt"));
        sc.scanFileForMisspellings();
        String expected = "tex, tie, tied, tien, tier, ties";

        // When
        String actual = sc.getSuggestions("tiex");

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getDeleteSuggestionsTest() {
        // Given
        String misspelled = "tcount";
        String expected = "[count]";

        // When
        HashSet<String> actual = sc.getDeleteSuggestions(misspelled);

        // Then
        Assert.assertEquals(expected, actual.toString());
    }

    @Test
    public void getChangeLetterSuggestionsTest() {
        // Given
        String misspelled = "brend";
        String expected = "[brens, brent, bread, trend, arend, blend, brand, brerd, breed]";

        // When
        HashSet<String> actual = sc.getChangeLetterSuggestions(misspelled);

        // Then
        Assert.assertEquals(expected, actual.toString());
    }

    @Test
    public void getInsertLetterSuggestionsTest() {
        // Given
        String misspelled = "tret";
        String expected = "[toret, trest, treat, trent, stret, trets]";

        // When
        HashSet<String> actual = sc.getInsertLetterSuggestions(misspelled);

        // Then
        Assert.assertEquals(expected, actual.toString());
    }

    @Test
    public void getSwapCharsSuggestionsTest() {
        // Given
        String misspelled = "ohtel";
        String expected = "[hotel]";

        // When
        HashSet<String> actual = sc.getSwapCharsSuggestions(misspelled);

        // Then
        Assert.assertEquals(expected, actual.toString());
    }

}
