import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.TreeSet;

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
        TreeSet<String> set = new TreeSet<>();
        TreeSet<String> exp = new TreeSet<>();
        exp.add("lemons");
        File file = new File("words_alpha.txt");
        sc.createSet(file);
        Assert.assertEquals(exp, sc.deleteLetters("leemons",set));
    }

    @Test
    public void swapLettersTest(){
        SpellChecker sc = new SpellChecker();
        TreeSet<String> set = new TreeSet<>();
        TreeSet<String> exp = new TreeSet<>();
        exp.add("lemons");
        File file = new File("words_alpha.txt");
        sc.createSet(file);
        Assert.assertEquals(exp, sc.swapLetters("leomns",set));
    }

    @Test
    public void changeLettersTest(){
        SpellChecker sc = new SpellChecker();
        TreeSet<String> set = new TreeSet<>();
        TreeSet<String> exp = new TreeSet<>();
        exp.add("dromons");
        File file = new File("words_alpha.txt");
        sc.createSet(file);
        Assert.assertEquals(exp, sc.changeLetters("deomons",set));
    }

    @Test
    public void insertLettersTest(){
        SpellChecker sc = new SpellChecker();
        TreeSet<String> set = new TreeSet<>();
        TreeSet<String> exp = new TreeSet<>();
        exp.add("demons");
        exp.add("lemons");
        exp.add("ebons");
        File file = new File("words_alpha.txt");
        sc.createSet(file);
        Assert.assertEquals(exp, sc.insertLetters("emons",set));
    }
}
