package src;

import java.awt.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.*;

// class importance:
    // this class is what is used to call
    // various graphic methods onto the window

public class showPanel extends JPanel {
        
    private BufferedImage img;
    private Graphics2D g2;
    
    // colour values
    Color wYellow = new Color (200,182,83);
    Color wGreen = new Color (108,169,101);
    Color wGray = new Color (120,124,127);
    
    static Scanner in = new Scanner (System.in);
    
    public showPanel(){ // initializes variables
        img = new BufferedImage(Gr12Wordle.WIDTH, Gr12Wordle.HEIGHT, BufferedImage.TYPE_INT_RGB);
        g2 = (Graphics2D)img.getGraphics(); // returns where image is
    }
    
    public void paintComponent(Graphics g){ 
        // paints to window
        g.drawImage(img, 0, 0, null);
        
    }
    
    // image memory methods
    
    public void menu(){        
        // background
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, Gr12Wordle.WIDTH, Gr12Wordle.HEIGHT);
        
        // font
        Font mDefault = new Font ("Helvetica Neue", Font.ITALIC, 40);
        g2.setFont(mDefault);
        g2.setColor(Color.white);
        
        // text
        g2.drawString("1 - START GAME", 199, 540);
        g2.drawString("2 - HOW TO PLAY", 194, 615);
        g2.drawString("3 - LEADERBOARD", 184, 690);
        g2.drawString("4 - EXIT", 290, 765);
        
        // wordle squares
        g2.setColor(wYellow);
        g2.fillRect(20, 140, 100, 100);
        g2.fillRect(356, 140, 100, 100);
        g2.fillRect(468, 140, 100, 100);
        g2.setColor(wGray);
        g2.fillRect(132, 140, 100, 100);
        g2.setColor(wGreen);
        g2.fillRect(244, 140, 100, 100);
        g2.fillRect(580, 140, 100, 100);
        
        // wordle letters
        Font Wordle = new Font ("Helvetica Neue", Font.BOLD, 70);
        g2.setFont(Wordle);
        g2.setColor(Color.white);
        g2.drawString("W", 37, 217);
        g2.drawString("O", 154, 217);
        g2.drawString("R", 268, 217);
        g2.drawString("D", 380, 217);
        g2.drawString("L", 498, 217);
        g2.drawString("E", 606, 217);
        
        repaint();
    }
    
    public void resetGrid(){ // resets grid after game over or play again
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, Gr12Wordle.WIDTH, Gr12Wordle.HEIGHT);
        
        // grid
        g2.setColor(wGray);
        int y = 150;
        
        for (int i = 0; i < 6; i++) {
            int x = 80;
            for (int j = 0; j < 5; j++) {
                g2.drawRect(x, y, 100, 100);
                x+=110;
            }
            y+=110;
        }
        
        g2.setColor(Color.WHITE);
        g2.setFont(new Font ("Helvetica Neue", Font.BOLD, 70));
        g2.drawString("WORDLE", 198, 80);
        repaint();
    }
    
    public void rowEdit (int x, int rounds, int n, String [][] uWord){
    // edits the rows as needed
    
        if (n == 0) { // deletes previous letter
            g2.setColor(Color.BLACK);
            g2.fillRect((80+((x-1)*110)), (150 +(rounds-1)*110), 100, 100);     
            
            g2.setColor(wGray);
            g2.drawRect((80+((x-1)*110)), (150 +(rounds-1)*110), 100, 100);
        }
        
        if (n == 1){ // inserts key pressed letter
            g2.setColor(Color.white);
            Font word = new Font ("Helvetica Neue", Font.BOLD, 70);
            g2.setFont(word);
            
            // inserts as they come
            if (uWord[rounds-1][x].equalsIgnoreCase("I")) {
                g2.drawString((uWord[rounds-1][x]).toUpperCase(), (120 + (x*110)), (223 +(rounds-1)*110));
            } else if (uWord[rounds-1][x].equalsIgnoreCase("M") || uWord[rounds-1][x].equalsIgnoreCase("W")) {
                g2.drawString((uWord[rounds-1][x]).toUpperCase(), (98 + (x*110)), (223 +(rounds-1)*110));
            } else {
                g2.drawString((uWord[rounds-1][x]).toUpperCase(), (107 + (x*110)), (223 +(rounds-1)*110));
            }
        }
        
        if (n == 2){
            for (int i = 0; i < 5; i++) {
                g2.setColor(Color.BLACK);
                g2.fillRect((80+((i)*110)), (150 +(rounds-1)*110), 100, 100);
                g2.setColor(wGray);
                g2.drawRect((80+((i)*110)), (150 +(rounds-1)*110), 100, 100);
            }
        }
        repaint();
    }
    
    public void fill (int n, int rounds, int x, String [][]uWord){
    // fills each square with colours depending on if the letter is correct or not
        switch (n){
            case 0:
                g2.setColor(wGreen);
                break;
            case 1:
                g2.setColor(wYellow);
                break;
            case 2:
                g2.setColor(wGray);
                break;
            default:
                g2.setColor(wGray);
        }
        
        g2.fillRect((80+(x*110)), (150 +(rounds-1)*110), 100, 100);
        
        g2.setColor(Color.white);
        Font word = new Font ("Helvetica Neue", Font.BOLD, 70);
        g2.setFont(word);
        if (uWord[rounds-1][x].equalsIgnoreCase("I")) {
            g2.drawString((uWord[rounds-1][x]).toUpperCase(), (120 + (x*110)), (223 +(rounds-1)*110));
        } else if (uWord[rounds-1][x].equalsIgnoreCase("M") || uWord[rounds-1][x].equalsIgnoreCase("W")) {
             g2.drawString((uWord[rounds-1][x]).toUpperCase(), (98 + (x*110)), (223 +(rounds-1)*110));
        } else {
            g2.drawString((uWord[rounds-1][x]).toUpperCase(), (107 + (x*110)), (223 +(rounds-1)*110));
        }
        
        repaint();
    }
    
    public void popUp (String str, int x, int y) throws InterruptedException{
        // displays messages of warning
            // ex. not in word list
            // ex. word not complete
        g2.setColor(Color.white);
        g2.fillRect(250, 95, 210, 40);
        g2.setColor(Color.black);
        Font wordList = new Font ("Helvetica Neue", Font.BOLD, 20);
        g2.setFont(wordList);
        g2.drawString(str, x, y);
        
        repaint();
        
        Thread.sleep (1800);
        g2.fillRect(250, 95, 210, 40);
        
        repaint();
    }
    
    public void howToPlay(){ // how to play method
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, Gr12Wordle.WIDTH, Gr12Wordle.HEIGHT);
        
        g2.setFont(new Font ("Times New Roman", Font.BOLD, 70));
        g2.setColor(Color.WHITE);
        g2.drawString("How To Play", 150, 80);
        
        g2.setFont(new Font ("Times New Roman", Font.ITALIC, 38));
        g2.drawString("Guess the Wordle in 6 tries.", 132, 137);

        g2.setFont(new Font ("Helvetica Neue", Font.PLAIN, 25));
        g2.drawString("・Each guess must be a valid 5-letter word.", 20, 227);
        g2.drawString("・The colour of the tiles will change to show how close", 20, 277);
        g2.drawString("your guess was to other word.", 45, 307);
        
        g2.setFont(new Font ("Helvetica Neue", Font.BOLD, 25));
        g2.drawString("Examples", 45, 377);
        
        g2.setColor(wGray);
        String str = "";
        String [] word0 = {"W","E","A","R","Y"};
        String [] word1 = {"P","I","L","L","S"};
        String [] word2 = {"V","A","G","U","E"};
        
        for (int i = 0; i < 3; i++) {
            g2.setColor(Color.white);
            g2.setFont(new Font ("Helvetica Neue", Font.PLAIN, 25));    
            switch (i){
                case 0:
                    str = "W is in the word and in the correct spot.";
                    g2.drawString(str, 45, 510);
                case 1:
                    str = "I is in the word but in the wrong spot.";
                    g2.drawString(str, 45, 630);
                case 2:
                    str = "U is not in the word in any spot.";
                    g2.drawString(str, 45, 750);
            }
            
            for (int j = 0; j < 5; j++) {
            g2.setFont(new Font ("Helvetica Neue", Font.BOLD, 40));
                g2.setColor(wGray);
                g2.drawRect(45 + (j*70), 410 + (i*120), 60, 60); // out line
                if (i == 0) {
                    if (j == 0) {
                        g2.setColor(wGreen);
                        g2.fillRect(45 + (j*70), 410 + (i*120), 61, 61); 
                        g2.setColor(Color.white);
                        g2.drawString(word0[j], 55, 455);
                    } else {
                    g2.setColor(Color.white);
                    g2.drawString(word0[j], 60 + (j*70), 455);
                    }
                } else if (i == 1) {
                    if (j == 1) {
                        g2.setColor(wYellow);
                        g2.fillRect(45 + (j*70), 410 + (i*120), 61, 61); 
                        g2.setColor(Color.white);
                        g2.drawString(word1[j], 60 + (j*78), 450 + (i*125));
                    } else {
                    g2.setColor(Color.white);
                    g2.drawString(word1[j], 60 + (j*70), 450 + (i*125));
                    }
                } else {
                    if (j == 3) {
                        g2.setColor(wGray);
                        g2.fillRect(45 + (j*70), 410 + (i*120), 61, 61);
                        g2.setColor(Color.white);
                        g2.drawString(word2[j], 60 + (j*70), 450 + (i*123));
                    } else {
                    g2.setColor(Color.white);
                    g2.drawString(word2[j], 60 + (j*70), 450 + (i*123));
                    }
                }  
            }

            g2.setFont(new Font ("Times New Roman", Font.ITALIC, 30));
            g2.drawString("Click Enter to Return to Main Menu", 133, 805);

        }
        
        repaint();
    }
    
    public void mainLeaderboard(int [] array){
    // main leaderboard accessed from main menu
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, Gr12Wordle.WIDTH, Gr12Wordle.HEIGHT);

        // interperets the highest score count as if it was 100%
        // uses it to find the percentage of the other scores
        // displays based on the highest score
        
        int percent [] = new int [array.length]; // to hold percentages as double
        double h = 0; // to keep track of the highest vaulue
        int g = 0; // number of games won

            for (int i = 0; i < percent.length; i++) { // finding the highest value to compare to the rest of the scores
                if (array[i] > h) {
                    h = array[i];
                }
                g+=array[i];
            }
            
        g2.setFont(new Font ("Times New Roman", Font.BOLD, 70));
        g2.setColor(Color.WHITE);
        g2.drawString("Leaderboard", 145, 80);
        g2.setFont(new Font ("Times New Roman", Font.PLAIN, 30));
        g2.drawString("Games Won: " + Integer.toString(g), 250, 130);
        g2.setFont(new Font ("Times New Roman", Font.ITALIC, 30));
        g2.drawString("Click Enter to Return to Main Menu", 133, 785);
          
        g2.setFont(new Font ("Helvetica Neue", Font.BOLD, 25)); 
        
            for (int i = 0; i < percent.length; i++) {
                g2.setColor(wGray);
                double d = Double.valueOf((array[i]/h)*500); // 500 is the amount of pixels it occupies
                percent[i] = (int) Math.round(d);
                g2.fillRect(80, 275 + (i*75), percent[i]+20, 50);
                
                g2.setColor(Color.white);
                g2.drawString(Integer.toString(i+1), 35, 308 + (i*75));
                if (array[i] == 0) {
                    g2.drawString(Integer.toString(array[i]), (percent[i]+83), 308 + (i*75));
                } else if (array[i] >= 10){
                    g2.drawString(Integer.toString(array[i]), (percent[i]+73), 308 + (i*75));
                } else {
                    g2.drawString(Integer.toString(array[i]), (percent[i]+80), 308 + (i*75)); 
                }
            }
            
        repaint();
    }
    
    public void end (int [] array, int n, String word){
    // displayed when player wins or loses
        int percent [] = new int [array.length]; // to hold percentages as double
        double h = 0; // to keep track of the highest vaulue
        int g = 0; // number of games won
        
        g2.setColor(Color.BLACK);
        g2.fillRect(100, 160, 500, 630); // x+10, y+20, width-10, length - 20
        
        for (int i = 0; i < percent.length; i++) { // finding the highest value to compare to the rest of the scores
            if (array[i] > h) {
                h = array[i];
            }
            g+=array[i];
        }
        
        g2.setFont(new Font ("Helvetica Neue", Font.BOLD, 25)); 
        
            for (int i = 0; i < percent.length; i++) {
                g2.setColor(wGray);
                double d = Double.valueOf((array[i]/h)*410); // 500 is the amount of pixels it occupies
                percent[i] = (int) Math.round(d);
                g2.fillRect(140, 420 + (i*60), percent[i]+35, 30);
                
                g2.setColor(Color.white);
                g2.drawString(Integer.toString(i+1), 112, 443 + (i*60));
                if (array[i] == 0) {
                    g2.drawString(Integer.toString(array[i]), (percent[i]+149), 443 + (i*60));
                } else if (array[i] >= 10){
                    g2.drawString(Integer.toString(array[i]), (percent[i]+133), 443 + (i*60));
                } else {
                    g2.drawString(Integer.toString(array[i]), (percent[i]+144), 443 + (i*60)); 
                }
            }
            
        if (n == 0) {
            g2.setFont(new Font ("Times New Roman", Font.BOLD, 70));
            g2.setColor(Color.WHITE);
            g2.drawString("You Lost!", 209,225);
        } else if (n == 1) {
            g2.setFont(new Font ("Times New Roman", Font.BOLD, 70));
            g2.setColor(Color.WHITE);
            g2.drawString("You Won!", 209,225);
        }
        g2.setFont(new Font ("Times New Roman", Font.PLAIN, 30));
            g2.drawString("The word was " + word + ".", 229, 270);
        g2.setFont(new Font ("Times New Roman", Font.ITALIC, 25));
        g2.drawString("Click Enter to Play Again", 220, 320);
        g2.drawString("Click Backspace to Return to Main Menu", 138, 350);
        
        repaint();
    }

}
