import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SpellCheckerTest {

    SpellChecker sc;

    @Before
    public void initialize(){
        sc = new SpellChecker();
    }

    @Test
    public void addSpellingReferenceTest(){
        File file = new File("words_alpha.txt");
        sc.addReferenceFile(file);

        Assert.assertFalse(sc.getReferenceSet().isEmpty());
    }

    @Test
    public void addTextDocTest(){
        File file = new File("letter_from_gandhi.txt");
        sc.addTextDoc(file);

        Assert.assertFalse(sc.getSet().isEmpty());
    }

    @Test
    public void correctSpellingTestTrue(){
        File file = new File("words_alpha.txt");
        sc.addReferenceFile(file);

        boolean actual = sc.correctSpelling("aahs");

        Assert.assertTrue(actual);
    }

    @Test
    public void correctSpellingTestFalse(){
        File file = new File("words_alpha.txt");
        sc.addReferenceFile(file);

        boolean actual = sc.correctSpelling("aabs");

        Assert.assertFalse(actual);
    }

    @Test
    public void deleteOneLetterTest(){
        File file = new File("words_alpha.txt");
        sc.addReferenceFile(file);

        StringBuilder actual = sc.deleteOneLetter("html");

        Assert.assertNull(actual);
    }

    @Test
    public void changeAnyLetterTest(){
        File file = new File("words_alpha.txt");
        sc.addReferenceFile(file);

        StringBuilder actual = sc.changeAnyLetter("txt");
        StringBuilder expected = new StringBuilder("tat");

        Assert.assertEquals(expected, actual);
    }
}
