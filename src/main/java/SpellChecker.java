import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Logger;

public class SpellChecker {
    Scanner scanner = new Scanner(System.in);
    private final static Logger myLogger = Logger.getLogger("com.codedifferently.spellchecker");
    private HashSet<String> setOfWords = new HashSet<String>();

    public static void main(String[] args) {
        SpellChecker checker = new SpellChecker();
        checker.keepAskingUserUntilTheyInputTheRightFile();

        myLogger.info("Size of the set: " + checker.getSetOfWords().size());
        myLogger.info(checker.createListOfWordsFromSet());
    }

    private void keepAskingUserUntilTheyInputTheRightFile() {
        String path;
        boolean fileNotValid = true;
        while(fileNotValid) {
            try {
                path = this.askUserForFilePath();
                File file = this.tidyUpPathAndReturnFile(path);
                this.readFile(file);
                fileNotValid = false;
            }
            catch(FileNotFoundException fileNotFoundException) {
                myLogger.severe("File cannot be found. Try again please.");
            }
        }
    }

    private String askUserForFilePath() {
        Scanner scanner = new Scanner(System.in);

        myLogger.info("Enter a file path name.");

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

    public void readFile(File file) throws FileNotFoundException {
        try (Scanner in = new Scanner(new FileInputStream(file), "UTF-8")) {
            while(in.hasNext()) {
                String individualWord = in.next().toLowerCase();
                //replace a character that is not a-z with inserting nothing.
                // (I don't check for A-Z because I change the string to lower case)
                individualWord = individualWord.replaceAll("[^a-z]", "");
                setOfWords.add(individualWord);
            }
        }
    }

    private String createListOfWordsFromSet() {
        StringBuilder builder = new StringBuilder();
        for (String word : setOfWords) {
            builder.append("This is a word that is spelled correctly: " + word + "\n");
        }
        return builder.toString();
    }

    public HashSet<String> getSetOfWords() {
        return this.setOfWords;
    }
}
