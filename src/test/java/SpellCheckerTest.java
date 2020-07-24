import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SpellCheckerTest {
    SpellChecker sc;
    @Before
    public void SetUp(){
        sc=new SpellChecker("test.txt");
    }

    @Test
    public void outputTest() {
        // Given
        String expected = "appl: (no suggestions)\n" +
            "d934wudg9324ug: (no suggestions)\n" +
            "xack: ack, back, cack, fack, hack, jack, lack, mack, pack, rack, sack, tack, wack, yack\n" +
            "eeffe: (no suggestions)\n" +
            "andx: anax, and, anda, ande, andi, ands, andy\n" +
            "ladn: lad, lade, laden, ladin, lads, lady, laen, lain, lan, land, larn, laun, lawn\n" +
            "banan: anan, badan, bahan, bajan, balan, bana, banak, banal, banana, banat, banian, banyan, basan, batan, tanan, yanan";

        // When
        String actual = sc.suggestWords();

        // Then
        Assert.assertEquals(expected, actual);
    }

}
