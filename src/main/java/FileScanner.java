import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileScanner {
    private static Scanner in = new Scanner(System.in);

    public static Scanner newFileScanner(File file) {
        Scanner ret = new Scanner(System.in);
        File newF = file;

        boolean passed = false;
        while(!passed)
            try {
                ret = new Scanner(newF);
                passed = true;
            } catch (FileNotFoundException e) {
                System.out.print("File not found. Enter existing file: ");
                newF = new File(in.nextLine());
            }

        return ret;
    }

}
