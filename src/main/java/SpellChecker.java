import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class SpellChecker {
    private HashSet<String> dictHash = new HashSet<String>();
    private HashSet<String> letterHash = new HashSet<String>();

    public void convert(String fileName) throws IOException {
        String data = new String(Files.readAllBytes(Paths.get("words_alpha.txt"))).toLowerCase();
        String[] splitData = data.split("\\W+");
        for (String str : splitData) {
            this.dictHash.add(str);
        }
        String dataLetter = new String(Files.readAllBytes(Paths.get(fileName))).toLowerCase();
        String[] splitLetter = dataLetter.split("\\W+");
        for (String string : splitLetter) {
            this.letterHash.add(string);
        }
    }
    public String[] newDeletedChars(String word){
        String newWord = "";
        String[] deletedWords = new String [word.length()];
        for(int i = 0; i < word.length(); i++){
            for(int j = 0; j < word.length(); j++) {
                if (j != i) {
                    newWord += word.charAt(j);
                }
            }
            deletedWords[i] = newWord;
            newWord = "";
        }
        return deletedWords;
    }
    public String[] newAddedChars(String word) {
        String newWord = "";
        String first = "";
        String middle = "";
        String last = "";
        List<String> addedWords = new ArrayList<String>();
        for (char alpha = 'a'; alpha <= 'z'; alpha++) {
            for (int i = 0; i < word.length(); i++) {
                first = word.substring(0, i);
                middle = String.valueOf(alpha);
                last = word.substring(i + 1);

                newWord = first+middle+last;
                addedWords.add(newWord);
                newWord = "";
            }
        }
        String[] added = new String[addedWords.size()];
        for(int j = 0; j < addedWords.size(); j++){
            added[j] = addedWords.get(j);
    }
        return added;
}
    public String[] insertLetter(String word){
        String newWord = "";
        String first = "";
        String middle = "";
        String last = "";
        List<String> insertedWords = new ArrayList<String>();
        for (char alpha = 'a'; alpha <= 'z'; alpha++) {
            for (int i = 0; i < word.length() + 1; i++) {
                if(i == 0){
                    first = String.valueOf(alpha);
                    last = word.substring(i);

                    newWord = first+last;
                    insertedWords.add(newWord);
                    newWord = "";
                }
                else {
                    first = word.substring(0, i);
                    middle = String.valueOf(alpha);
                    last = word.substring(i);

                    newWord = first + middle + last;
                    insertedWords.add(newWord);
                    newWord = "";
                }
            }
        }
        String[] inserted = new String[insertedWords.size()];
        for(int j = 0; j < insertedWords.size(); j++){
            inserted[j] = insertedWords.get(j);
        }
        return inserted;
    }
    public String[] swappedLetters(String word){
        String[] swapped = new String[word.length() - 1];
        for(int i = 0; i < word.length(); i++) {
            StringBuilder sb = new StringBuilder(word);
            if(i < word.length() - 1) {
                char temp = sb.charAt(i);
                sb.setCharAt(i, sb.charAt(i + 1));
                sb.setCharAt(i + 1, temp);
                swapped[i] = sb.toString();
            }
        }
        return swapped;
    }

    public TreeSet<String> suggestedWords(String[] possibleSuggested){
        TreeSet<String> suggested = new TreeSet<String>();
        for(String str : possibleSuggested){
            if(this.dictHash.contains(str)){
                suggested.add(str);
            }
        }
        return suggested;
    }
    public TreeSet<String> combinedSuggestions(String word){
        TreeSet<String> combined = new TreeSet<String>();
        combined.addAll(suggestedWords(newDeletedChars(word)));
        combined.addAll(suggestedWords(newAddedChars(word)));
        combined.addAll(suggestedWords(insertLetter(word)));
        combined.addAll(suggestedWords(swappedLetters(word)));

        return combined;
    }

    public String suggested(String fileName) throws IOException {
        convert(fileName);
        String str = "";
        for(String word : this.letterHash){
            if(this.dictHash.contains(word) || combinedSuggestions(word).isEmpty()){
                str += word + " | Suggested words: [no suggestions]\n";
            }
            else if(!this.dictHash.contains(word)){
                str += word + " | Suggested words: " + combinedSuggestions(word).toString() + "\n";
            }
        }
        return str;
    }

    /*Testing only
    public static void main(String...args){
        SpellChecker checker = new SpellChecker();
        String [] swap = checker.swappedLetters("here");
        for(String str : swap){
            System.out.println(str);
        }
    }

     */

}
