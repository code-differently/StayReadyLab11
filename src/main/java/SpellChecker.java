import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Logger;

public class SpellChecker {

    private static final Logger log = Logger.getGlobal();
    private Scanner scan = new Scanner(System.in);
    private Set<String> set = new HashSet<String>();
    private Set<String> checkSet = new HashSet<String>();
    private Set<String> tSet = new HashSet<String>();

    public void createSet(File file){//begin createSet

        if(fileExists(file)){//begin if file exists
            //scan.useDelimiter("\r\n");
            while(scan.hasNextLine()) {//begin while file has next line

                String[] word = getLine(scan.nextLine());
                for (int i = 0; i < word.length; i++) {//begin for loop placing values in hash Set
                    set.add(word[i]);
                    tSet.add(word[i]);

                }//end for loop placing values in hash set
            }// end while file has next line
        }//end if file exists
    }//end createSet


    public void createCheckSet(File file){//begin createSet

        if(fileExists(file)){//begin if file exists
            //scan.useDelimiter("\r\n");
            while(scan.hasNextLine()) {//begin while file has next line

                String[] word = getLine(scan.nextLine());
                for (int i = 0; i < word.length; i++) {//begin for loop placing values in hash Set
                    checkSet.add(word[i]);

                }//end for loop placing values in hash set
            }// end while file has next line
        }//end if file exists
    }//end createSet

    private String [] getLine(String input){

        input = input.toLowerCase();
        input = input.replaceAll("(?U)[\\p{S}\\p{P}]+", "");
        String[] word = input.split(" ");
        return word;

    }

    private boolean fileExists(File file){

        try {
            scan = new Scanner(file);
            return true;
        }catch (FileNotFoundException e){
            log.info("File not found.");
            return false;
        }

    }

    public String getSet(){

        Object[] arr =  set.toArray();
        Arrays.sort(arr);
        String setValues = "";
        setValues+="[";
        for (int i = 0; i < arr.length; i++){
            setValues+=arr[i];
            if(i<arr.length-1){
                setValues+=", ";
            }else{
                setValues+="]";
            }
        }
        return setValues;
    }

    public String checkSpell(File file){
        createCheckSet(file);
        TreeMap<String,TreeSet<String>> map = new TreeMap<>();
        TreeSet<String> sSet;
        for(String s: checkSet) {
            if(!set.contains(s)){
                sSet = new TreeSet<>();
                deleteLetters(s, sSet);
                changeLetters(s, sSet);
                insertLetters(s, sSet);
                swapLetters(s, sSet);
                if(sSet.isEmpty()){
                    sSet.add("No Suggestions");
                }
                map.put(s, sSet);
            }
        }
       // map.remove("");
        return getMap(map);
    }

    public TreeSet deleteLetters(String w, TreeSet<String> sSet){
        String words = "";
        String temp;

        for(int i = 0; i < w.length(); i++) {
            if(i == 0){
                temp = w.substring(i + 1);
                if(set.contains(temp) && tSet.contains(temp)){
                    //words += temp + " ";
                    sSet.add(temp);
                    tSet.remove(temp);
                }
            }
            else if(i == w.length()-1){
                temp = w.substring(0, i);
                if(set.contains(temp) && tSet.contains(temp)){
                    //words += temp + " ";
                    sSet.add(temp);
                    tSet.remove(temp);
                }
            }
            else{
                temp = w.substring(0, i) + w.substring(i+1);
                 if(set.contains(temp)&& tSet.contains(temp)) {
                     //words += temp + " ";
                     sSet.add(temp);
                     tSet.remove(temp);
                 }
            }
        }
        return sSet;
    }

    public TreeSet changeLetters(String w, TreeSet<String> sSet){
        String words = "";
        String temp = w;
        char [] l = w.toCharArray();
        for(int i = 0; i < w.length(); i++) {
            for(int a = 0; a < 25; a++) {
                l[i] = (char) ('a' + a);
                temp = new String(l);
                if(set.contains(temp) && tSet.contains(temp)) {
                   // words += temp + " ";
                    sSet.add(temp);
                    tSet.remove(temp);
                }
            }
            l[i] = w.charAt(i);

        }
        return sSet;
    }

    public TreeSet insertLetters(String w, TreeSet<String> sSet){
        String words = "";
        String temp = w;
        String insert;
        for(int i = 0; i < w.length(); i++) {
            for(int a = 0; a < 25; a++) {
                insert = Character.toString((char) ('a' + a));
                if(i == 0){
                    temp = insert + w.substring(i);
                    if(set.contains(temp) && tSet.contains(temp)){
                        //words += temp + " ";
                        sSet.add(temp);
                        tSet.remove(temp);
                    }
                }
                else if(i == w.length()-1){
                    temp += insert;
                    if(set.contains(temp) && tSet.contains(temp)){
                        //words += temp + " ";
                        sSet.add(temp);
                        tSet.remove(temp);
                    }
                }
                else{
                    temp = w.substring(0, i) + insert + w.substring(i+1);
                    if(set.contains(temp) && tSet.contains(temp)) {
                        //words += temp + " ";
                        sSet.add(temp);
                        tSet.remove(temp);
                    }
                }
            }
        }
        return sSet;
    }

    public TreeSet swapLetters(String w , TreeSet<String> sSet){
        String words = "";
        String temp;
        char t;
        char [] l;
        for(int i = 0; i < w.length()-1; i++) {
            l = w.toCharArray();
            t = l[i];
            l[i] = l[i+1];
            l[i+1] = t;

            temp = new String(l);
            if(set.contains(temp) && tSet.contains(temp)) {
                //words += temp + " ";
                sSet.add(temp);
                tSet.remove(temp);
            }
        }
        return sSet;
    }

    public String getMap(TreeMap<String, TreeSet<String>> map){

        Object[] arr =  map.keySet().toArray();
        Arrays.sort(arr);
        String mapValues = "";
        for (int i = 0; i < arr.length-1; i++){
            mapValues+=arr[i] + ": " + map.get(arr[i]) + "\n";
        }
        return mapValues;
    }

    public static void main(String [] args){

        SpellChecker sc = new SpellChecker();
        Scanner scan = new Scanner(System.in);

        log.info("Enter a file: ");
        String f = scan.nextLine();
        sc.createSet(new File(f));
        //sc.createSet(new File("words_alpha.txt"));
        log.info("Enter a file to check: ");
        f = scan.nextLine();
        log.info(sc.checkSpell(new File(f)));
        //log.info(sc.checkSpell(new File("letter_from_gandhi.txt")));
    }
}
