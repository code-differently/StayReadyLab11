import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class SpellChecker {

    // Used for correctly spelled words
    private final File correctWordsFile = new File("words_alpha.txt");
    private final Scanner inputC = FileScanner.newFileScanner(correctWordsFile);
    private final Set<String> correctWords = new HashSet<String>(); // Stores all correctly spelled words

    // Used for incorrectly spelled words
    private File fileToBeChecked;
    private Scanner inputM;
    private final Set<String> misspelledWords = new TreeSet<String>();

    public SpellChecker() {
        while(inputC.hasNext()) {
            correctWords.add(inputC.next().toLowerCase());
        }
    }

    public SpellChecker(File f) {
        fileToBeChecked = f;
        inputM = FileScanner.newFileScanner(fileToBeChecked);
        while(inputC.hasNext()) {
            correctWords.add(inputC.next().toLowerCase());
        }
    }

    public Set<String> getCorrectWords() {
        return correctWords;
    }

    public Set<String> getMisspelledWords() {
        return misspelledWords;
    }

    public File getFileToBeChecked() {
        return fileToBeChecked;
    }

    public void setFileToBeChecked(File f) {
        fileToBeChecked = f;
        inputM = FileScanner.newFileScanner(fileToBeChecked);
        misspelledWords.clear();
    }

    public void scanFileForMisspellings() {
        while(inputM.hasNext()) {
            String current = inputM.next();
            StringBuilder currentOnlyLetters = new StringBuilder();
            for(int i = 0; i < current.length(); i++) {
                if (Character.isLetter(current.charAt(i))) {
                    currentOnlyLetters.append(current.charAt(i));
                }
            }
            if(!correctWords.contains(currentOnlyLetters.toString().toLowerCase())) {
                misspelledWords.add(currentOnlyLetters.toString());
            }
        }
    }

    public String getMisspelledWordSuggestions() {
        StringBuilder ret = new StringBuilder();
        for(String entry : misspelledWords) {
            ret.append(entry).append(": ");
            ret.append(getSuggestions(entry)).append("\n");
        }
        return ret.toString().trim();
    }

    public String getSuggestions(String word) {
        StringBuilder ret = new StringBuilder();
        TreeSet<String> suggestions = new TreeSet<String>();
        suggestions.addAll(getDeleteSuggestions(word));
        suggestions.addAll(getChangeLetterSuggestions(word));
        suggestions.addAll(getInsertLetterSuggestions(word));
        suggestions.addAll(getSwapCharsSuggestions(word));
        if(suggestions.size() == 0)
            return "(no suggestions)";
        for(String entry : suggestions)
            ret.append(entry).append(", ");
        return ret.toString().substring(0, ret.toString().length()-2);
    }

    public HashSet<String> getDeleteSuggestions(String word) {
        HashSet<String> suggestions = new HashSet<String>();
        for(int i = 0; i < word.length(); i++) {
            StringBuilder newWord = new StringBuilder();
            for(int j = 0; j < word.length(); j++) {
                if(j != i)
                    newWord.append(word.charAt(j));
            }
            if(correctWords.contains(newWord.toString().toLowerCase())) {
                suggestions.add(newWord.toString());
            }
        }
        return suggestions;
    }

    public HashSet<String> getChangeLetterSuggestions(String word) {
        HashSet<String> suggestions = new HashSet<String>();
        for(int k = 0; k < 26; k++) {
            for (int i = 0; i < word.length(); i++) {
                StringBuilder newWord = new StringBuilder();
                for (int j = 0; j < word.length(); j++) {
                    if (i == j) {
                        newWord.append((char) (k + 'a'));
                    } else {
                        newWord.append(word.charAt(j));
                    }
                }
                if (correctWords.contains(newWord.toString().toLowerCase())) {
                    suggestions.add(newWord.toString());
                }
            }
        }
        return suggestions;
    }

    public HashSet<String> getInsertLetterSuggestions(String word) {
        HashSet<String> suggestions = new HashSet<String>();
        for(int k = 0; k < 26; k++) {
            for (int i = 0; i < word.length()+1; i++) {
                StringBuilder newWord = new StringBuilder();
                for (int j = 0; j < word.length(); j++) {
                    if (i == j) {
                        newWord.append((char) (k + 'a')).append(word.charAt(j));
                    } else if(i == word.length()){
                        newWord = new StringBuilder(word);
                        newWord.append((char)(k + 'a'));
                    } else {
                        newWord.append(word.charAt(j));
                    }
                }
                if (correctWords.contains(newWord.toString().toLowerCase())) {
                    suggestions.add(newWord.toString());
                }
            }
        }
        return suggestions;
    }

    public HashSet<String> getSwapCharsSuggestions(String word) {
        HashSet<String> suggestions = new HashSet<String>();
        for(int i  = 0; i < word.length()-1; i++) {
            StringBuilder newWord = new StringBuilder();
            for(int j = 0; j < word.length(); j++) {
                if(j == i) {
                    newWord.append(word.charAt(j+1)).append(word.charAt(i)); j++;
                } else {
                    newWord.append(word.charAt(j));
                }
            }
            if (correctWords.contains(newWord.toString().toLowerCase())) {
                suggestions.add(newWord.toString());
            }
        }
        return suggestions;
    }
    public static void main(String... args) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Spell Checker");

        String again;
        do {
            System.out.print("Please enter file to spell check: ");
            SpellChecker sc = new SpellChecker(new File(keyboard.nextLine()));
            sc.scanFileForMisspellings();

            System.out.println("\nMisspelled Words\n--------------------------------------------------------------------\n" +
                    sc.getMisspelledWordSuggestions());

            System.out.print("\nSpell check another file? ");
            again = keyboard.nextLine();

        } while(again.equalsIgnoreCase("YES"));
    }

}