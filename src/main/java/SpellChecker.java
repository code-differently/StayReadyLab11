import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Logger;

public class SpellChecker {
    private HashSet<String> setOfCorrectlySpelledWords = new HashSet<String>();
    TreeMap <String, TreeSet<String>> suggestions = new TreeMap<>();
    private Scanner scanner = new Scanner(System.in);

    private final static Logger myLogger = Logger.getLogger("com.codedifferently.spellchecker");
    private final File wordsSpelledCorrectlyFile = new File("./words_alpha.txt");
    private final Character[] alphabetArr = new Character[26];

    public static void main(String[] args) {
        SpellChecker checker = new SpellChecker();
        checker.populateAlphabetArray();
        try {
            checker.readFileWithCorrectlySpelledWords(checker.getWordsSpelledCorrectlyFile());
            checker.keepAskingUserUntilTheyInputTheRightFile();
            checker.displayAllSuggestedWords();
        }
        catch(FileNotFoundException fileNotFound) {
            myLogger.severe("Make sure that the reference wordsSpelledCorrectlyFile has the right file path");
        }
    }

    private void displayAllSuggestedWords() {
        for(Map.Entry<String, TreeSet<String>> allMisspelledWords: this.suggestions.entrySet()) {
            String individualMisspelledWord = allMisspelledWords.getKey();
            myLogger.info(this.displayListOfSuggestionsForOneWord(individualMisspelledWord));
        }
    }

    private void keepAskingUserUntilTheyInputTheRightFile() {
        String path;
        boolean fileNotValid = true;
        while(fileNotValid) {
            try {
                path = this.askUserForFilePath();
                File file = tidyUpPathAndReturnFile(path);
                this.readFileToCheckForErrors(file);
                fileNotValid = false;
            }
            catch(FileNotFoundException fileNotFoundException) {
                myLogger.severe("File cannot be found. Try again please.");
            }
        }
    }

    private String askUserForFilePath() {
        Scanner scanner = new Scanner(System.in);
        myLogger.info("Enter a file path name that is going to be spell checked.");
        return scanner.next() + "\n";
    }

    private File tidyUpPathAndReturnFile(String path) {
        File file;
        path = path.replace("\\", "\\\\");
        //for some reason it was inserting a newline so I had to take it out
        path = path.replace("\n", "");
        file = new File(path);
        return file;
    }

    public void readFileWithCorrectlySpelledWords(File file) throws FileNotFoundException {
        try (Scanner in = new Scanner(new FileInputStream(file), "UTF-8")) {
            while(in.hasNext()) {
                String individualWord = in.next().toLowerCase();
                //replace a character that is not a-z with inserting nothing.
                // (I don't check for A-Z because I change the string to lower case)
                individualWord = individualWord.replaceAll("[^a-z]", "");
                setOfCorrectlySpelledWords.add(individualWord);
            }
        }
    }

    public void readFileToCheckForErrors(File file) throws FileNotFoundException {
        try (Scanner in = new Scanner(new FileInputStream(file), "UTF-8")) {
            while(in.hasNext()) {
                String individualWord = in.next().toLowerCase();
                //replace a character that is not a-z with inserting nothing.
                // (I don't check for A-Z because I change the string to lower case)
                individualWord = individualWord.replaceAll("[^a-z]", "");
                if(!wordSpelledCorrectly(individualWord)){
                    findSuggestions(individualWord);
                }
            }
        }
    }

    private boolean wordSpelledCorrectly(String word) {
        return setOfCorrectlySpelledWords.contains(word);
    }

    private void findSuggestions(String misspelledWord) {
        //only find suggestions if you haven't done so already
        if(!suggestions.containsKey(misspelledWord)) {
            findSuggestionsUsingDelete(misspelledWord);
            findSuggestionsByChangingLetters(misspelledWord);
            insertLetterAtAnyPoint(misspelledWord);
            swapNeighboringCharacters(misspelledWord);
            noSuggestionForMisspelledWord(misspelledWord);
        }
    }

    public void findSuggestionsUsingDelete(String wordToBeChanged) {
        for(int index = 0; index < wordToBeChanged.length(); index++) {
            String subsetOfWord = wordToBeChanged.substring(0, index) + wordToBeChanged.substring(index + 1);
            populateSuggestionsIfChangedWordIsSpelledCorrectly(wordToBeChanged, subsetOfWord);
        }
    }

    public void findSuggestionsByChangingLetters(String wordToBeChanged) {
        StringBuilder flexibleWord = new StringBuilder(wordToBeChanged);
        int lengthOfWord = flexibleWord.length();

        for(int letterIndex = 0; letterIndex < lengthOfWord; letterIndex++) {
            flexibleWord.replace(0, lengthOfWord, wordToBeChanged);
            for(int alphabetIndex = 0; alphabetIndex < alphabetArr.length; alphabetIndex++) {
                int nextIndexAfterLetter = letterIndex + 1;
                flexibleWord.replace(letterIndex, nextIndexAfterLetter, String.valueOf(alphabetArr[alphabetIndex]));
                populateSuggestionsIfChangedWordIsSpelledCorrectly(wordToBeChanged, flexibleWord.toString());
            }
        }
    }

    public void insertLetterAtAnyPoint(String wordToBeChanged) {
        StringBuilder flexibleWord = new StringBuilder(wordToBeChanged);
        int lengthOfWord = flexibleWord.length();

        for(int indexOfBuilder = 0; indexOfBuilder < lengthOfWord; indexOfBuilder++) {
            for(int letterInAlpha = 0; letterInAlpha < alphabetArr.length; letterInAlpha++) {
                flexibleWord.insert(indexOfBuilder, alphabetArr[letterInAlpha]);
                populateSuggestionsIfChangedWordIsSpelledCorrectly(wordToBeChanged, flexibleWord.toString());
                int lengthOfStringWithInsertion = flexibleWord.length();
                flexibleWord.replace(0, lengthOfStringWithInsertion, wordToBeChanged);
            }
        }
    }

    public void swapNeighboringCharacters(String misspelledWord) {
        StringBuilder flexibleWord = new StringBuilder(misspelledWord);
        int lengthOfWord = flexibleWord.length();

        for(int index = 0; index < lengthOfWord; index++) {
            if(index < lengthOfWord - 1) {
                int oneAhead = index + 1;
                flexibleWord.setCharAt(index, misspelledWord.charAt(oneAhead));
                flexibleWord.setCharAt(oneAhead, misspelledWord.charAt(index));
                populateSuggestionsIfChangedWordIsSpelledCorrectly(misspelledWord, flexibleWord.toString());
                flexibleWord.replace(0, lengthOfWord, misspelledWord);
            }
        }
    }

    public void populateAlphabetArray() {
        for(int whichLetter = 0; whichLetter < alphabetArr.length; whichLetter++) {
            alphabetArr[whichLetter] = (char) ('a' + whichLetter);
        }
    }

    public Character[] getAlphabetArr() {
        return this.alphabetArr;
    }

    private void populateSuggestionsIfChangedWordIsSpelledCorrectly(String misspelledWord, String correctedWord) {
        if(setOfCorrectlySpelledWords.contains(correctedWord)) {
            if (!suggestions.containsKey(misspelledWord)) {
                suggestions.put(misspelledWord, new TreeSet<String>());
            }
            //keeping suggestions unique
            if(!suggestions.get(misspelledWord).contains(correctedWord)) {
                suggestions.get(misspelledWord).add(correctedWord);
            }
        }
    }

    public void noSuggestionForMisspelledWord(String misspelledWord) {
        if(!suggestions.containsKey(misspelledWord)) {
            suggestions.put(misspelledWord, new TreeSet<String>());
        }
    }

    public String displayListOfSuggestionsForOneWord(String misspelledWord) {
        TreeSet<String> misspelledWordSuggestions = suggestions.get(misspelledWord);
        int sizeOfSuggestions = misspelledWordSuggestions.size();
        if(sizeOfSuggestions == 0) {
            return misspelledWord + ": (no suggestions)\n";
        }

        return giveSuggestionsForOneWord(misspelledWord, misspelledWordSuggestions, sizeOfSuggestions).toString();
    }

    private StringBuilder giveSuggestionsForOneWord(String misspelledWord, TreeSet<String> misspelledWordSuggestions, int sizeOfSuggestions) {
        StringBuilder builder = new StringBuilder();
        builder.append(misspelledWord + ": ");

        int index = 0;
        for(String correctlySpelledWord: misspelledWordSuggestions) {
            String wordWithOrWithoutComma = index != sizeOfSuggestions - 1 ? correctlySpelledWord + ", " : correctlySpelledWord;
            builder.append(wordWithOrWithoutComma);
            index++;
        }
        builder.append("\n");
        return builder;
    }



    public HashSet<String> getSetOfCorrectlySpelledWords() {
        return this.setOfCorrectlySpelledWords;
    }

    public File getWordsSpelledCorrectlyFile() {
        return this.wordsSpelledCorrectlyFile;
    }
}
