import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

public class SpellChecker {
    private HashSet<String> referenceSet;
    private HashSet<String> set;
    Scanner sc;

    public void addReferenceFile(File file){
        try{
            referenceSet = new HashSet<String>();
            sc = new Scanner(file);

            while(sc.hasNextLine()){
                String currentWord = sc.nextLine();
                currentWord.toLowerCase();
                referenceSet.add(currentWord);
            }
            sc.close();
        }
        catch(FileNotFoundException e){
            System.out.println("The file was not found.");
            e.printStackTrace();
        }
    }

    public void addTextDoc(File file){
        try{
            set = new HashSet<String>();
            sc = new Scanner(file);

            while(sc.hasNextLine()){
                String currentLine = sc.nextLine();
                String[] words = currentLine.split(" ");

                for(String w: words){
                    w = w.toLowerCase().replaceAll("\\p{Punct}", "");
                    set.add(w);
                }
            }
            sc.close();
        }
        catch(FileNotFoundException e){
            System.out.println("The file was not found.");
            e.printStackTrace();
        }
    }

    public boolean correctSpelling(String word){

        for(String w: referenceSet){
            if(w.equals(word))
                return true;
        }

        return false;
    }

    public TreeSet<String> getSuggestedWords(String mispelledWord){
        TreeSet<String> suggestedWords = new TreeSet<String>();
        //StringBuilder mispelledWord = new StringBuilder(word);

        StringBuilder suggestion1 = deleteOneLetter(mispelledWord); 
        if(suggestion1 != null)
            suggestedWords.add(suggestion1.toString());


        return suggestedWords;
    }

    public StringBuilder deleteOneLetter(String w){
        StringBuilder newWord = new StringBuilder();
        StringBuilder word = new StringBuilder(w);

        for(int i = 0; i < word.length(); i++){
            word.deleteCharAt(i);

            if(referenceSet.contains(word)) {
                newWord = word;
                return newWord;
            }
        }

        return null;
    }

    public StringBuilder changeAnyLetter(String w){
        StringBuilder newWord = new StringBuilder();
        StringBuilder word = new StringBuilder(w);

        for(int i = 0; i < word.length(); i++){
            for(int j = 97; j <= 122; j++){

                Character character = (char) j;
                String ch = character.toString();
                word.replace(i, i+1, ch);

                if(referenceSet.contains(word)){
                    newWord = word;
                    return newWord;
                }
            }

            word = new StringBuilder(w);
        }
        return null;
    }

    public void printSpellingReference(){
        Iterator<String> it = referenceSet.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
    }

    public void printTextDoc(){
        Iterator<String> it = set.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
    }

    protected HashSet<String> getSet(){
        return set;
    }

    protected HashSet<String> getReferenceSet(){
        return referenceSet;
    }
}
