import org.junit.jupiter.api.Test;

public class SpellCheckerTest {

    @Test
    public void correctSpellingTest(){
        //Given
        String expectedFilePath = "C:\Users\StayReady02\Documents\dev\CodeDifferently\stayReadyLabs\StayReadyLab12\StayReadyLab11\words_alpha.text";
        //When 
        String actualFilePath = spellChecker.checkWord();
        //Then 
        Assert.assertEquals(expectedFilePath, actualFilePath);
    }
}
