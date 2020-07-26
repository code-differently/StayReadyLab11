import static org.junit.Assert.*;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SpellCheckerTest {
    SpellChecker obj;

    @Test
    public void storeDictionaryTest() throws FileNotFoundException {
        obj = new SpellChecker();

        obj.storeDictionary();

        assertTrue(!obj.getWordList().isEmpty());
    }

    @Test
    public void storeLetterTest() throws FileNotFoundException {
        obj = new SpellChecker();
        String filePath = "testText.txt";
        File fileToCheck = new File(filePath);

        obj.in = new Scanner(fileToCheck);
        obj.storeWords(obj.getWordsToCheck());

        assertTrue(!obj.getWordsToCheck().isEmpty());
    }

    @Test
    public void spellCheckTest() throws FileNotFoundException {
        obj = new SpellChecker();
        String filePath = "testText.txt";
        File fileToCheck = new File(filePath);

        int expected = 2;
        obj.storeDictionary();
        obj.in = new Scanner(fileToCheck);
        obj.storeWords(obj.getWordsToCheck());
        obj.spellCheck();

        assertEquals(expected, obj.getMisspelledWords().size());
    }

    @Test
    public void suggestWordsTest() throws FileNotFoundException {
        obj = new SpellChecker();

        String misspelledWord = "nightt";
        obj.storeDictionary();
        obj.suggestWords(misspelledWord);

        assertTrue(!obj.getTreeSet().isEmpty());
    }
}
