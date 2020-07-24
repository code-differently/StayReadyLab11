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
            spellChecker.readFile(file);
        }
        catch(FileNotFoundException fileNotFound) {
            myLogger.info("Try again");
        }
    }

    @Test
    public void getSetOfWordsTest() {
        int expectedSize = 370104;

        int actualSize = spellChecker.getSetOfWords().size();

        Assert.assertEquals(expectedSize, actualSize);
    }
}
