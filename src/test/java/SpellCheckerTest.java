import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.TreeSet;

public class SpellCheckerTest {
    @Test
    public void deletedCharsTest(){
        //Given
        String[] expected = {"ere", "hre", "hee", "her"};

        //When
        SpellChecker checker = new SpellChecker();
        String[] actual = checker.newDeletedChars("here");

        //Then
        Assert.assertArrayEquals(expected, actual);
    }
    @Test
    public void addedCharsTest(){
        //Given
        int expected = 104;

        //When
        SpellChecker checker = new SpellChecker();
        int actual = checker.newAddedChars("here").length;

        //Then
        Assert.assertEquals(expected, actual);
    }
    @Test
    public void insertCharTest(){
        //Given
        int expected = 130;

        //When
        SpellChecker checker = new SpellChecker();
        int actual = checker.insertLetter("here").length;

        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void suggestedTest() throws IOException {
        //Given
        String expected =
                "humnity | Suggested words: [humanity]\n" +
                "bil | Suggested words: [ail, bail, bal, bbl, bdl, bel, bhil, bi, bib, bibl, bid, big, bile, bilk, bill, bilo, bim, bin, bio, biol, birl, bis, bit, biz, bl, boil, bol, brl, btl, bul, dil, fil, gil, il, kil, mil, nil, oil, pil, sil, til, vil]\n" +
                "lkdpoqhje9ra23o8yrbkjfjadfhiu | Suggested words: [no suggestions]\n" +
                "nill | Suggested words: [no suggestions]\n" +
                "bill | Suggested words: [no suggestions]\n" +
                "fill | Suggested words: [no suggestions]\n";

        //When
        SpellChecker checker = new SpellChecker();
        String actual = checker.suggested("text.txt");

        //Then
        Assert.assertEquals(expected, actual);
    }
}
