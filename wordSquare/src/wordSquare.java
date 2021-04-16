import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class wordSquare {

    private static String inputString;
    private static ArrayList<String> validWords = new ArrayList<>();
    private static final File dictionary = new File("/Users/user/Desktop/dev/wordSquare/wordSquare/src/dictionary.txt");
    private static int wordSquareSize = 0;
    private static Scanner scanner;

    public static void main(String[] args) {
        getInput();
        readDictionary();
        buildSquare();
    }

    public static void getInput() {
        scanner = new Scanner(System.in);
        String cmdInput = scanner.nextLine();
        String[] cmdInputLetters = cmdInput.split(" ");
        wordSquareSize = Integer.parseInt(cmdInputLetters[0]);
        inputString = cmdInputLetters[1];
        System.out.println("The word square size is - " + wordSquareSize);
        System.out.println("The word square letters are - " + inputString);
    }

    public static void readDictionary(){
        try {
            scanner = new Scanner(dictionary);
            while (scanner.hasNextLine()){
                String dictionaryLine = scanner.nextLine();
                checkWordValidity(dictionaryLine);
            }
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
    }

    public static void checkWordValidity(String dictionaryLineToValidate){
        if(dictionaryLineToValidate.length() == wordSquareSize) {
            String currentDictionaryLineToValidate = dictionaryLineToValidate;
            String currentInputString = inputString;
            for (int i = 0; i < currentDictionaryLineToValidate.length(); i++) {
                if (!currentInputString.contains(currentDictionaryLineToValidate.substring(i, i + 1))) {
                    return;
                } else {
                    currentInputString = currentInputString.replaceFirst(currentDictionaryLineToValidate.substring(i, i + 1), " ");
                    currentDictionaryLineToValidate = currentDictionaryLineToValidate.replaceFirst(currentDictionaryLineToValidate.substring(i, i + 1), " ");
                }
            }
            validWords.add(dictionaryLineToValidate);
        }
    }

    public static void buildSquare(){
        for (String validWord : validWords) {
            System.out.println(validWord);
        }
    }
}

//4 eeeeddoonnnsssrv