import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Logger;

public class SpellCheckerTest {
    private File file;
    private SpellChecker spellChecker;
    private final static Logger myLogger = Logger.getLogger("com.codedifferently.spellchecker");;

    @Before
    public void setUp() {
        file = new File("words_alpha.txt");
        spellChecker = new SpellChecker();
        try {
            spellChecker.readFile(file, true);
        }
        catch(FileNotFoundException fileNotFound) {
            myLogger.info("Try again");
        }
        spellChecker.populateAlphabetArray();
    }

    @Test
    public void getSetOfWordsTest() {
        int expectedSize = 370104;

        int actualSize = spellChecker.getSetOfCorrectlySpelledWords().size();

        Assert.assertEquals(expectedSize, actualSize);
    }

    @Test
    public void getWordsSpelledCorrectlyFileTest() {
        String expectedFilePath = "C:\\Kaveesha\\Github\\devCodeDifferently\\stayReadyLabs\\StayReadyLab11\\words_alpha.txt";

        File wordsSpelledCorrectly = spellChecker.getWordsSpelledCorrectlyFile();
        String actualFilePath = wordsSpelledCorrectly.toString();

        Assert.assertEquals(expectedFilePath, actualFilePath);
    }

    @Test
    public void getAlphabetArr() {
        int expectedSize = 26;

        Character[] alphabetArr = spellChecker.getAlphabetArr();
        int actualSize = alphabetArr.length;

        Assert.assertEquals(expectedSize, actualSize);
    }

    @Test
    public void findSuggestionsUsingDeleteTest() {
        String misspelledWord = "gorale";
        String expectedResult = "gorale: goral, orale";

        spellChecker.findSuggestionsUsingDelete(misspelledWord);
        String actualResult = spellChecker.displayListOfSuggestions(misspelledWord);

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void findSuggestionsByChangingLettersTest() {
        String misspelledWord = "gopherperry";
        String expectedResult = "gopherperry: gopherberry";

        spellChecker.findSuggestionsByChangingLetters(misspelledWord);
        String actualResult = spellChecker.displayListOfSuggestions(misspelledWord);

        Assert.assertEquals(expectedResult, actualResult);
    }
}
