import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpellChecker {
    

    public static Set<String> addToSet(String fileName) throws FileNotFoundException {
        Set<String> hashSet = new HashSet<String>();
        Scanner scan = new Scanner(new File(fileName));

        while(scan.hasNextLine()){
            hashSet.add(scan.next().trim().toLowerCase());
        }

        Scanner input = new Scanner(System.in);
        String search = input.next().toLowerCase();

        if(hashSet.contains(search)){
            System.out.println("Yes");
        } else{
            System.out.println("No"); 
        }

        return hashSet;
     }

        
    }

    public static boolean isSpecial(String input){
        Pattern pattern = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
        Matcher match = pattern.matcher(input);
        return match.find();
    }

    public static boolean checkWord(String input, String[] diction){
        boolean valid = false; 
        int length = diction.length;
        int i = 0;

        while(!valid && i < length)
        {
            if(input.trim().equalsIgnoreCase(diction[i])){
                valid = true;
            }
            i++;
        }
        return valid;
    }

    public static boolean spellCheck(String input, String[] diction){
       String currentCheck = "";
       boolean noErrors = true;
       Scanner spellChecker = new Scanner(input);
       spellChecker.useDelimiter("\\s+");

       while(spellChecker.hasNext()){
           currentCheck = spellChecker.next();

           if(!isSpecial(currentCheck)){
                if(!checkWord(currentCheck, diction)){
                    System.out.println(currentCheck + " is spelt incorrectly");
                    noErrors = false;
                }
           }
       }
       return noErrors;
    }
}
