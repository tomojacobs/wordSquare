# wordSquare
Word Square coding challenge Naimuri

* Author: Thomas Jacobs
* Version: 1.0
* Contact: tom.o.jacobs@hotmail.com


The challenge
--------------------
Taking an input string create a wordsquare that satisfies the input, the input states a size for the square and the letters it must use.
The words chosen should spell an english word that exists in the prodived dictionary file.
The words should be correct from left to right but also top to bottom for example:

* rose
* oven
* send
* Ends

* - The words spelt are rose, oven, send and ends, these words are correct spelt down and across.

The words should also only be chosen if they can be made from only the letters in the given input string for example:

* eeeeddoonnnsssrv

No more characters can be used but for reoccuring letters they can i.e. spread the four e's across the words in the string.
- Thats it, a simple game


Input
--------------------
A string comprised of a number followed by a space then a collection of letters (the number of letters should be the first number squared)

Example
-  4 eeeeddoonnnsssrv


Output
--------------------
A valid word square that satisfies the rules.

Example 
  - rose
    oven
    send
    Ends


How the program works
--------------------

The program is made of 3 main sections, it gets the users input, filters the dictionary for words that initially satisfy the right length and letters from the input string then
recursively calls a function to try additional words until the wordSqaure is of the input size.

Methods:
start()
getInput()
readDictionar()
filterDictionary()
buildSquare()
getWord()
duplicateWordsCheck()
removeLettersFromInputString()
updatePrefix()
lettersExistInInputString()


User Input - 
Function:
    getInput();

Description:
    Gets the users input and splits it to usable variables with some user feedback for confirmation.



Read dictionary - 
Function:
    readDictionary()
    \/
    filterDictionary()
    \/
    lettersExistInInputString()

Description:
    Loops through each word in the dictionary provided and attempts to filter them passing to the filterDictionary method



Build Square - 
Function:
    buildSquare()
    \/
    lettersExistInInputString | removeLettersFromInputString() | getWord()
                                                                 \/
                                                                 updatePrefix() | duplicateWordsCheck() | lettersExistInInputString() | removeLettersFromInputString()

Description:
    Recursive function that calls itself to try and find the next word that fits into the word square loop through filtered Words produced in Read dictionary step to check.
    If the word meets the conditions to input (if the word starts with the prefix, isn't a duplicate word already in the wordSquare and its letters exist in whats left of the input string) add it to the square and remove the letters from the input string. When the square has 4 words print the square if not call itself for the next word.
    If no new words can be found to match the prefix, add the letters back to the input string, remove the word from the wordSquare and try the next word.

Thanks
- Thomas Jacobs