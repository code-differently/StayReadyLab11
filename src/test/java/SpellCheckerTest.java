import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.io.File;

public class SpellCheckerTest {

    @Test
    public void createSetTest(){
        SpellChecker sc = new SpellChecker();

        File file = new File("Test.txt");
        sc.createSet(file);
        Assert.assertEquals("[air, and, around, coca, cola, feet, freeze, ground, iced, in, kick, lemonade, pepsi, tea, the, touch, turn, up, your]", sc.getSet());
    }

    @Test
    public void deleteLettersTest(){
        SpellChecker sc = new SpellChecker();
        File file = new File("words_alpha.txt");
        sc.createSet(file);
        Assert.assertEquals("lemons ", sc.deleteLetters("leemons"));
    }

    @Test
    public void swapLettersTest(){
        SpellChecker sc = new SpellChecker();
        File file = new File("words_alpha.txt");
        sc.createSet(file);
        Assert.assertEquals("lemons ", sc.swapLetters("leomns"));
    }

    @Test
    public void changeLettersTest(){
        SpellChecker sc = new SpellChecker();
        File file = new File("words_alpha.txt");
        sc.createSet(file);
        Assert.assertEquals("dromons ", sc.changeLetters("deomons"));
    }

    @Test
    public void insertLettersTest(){
        SpellChecker sc = new SpellChecker();
        File file = new File("words_alpha.txt");
        sc.createSet(file);
        Assert.assertEquals("demons lemons ebons ", sc.insertLetters("emons"));
    }
}
