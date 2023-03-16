/*Programmer: Dylan Driscoll
Class: CS 145
Assignment: Lab 6 20 Questions
Date: 3/2/2023*/

/*This class defines a QuestionNode object. It also has the methods
to create a tree and the helper method for loading a text file. */

import java.util.*;
import java.io.*;

public class QuestionNode {
    private String value;
    public QuestionNode left;
    public QuestionNode right;

    /*Constructor for a QuestionNode object.*/
    public QuestionNode(String value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
   
   /*Getter method for string in a passed node.*/
   public String getString(QuestionNode node) {
      return value;
   }
   
   /*Setter method for string data in a node.*/
   public void setValue(String value) {
      this.value = value;
   }
  
   /*Method to add a node to the tree for nodes after the computer
   loses or after the user loads a text file..*/
   public QuestionNode addNode(QuestionNode current, String value) {
      //Base case
      //This is the only case used if called from loadSubtree
      if (current == null) {
         return new QuestionNode(value); 
      }
      //Case if left node is full
      else if (current.left != null) {
         //If left node is an answer node go right
         if (current.left.value.startsWith("A:")) {
            current.right = addNode(current.right, value);
            return current;
         }
         //Otherwise go left
         else {
         current.left = addNode(current.left, value);
         return current;
         }
      }
      else {
         current.left = addNode(current.left, value);
         return current;
      }
   }
   
   /*Helper method for creating a tree from a text file.*/
   public void loadSubtree(QuestionNode current, Scanner input) {
      //Returns if current is null
      if (current == null) {
         return;
      }
      //If current node isnt a leaf node, recurses loading the left
      //branches then the right branches.
      if (!current.value.startsWith("A:")) {
        current.left = addNode(current.left, input.nextLine());
        loadSubtree(current.left, input);
        current.right = addNode(current.right, input.nextLine());
        loadSubtree(current.right, input);
      }
   } 
} 
