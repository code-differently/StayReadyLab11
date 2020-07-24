import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class SpellChecker {
    private HashSet<String> dictionary = new HashSet<String>();
    private Set<String> printCorrect = new TreeSet<String>();
    private char[] alphabetArray = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public SpellChecker() {

    }

    public Set<String> getCorrect()
    {
        return printCorrect;
    }

    public void fillDict(String fileInput) throws FileNotFoundException 
    {
        Scanner scan = new Scanner(new File(fileInput));
        while (scan.hasNext()) 
        {
            dictionary.add(scan.next());
        }
    }

    //Method to check if file exists  abd use it if it does
    public void checkFile(String input) throws FileNotFoundException 
    {
        try
        {
              Scanner scan = new Scanner(new File(input));
        while (scan.hasNext() )
        {
           
              getCorrect(scan.next().replaceAll("[^a-zA-Z ]", "").toLowerCase());  
        }
    }
        catch (Exception e) 
        {
           System.out.println("File does not exist, try again");
        }
    }

    //Method to look through the dictionary and to affect it if its not contained
    private void getCorrect(String word) 
    {
        System.out.print(word+": ");
        if (!dictionary.contains(word)) 
        {
            
            checkAdd(word);
            checkRemove(word);
            checkSwap(word);
            checkSwitch(word);
            System.out.println(printCorrect.toString());
        } 
        else 
        {
            System.out.println("(No Sugggestions)");
        }

       
        printCorrect.clear();

    }
    //Complete Method to insert any letter at any point in the misspelled word
    public void checkAdd(String word) 
    {
      StringBuilder sBuilder = new StringBuilder(word);
      

        for (int i = 0; i < word.length(); i++) 
        {
            for (int j = 0; j < alphabetArray.length; j++) 
            {
                sBuilder.insert(i,alphabetArray[j]);
                String check = String.valueOf(sBuilder);
                if (dictionary.contains(check)) 
                {
                    printCorrect.add(check);
                }
                sBuilder = new StringBuilder(word);
            }
        }
    }

    //Complete: Method to delete any one of the letters from the misspelled word
    public void checkRemove(String word) 
    {
        StringBuilder sBuilder = new StringBuilder(word);
        for (int i = 0; i < word.length(); i++) 
        {
            sBuilder.replace(i, i+1, "");
            String checker = String.valueOf(sBuilder.toString());
                if (dictionary.contains(checker)) 
                {
                    printCorrect.add(checker);
                    
                }
                sBuilder = new StringBuilder(word);  
            }
    }
    
    //Complete Method to change any letter in the misspelled word to any other letter
    public void checkSwitch(String word) 
    {
        char[] wordArray = word.toCharArray();

        for (int i = 0; i < wordArray.length; i++) 
        {
            wordArray = word.toCharArray();
            for (int j = 0; j < alphabetArray.length; j++) 
            {
                wordArray[i] = alphabetArray[j];
                String check = String.valueOf(wordArray);
                if (dictionary.contains(check)) 
                {
                   printCorrect.add(check);
                }

            }
        }
    }

    //Complete Method to swap any two neighboring characters in the misspelled word
    public void checkSwap(String word)
    {
        StringBuilder sBuilder = new StringBuilder(word);
        for (int i = 0; i < word.length()-1;i++)
        {
            char firstSwap = sBuilder.charAt(i);
            char secondSwap = sBuilder.charAt(i+1);

            sBuilder.setCharAt(i, secondSwap);
            sBuilder.setCharAt(i+1, firstSwap);

            String check = String.valueOf(sBuilder);
            if (dictionary.contains(check))
            {
                printCorrect.add(check);
            }
            sBuilder = new StringBuilder(word);
        }
    }

    
    
}
