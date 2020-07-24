import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

public class SpellChecker {
    private HashSet<String> dictionary;
    private HashSet<String> correctWords;
    private HashMap<String, TreeSet<String>> suggestions;
    private String output;

    public SpellChecker(String filePath){
        this.output="";
        this.suggestions= new HashMap<String, TreeSet<String>>();
        this.dictionary=new HashSet<String>();
        this.correctWords=new HashSet<String>();
        Scanner file;
        try {
            file=new Scanner(new File("words_alpha.txt"));
            while(file.hasNext()){
                String currentWord=file.next().toLowerCase().replaceAll("[\\W+]","");
                this.dictionary.add(currentWord);
            }
            scanWords(filePath);
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        }
    }

    private void scanWords(String filePath) {
        Scanner file;
        try {
            file=new Scanner(new File(filePath));
            while(file.hasNext()){
                String currentWord=file.next().toLowerCase().replaceAll("[\\W+]","");
                if(this.dictionary.contains(currentWord)) {
                    this.correctWords.add(currentWord);
                    this.output+=currentWord+": (no suggestions)\n";
                }
                else{
                    if(!this.suggestions.containsKey(currentWord))
                        this.suggestions.put(currentWord,new TreeSet<String>());
                }
            }
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        }
        for (String word :
            this.suggestions.keySet()) {
            TreeSet<String> replace=suggestedWords(word);
            this.suggestions.replace(word,replace);
            if(replace.size()==0)
                this.output+=word+": (no suggestions)\n";
            else
                this.output+=word+": "+replace.toString().replaceAll("[\\[\\]]","")+"\n";
        }
    }

    public TreeSet<String> suggestedWords(String word){
        TreeSet<String> suggestedWords=new TreeSet<String>();
        for (int i = 0; i <word.length() ; i++) {
            StringBuilder sb1 = new StringBuilder(word);
            StringBuilder sb5 = new StringBuilder(word);
            sb1.deleteCharAt(i);
            if(i<word.length()-1) {
                char temp=sb5.charAt(i);
                sb5.setCharAt(i, sb5.charAt(i + 1));
                sb5.setCharAt(i + 1,temp);
            }
            String newWord1 = sb1.toString();
            String newWord5 = sb5.toString();
            for (char j = 'a'; j < 'z'; j++) {
                StringBuilder sb2 = new StringBuilder(word);
                StringBuilder sb3 = new StringBuilder(word);
                StringBuilder sb4 = new StringBuilder(word);
                sb3.insert(i,j);
                sb4.insert(sb4.length(),j);
                sb2.setCharAt(i, j);
                String newWord2 = sb2.toString();
                String newWord3 = sb3.toString();
                String newWord4 = sb4.toString();
                for (String correctWord :
                    this.dictionary) {
                    int length = word.length();
                    if (correctWord.length() <= length + 1 && correctWord.length() >= length - 1) {
                        if (correctWord.equals(newWord1))
                            suggestedWords.add(correctWord);
                        if (correctWord.equals(newWord2))
                            suggestedWords.add(correctWord);
                        if (correctWord.equals(newWord3))
                            suggestedWords.add(correctWord);
                        if (correctWord.equals(newWord4))
                            suggestedWords.add(correctWord);
                        if (correctWord.equals(newWord5))
                            suggestedWords.add(correctWord);
                    }
                }

            }
        }
        return suggestedWords;
    }

    public String suggestWords(){
        return this.output.trim();
    }

}
