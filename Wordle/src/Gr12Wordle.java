
// Kaveena Ramkissoon
// Mr. Gonzales
// January 23, 2022
// ICS4U1

package src;

import java.io.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class Gr12Wordle extends JFrame implements KeyListener{
    
    public static Scanner in = new Scanner (System.in);
    
    // all valid words / allowed guessing words
    static ArrayList <String> allowedWords = new ArrayList <String>();
    
    // words allowed to be chosen as the computer word
    static ArrayList <String> gameWords = new ArrayList <String>();
    
    // leaderboard creation
    static int [] arrayL;
    
    // variables for graphics
    
    public static final int WIDTH = 700;
    public static final int HEIGHT = 1000;
    
    // frame to display
    static JFrame w = new JFrame();  
    
    // panel to put things on
    // allows us to call from other class whenever
    static showPanel p = new showPanel();
   
    // holds and updates the letters from the keyboard
        // originally set to 0 so that mainMenu(); continues to run
    static String keyInput = "0";
    
    // main code for game
    
    public static void main(String[] args)throws InterruptedException {
        createWindow();
        wordList(); // initializing all the allowed words
        arrayL = bubbleLeaderboard(); // initializes leaderboard values
        
        mainMenu();

            System.exit(0);
    }
    
    public static void mainMenu ()throws InterruptedException{
    keyInput = "0"; // temp
    p.menu();

        // allows the method to not keep resetting
        // as we wait on the player to choose an option
            // idle loop
        while (keyInput == "0"){
            Thread.sleep(100);
                if (keyInput != "0") {
                    Thread.interrupted();
                    break;
                }   
        }
        
        switch (keyInput){
            case "1": startGame();
                break;
            case "2": p.howToPlay();
                keyInput = "0";
                while (keyInput == "0"){
                    Thread.sleep(100);
                    if (keyInput != "0") {
                        Thread.interrupted();
                        break;
                    }   
                }
                break;
            case "3": p.mainLeaderboard(arrayL);
                keyInput = "0";
                    while (keyInput == "0"){
                        Thread.sleep(100);
                        if (keyInput != "0") {
                            Thread.interrupted();
                            break;
                        }   
                }
                break;
            case "4":
                return;
            default:
                break;
        };
    
    mainMenu(); // loops menu

    }
    
    public static void startGame ()throws InterruptedException{ // main gameplay
    p.resetGrid();
    
    // initializing variables
    int rounds = 0;
    int correctLetters = 0;
    int n = 0;
    String uWord [][] = new String [6][5];
    ArrayList <String> cWord = compSelect(); 

        do {
            correctLetters = 0; // reset
            rounds++; // increase rounds
            uWord = userWord(uWord, rounds);
            correctLetters = letterCompare(uWord, cWord, rounds);
               
        } while (rounds < 6 && correctLetters < 5);

        if (correctLetters == 5 ) {  // if player wins
            n = 1;
            
        // writing points to scoreboard
            try {
                File scoreboard = new File("scoreboard.txt");
                PrintWriter pw = new PrintWriter(new FileWriter(scoreboard,true));
                pw.println(rounds);
                pw.close();
            } catch (IOException e) {}
        
        // updating scoreboard
        arrayL = bubbleLeaderboard();

        } else if (rounds == 6 && correctLetters != 5){ // player loss
            n = 0;
        }
        
        p.end(arrayL, n, (String.join("", cWord)));

        // player idle
        keyInput = "0";
        while (keyInput != "ENTER" && keyInput != "BACKSPACE"){
            Thread.sleep(100);
                if (keyInput == "BACKSPACE") {
                    Thread.interrupted();
                    return;
                } else if (keyInput == "ENTER"){
                    startGame();
                    break;
                }
        }
        
    }
        
    public static ArrayList <String> compSelect(){ // generates a word for guessing
        ArrayList <String> cWord = new ArrayList <String>();

        if (gameWords.isEmpty()) {
        	System.out.println("game words is empty and cannnot select a word");
        	return cWord;
        }
        
        int cSelect = (int)(gameWords.size() * Math.random () + 0); // randomizing a word
        
        for (int i = 0; i < 5; i++) {
            cWord.add((gameWords.get(cSelect)).substring(i,(i+1))); // adding to an ArrayList
        }
        
        return cWord;
    }
    
    public static String[][] userWord(String[][] uWord, int rounds)throws InterruptedException{
    // temp
    String word = "";
    // sends instructions to other methods
    int n = 0;

    // results to / scenarios of keyInput
        do {
            for (int i = 0; i < 6; i++) {
            String prev = "";
            int b = 0;
                if (keyInput != "BACKSPACE") {
                    prev = keyInput;
                } else {
                    b = 1;
                    keyInput = "";
                }
            
            // idle
            while (prev == keyInput || (keyInput == uWord[(rounds-1)][i-1]) || i == 0){
                Thread.sleep(100);
                if ((prev!=keyInput) || (b == 1 && keyInput == "BACKSPACE")) {
                    b = 0;
                    Thread.interrupted();
                    break;
                }
            }
            
            if (keyInput.equalsIgnoreCase("BACKSPACE")) { // deleting letters
                if (i == 5){
                    uWord[(rounds-1)][i-1] = null;
                    n = 0;
                    p.rowEdit(i, rounds, n, uWord);
                    i-=2;
                } else if (i <= 0) {
                    uWord[(rounds-1)][i] = null;
                    n = 0;
                    p.rowEdit(i+1, rounds, n, uWord);
                    i--;
                } else {
                    // all minus by 2
                    uWord[(rounds-1)][i] = null;
                    n = 0;
                    p.rowEdit(i, rounds, n, uWord);
                    i-=2;
                }
            } else if ((i >= 5 && !keyInput.equalsIgnoreCase("ENTER")) || keyInput.equalsIgnoreCase("SPACE") || ((keyInput.trim().isBlank()) && i > 0)){
                // out of bounds
                // white space
                i--;
            } else if (keyInput.equalsIgnoreCase("ENTER") || keyInput.equalsIgnoreCase("￿")){ // click enter, final word submission
                if (i < 5){
                    p.popUp("word not complete", 270, 120);
                    i--; // goes back one
                } else {
                    break;
                }
            
            } else if (keyInput.trim().matches("[a-zA-Z]+") && (keyInput != "ENTER" || keyInput != "SPACE" || keyInput != "BACKSPACE")) { // if it is a letter
                uWord[(rounds-1)][i] = keyInput;
                n = 1;
                p.rowEdit(i, rounds, n, uWord);
                
            } else { // if user inserts non-alphabetical key
                i--;
            }
        }
    
        // temp string to join the letters
        
        word = String.join("", uWord[(rounds-1)]);
        word = word.toLowerCase();
    
        // checking if word exists
        if (!allowedWords.contains(word)) {
            p.popUp("not in word list", 285, 120);
            n = 2;
            int i = 0;
            p.rowEdit(i, rounds, n, uWord); // resets row
        } // pop up
    } while (!allowedWords.contains(word));
    
    return uWord;
    }
    
    public static int letterCompare(String [][] uWord, ArrayList <String> cWord, int rounds){
        //returns the number of correct guesses
        int correctLetters = 0;
        int n = 0;
        
        for (int i = 0; i < 5; i++) {
            if (cWord.contains(uWord[rounds-1][i])) {
            // if the letter is in the word at all
                if (cWord.get(i).equalsIgnoreCase(uWord[rounds-1][i])) {
                // if the letter is in the same place as the user letter
                    n = 0;
                    correctLetters++;
                } else {
                    n = 1;
                }
            } else {
                n = 2;
            }
            p.fill(n, rounds, i, uWord); // fills spots with assigned colour
        }
        return correctLetters;
    }
    
    public static int [] bubbleLeaderboard (){ // sorts all scores from highest to lowest
        ArrayList <Integer> leaderboard = new ArrayList <Integer> (); // leaderboard as an array list
        int [] amount = new int [6]; // holds amount of scores in each row
        
        try {
            FileReader fr = new FileReader("scoreboard.txt");
            Scanner s = new Scanner (fr);
            while (s.hasNextInt()){
                leaderboard.add(s.nextInt());
            }
                s.close();
        }catch (IOException e) {}
        
        if (leaderboard.isEmpty()) {
        	return amount;
        }
        
        int [] array = new int [leaderboard.size()]; // leaderboard as an array
        for (int i = 0; i < leaderboard.size(); i++) {
           array [i] = leaderboard.get(i);
        }
        
        // bubble sort
        for (int i = 0; i < array.length-1; i++) {
            for (int j = 0; j < array.length-1-i; j++) {
                if (array[j] > array [j+1]) {
                    int temp = array[j+1];
                    array[j+1] = array[j];
                    array[j] = temp;
                }
            }
        }

        if (array.length > 0) {
        	return amount = scoreCount(array, 0, amount, 0, 1);
        }
        
        return amount;
    }
    
    public static int [] scoreCount (int [] array, int i, int [] score, int j, int count){
        // should function as a counter to indicate how many of each number is in the array
    
    	if (array.length == 0) { // empty array
    		return score;
    	}
    	
        if (i == (array.length-1)) { // iterated all the way, both ends
            score[j] = count;
            return score;
            
        } else if (array[i] != (j+1)){ // if the number doesn't exist
            score[j] = 0;
            count = 1;
            return scoreCount(array, i, score, j+1, count);
            
        } else if (array[i+1] > array[i]){ // if the number prior is less than
            score[j] = count;
            count = 0;
            return scoreCount(array, i+1, score, j+(array[i+1] - array[i]), count+1);
            
        } else { // if number and number prior are the same
            return scoreCount(array, i+1, score, j, count+1);
            
        }
    }
    
    public static void wordList (){
    // brings words from text file into an arrayList
    // more convenient than having to open and close the file every time
        try {
            FileReader fr = new FileReader("allowedwords.txt"); // words user is allowed to guess
            Scanner s = new Scanner (fr);
            
            while (s.hasNextLine()){
                allowedWords.add(s.nextLine().trim());
            }
                s.close();
        } catch (IOException e) {
        	System.out.println("Error reading allowedwords.txt" + e.getMessage());
        }
        
        try {
            FileReader fr = new FileReader("words.txt"); // words that are valid as cWord
            Scanner s = new Scanner (fr);
            while (s.hasNextLine()){
            	String word = s.nextLine().trim();
                if (!word.isEmpty()) {
                	gameWords.add(word);
                }
            }
                s.close();
        }catch (IOException e) {
        	System.out.println("Error reading words.txt" + e.getMessage());
        }
        
        System.out.println("Loaded " + gameWords.size() + " words into game words.");
    }
    
    
    // window creation
    
    public static void createWindow() {
        w.setSize(WIDTH, HEIGHT);
        w.setResizable(false);
        w.setLocationRelativeTo(null);
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        w.add(p); // implementing panel
        
        // keylistener creation
        Gr12Wordle l = new Gr12Wordle(); 
        w.addKeyListener(l);
        
        w.setVisible(true);
    }
    
    // key listener
    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 10) {
            keyInput = "ENTER";
        } else if (e.getKeyCode() == 32) {
            keyInput = "SPACE";
        } else if (e.getKeyCode() == 8) {
            keyInput = "BACKSPACE";
        } else {
            keyInput = Character.toString(e.getKeyChar());
        }
    }

    // these don't matter / aren't used
    public void keyReleased(KeyEvent e) {
    }
    public void keyTyped(KeyEvent e) {
    }
    

}

