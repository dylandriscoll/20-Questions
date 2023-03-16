
/*This class represents a QuestionTree object for the game of 20
questions. It has a default guess of "computer" and will add new
questions and answers to the tree if it loses.*/

import java.util.*;
import java.io.*;

public class QuestionTree {
   private QuestionNode root;
   private UserInterface ui;
   private int gamesPlayed;
   private int gamesWon;
   
   public QuestionTree(UserInterface ui) {
      root = new QuestionNode("computer");
      this.ui = ui; 
   }
   
   /*This is my play method. I couldn't get it to work without
   having a QuestionNode parameter so I am calling a private 
   method from this method instead.*/
   public void play() {
      playGame(root);
   }   
    
   /*This method will play one game with the user. If the computer
   guesses the wrong object it will ask the user what the object 
   was and ask for a yes/no question to distinguish it and add 
   those to the tree.*/                
   private void playGame(QuestionNode node) {
      String data = node.getString(node);
      String newQuestion;
      String answer;
      boolean response;
      boolean isAnswer = isAnswer(data);
      //Checks if node is not an answer node
      if (node.right != null && node.left != null) {
         data = node.getString(node);
         //Prints string prior to object if its an answer node
            if (isAnswer) {
               ui.print(
               "Would your object happen to be a " 
               + cutString(data) +"?");
            }
            //Otherwise print the question
            else {
               ui.print(cutString(data));
            }
         response = ui.nextBoolean();
         //Yes answer sends left in tree
            if (response) {
               playGame(node.left);
            }
         //No answer sends right in tree
            else {
               playGame(node.right);
            }
      }
      //Comes here if an answer node has been reached
      else {
         data = node.getString(node);
         ui.print(
         "Would your object happen to be a " 
         + cutString(data) + "?");
         response = ui.nextBoolean();
         //Computer response if it guesses correctly
            if (response) {
                  ui.println("I win!");
                  gamesPlayed++;
                  gamesWon++;
             }
         //Computer response if it guesses incorrectly
             else {
                gamesPlayed++;
                ui.print(
                "I lose. What is your object?");
                answer = "A:" + ui.nextLine();
                ui.print(
                "Type a yes/no question to distinguish " +
                "your item from " + cutString(data) + ":");
                newQuestion = "Q:" + ui.nextLine();
                //Sets node to new question and adds answer
                //to the left node and other guess to  the
                //right node
                node.setValue(newQuestion);
                node.addNode(node, answer);
                node.addNode(node, data);
             }
      }
   }
   
   /*Helper method to see if current node value is an answer*/
   public boolean isAnswer (String value) {
      if (value.startsWith("A:")) {
         return true;
      }
      else {
         return false;
      }
   }
   
   /*Helper method to cut the node string after the :*/
   public String cutString (String data) {
      int index = data.indexOf(":");   
      String dataCut = data.substring(index+1, data.length());
      dataCut = dataCut.trim();
      return dataCut;
   }
   
   /*Saves the current tree to a text file.*/
   public void save(PrintStream output) {
    saveSubtree(root, output);
   }
   
   /*Helper method for saving left and right sides of tree.*/
   private void saveSubtree(QuestionNode current, PrintStream output) {
      if (current == null) {
        return;
   }
      output.println(current.getString(current));
      saveSubtree(current.left, output);
      saveSubtree(current.right, output);
   }
  
   /*Loads a tree into the program from a text file.*/
   public void load(Scanner input) {
      root = root.addNode(null, input.nextLine());
      root.loadSubtree(root, input);
   }
   
   /*Getter method for total games played*/
   public int totalGames() {
      return gamesPlayed;
   }
   
   /*Getter method for total games won by the computer.*/
   public int gamesWon() {
      return gamesWon;
   }
   
}
