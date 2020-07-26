import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Logger;

public class SpellChecker {
    private HashSet<String> setOfCorrectlySpelledWords = new HashSet<String>();
    TreeMap <String, ArrayList<String>> suggestions = new TreeMap<>();
    private Scanner scanner = new Scanner(System.in);

    private final static Logger myLogger = Logger.getLogger("com.codedifferently.spellchecker");
    private final File wordsSpelledCorrectlyFile = new File("C:\\Kaveesha\\Github\\devCodeDifferently\\stayReadyLabs\\StayReadyLab11\\words_alpha.txt");
    private final Character[] alphabetArr = new Character[26];

    public static void main(String[] args) {
        SpellChecker checker = new SpellChecker();
        checker.populateAlphabetArray();
        try {
            checker.readFile(checker.getWordsSpelledCorrectlyFile(), true);
        }
        catch(FileNotFoundException fileNotFound) {
            myLogger.info("Make sure that the reference wordsSpelledCorrectlyFile has the right file path");
        }

        checker.keepAskingUserUntilTheyInputTheRightFile();

        checker.displayAllSuggestedWords();
    }

    private void displayAllSuggestedWords() {
        for(Map.Entry<String, ArrayList<String>> allMisspelledWords: this.suggestions.entrySet()) {
            String individualMisspelledWord = allMisspelledWords.getKey();
            myLogger.info(this.displayListOfSuggestions(individualMisspelledWord));
        }
    }

    private void keepAskingUserUntilTheyInputTheRightFile() {
        String path;
        boolean fileNotValid = true;
        while(fileNotValid) {
            path = this.askUserForFilePath();
            File file = this.tidyUpPathAndReturnFile(path);
            try {
                this.readFile(file, false);
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

    public void readFile(File file, boolean fileWithCorrectSpelling) throws FileNotFoundException {
        try (Scanner in = new Scanner(new FileInputStream(file), "UTF-8")) {
            while(in.hasNext()) {
                String individualWord = in.next().toLowerCase();
                //replace a character that is not a-z with a inserting nothing.
                // (I don't check for A-Z because I change the string to lower case)
                individualWord = individualWord.replaceAll("[^a-z]", "");
                if(fileWithCorrectSpelling) {
                    setOfCorrectlySpelledWords.add(individualWord);
                }
                else if(!wordSpelledCorrectly(individualWord)){
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

    public void swapNeighboringCharacters(String wordToBeChanged) {
        StringBuilder flexibleWord = new StringBuilder(wordToBeChanged);
        int lengthOfWord = flexibleWord.length();

        for(int index = 0; index < lengthOfWord; index++) {
            if(index < lengthOfWord - 1) {
                int oneAhead = index + 1;
                flexibleWord.setCharAt(index, wordToBeChanged.charAt(oneAhead));
                flexibleWord.setCharAt(oneAhead, wordToBeChanged.charAt(index));
                populateSuggestionsIfChangedWordIsSpelledCorrectly(wordToBeChanged, flexibleWord.toString());
                flexibleWord.replace(0, lengthOfWord, wordToBeChanged);
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

    private void populateSuggestionsIfChangedWordIsSpelledCorrectly(String wordToBeChanged, String changedWord) {
        if(setOfCorrectlySpelledWords.contains(changedWord)) {
            if (!suggestions.containsKey(wordToBeChanged)) {
                suggestions.put(wordToBeChanged, new ArrayList<String>());
            }
            //keeping suggestions unique
            if(!suggestions.get(wordToBeChanged).contains(changedWord)) {
                suggestions.get(wordToBeChanged).add(changedWord);
            }

        }
    }

    public String displayListOfSuggestions(String misspelledWord) {
        ArrayList<String> individualSuggestions = suggestions.get(misspelledWord);
        int sizeOfSuggestions = individualSuggestions.size();

        if(sizeOfSuggestions == 0) {
            String noResultsFound = misspelledWord + ": (no suggestions)";
            return noResultsFound;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(misspelledWord + ": ");
        Collections.sort(individualSuggestions);

        for (int index = 0; index < sizeOfSuggestions; index++) {
            String word = individualSuggestions.get(index);
            String wordWithOrWithoutComma = index != sizeOfSuggestions - 1 ? word + ", " : word;
            builder.append(wordWithOrWithoutComma);
        }
        return builder.toString();
    }

    public boolean isThereSuggestions(String word) {
        return suggestions.get(word).size() != 0;
    }

    public HashSet<String> getSetOfCorrectlySpelledWords() {
        return this.setOfCorrectlySpelledWords;
    }

    public File getWordsSpelledCorrectlyFile() {
        return this.wordsSpelledCorrectlyFile;
    }

}
