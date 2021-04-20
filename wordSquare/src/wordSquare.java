/*
*
* Author: Thomas Jacobs
* Version: 1.0
* Contact: tom.o.jacobs@hotmail.com
* Description: A simple wordSquare game, inputs are a number corresponding to the size of the square and number of letters to a word (rows and columns)
*              followed by a collection of letters that can only be used once as a letter to one of the words. The program will then sift words provided
*              by a dictionary.txt file for words that are the correct length, then sift those words for a square such that each worth across is the same
*              down.
*
* Example:
*           Input: '4 eeeeddoonnnsssrv'
*           Output: rose
*                   oven
*                   send
*                   Ends
*
* */

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
        //Created a new instance of the game and triggers the start method.

        wordSquare wordSquare = new wordSquare();
        wordSquare.start();
    }

    public void start (){
        // Game runs as 3 parts, get the user input, sift the dictionary to match the input string then build the square.

        getInput();
        readDictionary();
        buildSquare();
    }

    public static void getInput() {
        // Gets the users input and splits it to usable variables with some user feedback for confirmation.

        scanner = new Scanner(System.in);
        String cmdInput = scanner.nextLine();
        String[] cmdInputLetters = cmdInput.split(" ");
        wordSquareSize = Integer.parseInt(cmdInputLetters[0]);
        originalInputString = cmdInputLetters[1];
        System.out.println("\nThe word square size is - "+wordSquareSize);
        System.out.println("The word square letters are - \""+originalInputString+"\"");
    }

    public static void readDictionary(){
        // Loops through each word in the dictionary provided and attempts to filter them passing to the filterDictionary method

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
        // For each given word, does it match the word length provided, checks if the words letters exist in the input string by passing to lettersExistInString
        // function, boolean returned for true or false. If true all letters exist so add to filtered word array.

        boolean valid = false;
        if(dictionaryLineToFilter.length() == wordSquareSize) {
            valid = lettersExistInInputString(originalInputString, dictionaryLineToFilter);
            if (valid){
                filteredWords.add(dictionaryLineToFilter);
            }
        }
    }

    public static void buildSquare(){
        // The build square method to recursively try and find the next word that fits, loop through filtered words array to get first word, add it to the square
        // check if the letters exist in the input string then remove the letters from input string. Call get word method to find the next words, once all words
        // have been looped through show end message to user.

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
        System.out.println("\nEnd of words");
    }

    public static void getWord(ArrayList<String> wordSquare, int row, String prefix){
        // Recursive function that calls itself to try and find the next word that fits into the word square, passed in the wordSquare, a row number and current prefix
        // for each word pass wordSquare and row vars to update prefix method. Loop through filteredWords array and check if the word meets the conditions to try
        // and input, if the word starts with the prefix, isn't a duplicate word already in the wordSquare and its letters exist in whats left of the input string.
        // If so add it to the wordSquare remove the letters from the input string, if the wordSquare now has 4 words end and print the square if not call itself for the next word.
        // If no new words can be found to match the prefix, add the letters back to the input string, remove the word from the wordSquare and try the next word.

        for (int i =0; i < 1; i++){
           prefix = updatePrefix(wordSquare, row);
           for (int j=0; j< filteredWords.size(); j++){
               if (filteredWords.get(j).startsWith(prefix) && !duplicateWordsCheck(wordSquare, filteredWords.get(j)) && lettersExistInInputString(copyInputString, filteredWords.get(j))){
                   wordSquare.add(filteredWords.get(j));
                   copyInputString = removeLettersFromInputString(copyInputString, filteredWords.get(j));
                   if(wordSquare.size() == wordSquareSize)
                   {
                       System.out.println("\nWord square successful - \n");
                       for (int k=0; k<wordSquare.size(); k++){
                           System.out.println(wordSquare.get(k));
                       }
                       return;
                   }
                   else {getWord(wordSquare,row+1, prefix);}
               }
               else {
                   //System.out.println("Word - "+filteredWords.get(j)+" - does not start with prefix - "+prefix+" - or word already exists in square or its letters don't exists in - \""+copyInputString+"\"");
               }
           }
           copyInputString = copyInputString.concat(wordSquare.get(wordSquare.size()-1));
           wordSquare.remove(wordSquare.get(wordSquare.size()-1));
       }
    }

    public static boolean duplicateWordsCheck (ArrayList<String> square, String word){
        // Function to take the square and a word and check if that word already exists in the wordSquare, return true or false as boolean to if statement in getWord line '119'.

        for (int i =0; i< square.size(); i++){
            if (square.get(i).equals(word)){
                return true;
            }
        }
        return false;
    }

    public static String removeLettersFromInputString(String tempInputString, String wordToRemove){
        // Function to take the input string and word to remove, remove the letters of that word from the input string and return it, getWord function line '121'.

        for (int i =0; i<wordToRemove.length(); i++){
            tempInputString = tempInputString.replaceFirst(wordToRemove.substring(i, i + 1), " ");
        }
        return  tempInputString;
    }

    public static String updatePrefix (ArrayList<String> square, int row){
        // Function to take the wordSquare and row to make the prefix string and return it, for each word in the square take the
        // rowth letter (letter from each word corresponding to the current row) and concat them together then return the string.
        // Example: for input rose we are now looking for row 3 which must start with the 3rd letter of each word 'se' gets 'se'nds.
        //                    oven

        String prefix = "";
        for (int i=0; i<square.size();i++){
            prefix = prefix.concat(square.get(i).substring(row, row+1));
        }
        return prefix;
    }

    public static boolean lettersExistInInputString (String tempInputString, String wordToCheck){
        // Function to check if the letters of a given word exists in the input string given, loop through each letter of the word to check
        // create a substring of each letter and check if that letter exists in the input string. Try's every letter if none are missing return true.

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