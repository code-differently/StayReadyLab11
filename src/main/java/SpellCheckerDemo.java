import java.util.Scanner;

public class SpellCheckerDemo {
    public static void main(String[] args){
        Scanner scan=new Scanner(System.in);
        System.out.println("Enter filepath (add another backslash for backslashes): ");
        String file=scan.nextLine();
        System.out.println("Searching...");
        SpellChecker sc =new SpellChecker(file);
        System.out.println("---Suggestions---\n"+sc.suggestWords()+"\n");

    }
}
