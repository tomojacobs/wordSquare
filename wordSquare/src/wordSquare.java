import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class wordSquare {

    private static String originalInputString;
    private static String copyInputString;
    private static ArrayList<String> filteredWords = new ArrayList<>();
    private static final File dictionary = new File("/Users/user/Desktop/dev/wordSquare/wordSquare/src/dictionary.txt");
    private static int wordSquareSize;
    private static Scanner scanner;

    public static void main(String[] args) {
        wordSquare wordSquare = new wordSquare();
        wordSquare.start();
    }

    public void start (){
        getInput();
        readDictionary();
        buildSquare();
    }

    public static void getInput() {
        scanner = new Scanner(System.in);
        String cmdInput = scanner.nextLine();
        String[] cmdInputLetters = cmdInput.split(" ");
        wordSquareSize = Integer.parseInt(cmdInputLetters[0]);
        originalInputString = cmdInputLetters[1];
        System.out.println("The word square size is - "+wordSquareSize);
        System.out.println("The word square letters are - \""+originalInputString+"\"");
    }

    public static void readDictionary(){
        try {
            scanner = new Scanner(dictionary);
            while (scanner.hasNextLine()){
                String dictionaryLine = scanner.nextLine();
                filterDictionary(dictionaryLine);
            }
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
    }

    public static void filterDictionary(String dictionaryLineToFilter){
        if(dictionaryLineToFilter.length() == wordSquareSize) {
            String currentDictionaryLineToValidate = dictionaryLineToFilter;
            String tempInputString = originalInputString;
            for (int i = 0; i < currentDictionaryLineToValidate.length(); i++) {
                if (!tempInputString.contains(currentDictionaryLineToValidate.substring(i, i + 1))) {
                    return;
                } else {
                    tempInputString = tempInputString.replaceFirst(currentDictionaryLineToValidate.substring(i, i + 1), " ");
                    currentDictionaryLineToValidate = currentDictionaryLineToValidate.replaceFirst(currentDictionaryLineToValidate.substring(i, i + 1), " ");
                }
            }
            filteredWords.add(dictionaryLineToFilter);
        }
    }

    public static void buildSquare(){
        ArrayList<String> wordSquare;
        for (int i =0; i < filteredWords.size(); i++){
            copyInputString = originalInputString;
            wordSquare = new ArrayList<>();
            wordSquare.add(filteredWords.get(i));
            if (lettersExistInInputString(copyInputString, filteredWords.get(i))){
                copyInputString = removeLettersFromInputString(copyInputString, filteredWords.get(i));
                getWord(wordSquare, 1, "");
            }
        }
        System.out.println("\nEnd of valid words");
    }

    public static void getWord(ArrayList<String> wordSquare, int row, String prefix){
        for (int i =0; i < 1; i++){
           prefix = updatePrefix(wordSquare, prefix, row);
           for (int j=0; j< filteredWords.size(); j++){
               if (filteredWords.get(j).startsWith(prefix) && !duplicateWordsCheck(wordSquare, filteredWords.get(j)) && lettersExistInInputString(copyInputString, filteredWords.get(j))){
                   wordSquare.add(filteredWords.get(j));
                   copyInputString = removeLettersFromInputString(copyInputString, filteredWords.get(j));
                   if(wordSquare.size() == wordSquareSize)
                   {
                       System.out.println("Word square successful - \n");
                       for (int k=0; k<wordSquare.size(); k++){
                           System.out.println(wordSquare.get(k));
                       }
                       return;
                   }
                   else {
                       getWord(wordSquare,row+1, prefix);
                   }
               }
               else {
                   //System.out.println("Word - "+filteredWords.get(j)+" - does not start with prefix - "+prefix+" - or word already exists in square or its letters don't exists in - \""+copyInputString+"\"");
               }
           }
           copyInputString = copyInputString.concat(wordSquare.get(wordSquare.size()-1));
           wordSquare.remove(wordSquare.get(wordSquare.size()-1));
           prefix = prefix.substring(0, prefix.length()-1);
       }
    }

    public static boolean duplicateWordsCheck (ArrayList<String> square, String word){
        for (int i =0; i< square.size(); i++){
            if (square.get(i).equals(word)){
                return true;
            }
        }
        return false;
    }

    public static String removeLettersFromInputString(String tempInputString, String wordToRemove){
        for (int i =0; i<wordToRemove.length(); i++){
            tempInputString = tempInputString.replaceFirst(wordToRemove.substring(i, i + 1), " ");
        }
        return  tempInputString;
    }

    public static String updatePrefix (ArrayList<String> square, String prefix, int row){
        prefix = "";
        for (int i=0; i<square.size();i++){
            prefix = prefix.concat(square.get(i).substring(row, row+1));
        }
        return prefix;
    }

    public static boolean lettersExistInInputString (String tempInputString, String wordToCheck){
        for (int i = 0; i < wordToCheck.length(); i++) {
            if (!tempInputString.contains(wordToCheck.substring(i, i + 1))) {
                return false;
            } else {
                tempInputString = tempInputString.replaceFirst(wordToCheck.substring(i, i + 1), " ");
                wordToCheck = wordToCheck.replaceFirst(wordToCheck.substring(i, i + 1), " ");
            }
        }
        return true;
    }
}