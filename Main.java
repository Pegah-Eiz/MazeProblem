/*This program solves a maze and outputs the path it takes as well as 
 *weather or not it was able to solve the maze or not. reads the dimensions of a maze, 
 *the maze matrix and the starting and ending points from a text file.
 * 
 */

import java.util.*;
public class Main
{
    public static void main (String [] args)
    {   
    	//create new scanner object
        Scanner keyboard = new Scanner(System.in);
        //create object of MazeGame class
        MazeGame  mmgg = new MazeGame();
        //read name of file
        System.out.println("Please enter the name of the maze file: ");
        String mazeFile = keyboard.next();
        
        //call on MazeGame method
        mmgg.createMaze(mazeFile);
       
        //print results
        if(mmgg.solveMaze())
            System.out.println("Maze was solved!");
        else
            System.out.println("Maze could not be solved.");
        
    }

}
