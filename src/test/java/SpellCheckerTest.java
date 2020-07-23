import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SpellCheckerTest 
{
    public static void main(String[] args) throws FileNotFoundException
    {
        
        SpellChecker spellChecker = new SpellChecker();
        //To fill the HashSet object named dictionary with words
        spellChecker.fillDict("words_alpha.txt");
        
        //To select a file by inputing into the terminal
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the name of the file:");
        String file = scan.next();
        spellChecker.checkFile(file);

    }
}
