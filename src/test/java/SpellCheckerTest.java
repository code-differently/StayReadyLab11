import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SpellCheckerTest 
{

    SpellChecker spell;
    @Before
    public void initialize() throws FileNotFoundException
    {
        spell  = new SpellChecker();
        spell.fillDict("words_alpha.txt");
    }

    @Test
    public void removeTest()
    {
        TreeSet <String> expected = new TreeSet<String>();
        expected.add("actual");

        spell.checkRemove("actuaal");


        Assert.assertEquals(expected, spell.getCorrect());
        
    }

    @Test
    public void addTest()
    {

        spell.checkAdd("ce");

        Assert.assertTrue(spell.getCorrect().contains("ace"));
        Assert.assertTrue(spell.getCorrect().contains("ice"));
        
    }

    @Test
    public void switchTest()
    {

        spell.checkSwitch("wll");


        Assert.assertTrue(spell.getCorrect().contains("ill"));
        Assert.assertTrue(spell.getCorrect().contains("fll"));
        
    }

    @Test
    public void swapTest()
    {
        TreeSet <String> expected = new TreeSet<String>();
        expected.add("will");

        spell.checkSwap("wlil");


        Assert.assertEquals(expected, spell.getCorrect());
        
        
    }
}
