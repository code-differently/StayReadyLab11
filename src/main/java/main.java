import java.io.IOException;
import java.util.Scanner;

public class main {
    public static void main(String...args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello and Welcome to the designated File reader PART 2: File Checker");
        System.out.println("To start please enter the filename of which you want to check!");
        System.out.println("Please enter a filename ending in .txt");
        String fileName = scanner.nextLine();
        SpellChecker checker = new SpellChecker();
        System.out.println("This is the complete output of suggested words you may find useful:");
        System.out.println();
        System.out.println(checker.suggested(fileName));
        System.out.println();
        System.out.println("---------------------------------------------------");
        System.out.println("Thank you for using THE File Checker Official!");
    }
}
