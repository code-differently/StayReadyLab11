import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SpellChecker {
    Scanner in;
    private Set<String> wordList = new HashSet<>();
    private Set<String> wordsToCheck = new HashSet<>();
    private Set<String> misspelledWords = new HashSet<>();
    private final String[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".split("");
    private TreeSet<String> treeSet;

    public static void main(String[] args) throws FileNotFoundException {
        SpellChecker obj = new SpellChecker();
        obj.storeDictionary();
        obj.storeLetter();
        obj.spellCheck();

    }

    public void storeDictionary() throws FileNotFoundException {
        File wordFile = new File("words_alpha.txt");
        in = new Scanner(wordFile);
        // reads line by line
        storeWords(wordList);
    }

    public void storeLetter() {
        in = new Scanner(System.in);
        System.out.println("Enter the path of the file you would like to spell check");
        String input = in.next();
        try {
            File fileToCheck = new File(input);
            in = new Scanner(fileToCheck);
        } catch(Exception err) {
            System.out.println("File not found, please try again\n");
            storeLetter();
        }
        storeWords(wordsToCheck);
    }

    public void storeWords(Set<String> set) {
        while (in.hasNextLine()) {
            String currentLine = in.nextLine();
            // removes anything that isn't a letter or a space
            currentLine = currentLine.replaceAll("[^a-zA-Z ]", "");
            currentLine = currentLine.toLowerCase();
            // separates each line into a word array
            String[] words = currentLine.split(" ");
            // iterating through words
            set.addAll(Arrays.asList(words));
        }
    }

    public void spellCheck() {
        for (String word : wordsToCheck) {
            if (!wordList.contains(word)) {
                if (!misspelledWords.contains(word)) {
                    misspelledWords.add(word);
                    suggestWords(word);
                }
                String suggestions;
                if (treeSet.isEmpty()) {
                    suggestions = "(No suggestions)";
                } else {
                    suggestions = treeSet.toString();
                }
                System.out.println(word + ": " + suggestions);
            }
        }
    }

    public void suggestWords(String word) {
            treeSet = new TreeSet<>();
            tryDelete(word);
            tryChangeLetter(word);
            tryInsertLetter(word);
            trySwap(word);

    }

    // Delete any one of the letters from the misspelled word
    public void tryDelete(String str) {
        StringBuilder tempWord = new StringBuilder(str);
        for (int i = 0; i < str.length(); i++) {
            tempWord.deleteCharAt(i);
            if (wordList.contains(tempWord.toString())) {
                treeSet.add(tempWord.toString());
                break;
            } else {
                tempWord = new StringBuilder(str);
            }
        }
    }

    // Change any letter in the misspelled word to any other letter
    public void tryChangeLetter(String str) {
        StringBuilder tempWord = new StringBuilder(str);
        for (int i = 0; i < str.length(); i++) {
            for (String s : ALPHABET) {
                tempWord.replace(i, i + 1, s);
                if (wordList.contains(tempWord.toString())) {
                    treeSet.add(tempWord.toString());
                    break;
                } else {
                    tempWord = new StringBuilder(str);
                }
            }
        }
    }

    // Insert any letter at any point in the misspelled word
    public void tryInsertLetter(String str) {
        StringBuilder tempWord = new StringBuilder(str);
        for (int i = 0; i < str.length(); i++) {
            for (String s : ALPHABET) {
                tempWord.insert(i, s);
                if (wordList.contains(tempWord.toString())) {
                    treeSet.add(tempWord.toString());
                    break;
                } else {
                    tempWord = new StringBuilder(str);
                }
            }
        }
    }

    // Swap any two neighboring characters in the misspelled word
    public void trySwap(String str) {
        StringBuilder tempWord = new StringBuilder(str);
        for (int i = 1; i < str.length(); i++) {
            String firstElementSwap = tempWord.substring(i - 1, i);
            String nextElementSwap = tempWord.substring(i, i + 1);
            tempWord.replace(i - 1, i, nextElementSwap);
            tempWord.replace(i, i + 1, firstElementSwap);
            if (wordList.contains(tempWord.toString())) {
                treeSet.add(tempWord.toString());
                break;
            } else {
                tempWord = new StringBuilder(str);
            }
        }
    }

    public Set<String> getWordList() {
        return wordList;
    }

    public Set<String> getWordsToCheck() {
        return wordsToCheck;
    }

    public Set<String> getMisspelledWords() {
        return misspelledWords;
    }

    public TreeSet<String> getTreeSet() {
        return treeSet;
    }
}
