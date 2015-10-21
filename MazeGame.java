
import java.util.*;
import java.io.*;


public class MazeGame 
{
	public int [] positionStackX;          //stack for x positions
	  public int [] positionStackY;        //stack for y positions
	  public int capacityX;                //capacity for x stack
	  public int capacityY;                //capacity for y stack
	  public int sizeX;                    // size for x stack
	  public int sizeY;                    // size for y stack
	  public int topX;                     // top for x stack
	  public int topY;                     // top for y stack 
	  public static final int INITIAL_CAPACITYX = 50;  // x stacak initial capacity
	  public static final int INITIAL_CAPACITYY = 50;  // y stack initial capacity
	  
	
	  int rows, cols, dim;
	  int [] dimensions = new int [2];        //stores dimensions of maze
	  int [] startingPositions = new int [2];  //stores starting positions of maze
	  int [] endingPositions = new int [2];    //stors ending positions of maze
	  int [][] maze;
	  
	  //constructor of stack
	  public MazeGame()
	  {
		  positionStackX = new int [INITIAL_CAPACITYX];
		  positionStackY = new int [INITIAL_CAPACITYY];
		  capacityX = INITIAL_CAPACITYX;
		  capacityY = INITIAL_CAPACITYY;
		  sizeX = 0;
		  sizeY =0;
		  topX = -1;
		  topY = -1;
	  }
	  
	  //reads and creates maze from text file, gets dimensions, values, and starting and ending positions  
	  public void createMaze(String fileName)
		{
		    // open file for reading
			Scanner inputStream = null;
			try
			{
				inputStream = new Scanner (new FileInputStream (fileName));
			}
			catch (FileNotFoundException e)
			{
				System.out.println("Error: file not found or could not be opened!");
				System.exit(0);
			}

			String line;
			//get dimensions of the array				    
				line = inputStream.nextLine();
				dim = Integer.parseInt(line);
				rows = (dim/10);
				cols = (dim%10);
				
			maze= new int [rows][cols];

			//assign values to matrix coordinates

			int size = (rows * cols);
			int p = 0;
			int j=0;
			

			while(inputStream.hasNextLine())
			{   //read line by line
				line = inputStream.nextLine();
				System.out.println();
				
				//assign each line to a string
				String s = line;
				int k=0;

				  for (char c : s.toCharArray())
				  {
					  if (p >= size)
						 break;
					  int value = Integer.parseInt(String.valueOf(c));
					  maze[j][k] = value;
					  //CHECKPOINT: parses each digit: PASS
					  p++;
					  k++;
				  }

				j++;

				if (j == rows )
					break;
			}

			
			//parse starting positions 
			int kk =0;
			while(inputStream.hasNextLine())
			{
				line =inputStream.nextLine();
				
				String s = line;			
				for (char c : s.toCharArray())
				  {
					  int value = Integer.parseInt(String.valueOf(c));
					  startingPositions[kk] = value;
					  kk++;
					  
					  if(kk==2)
						  break;				  			 
				}
				
				if(kk==2)
					  break;
			}
			
			//parse ending positions
					int pp =0;
					while(inputStream.hasNextLine())
					{
						line =inputStream.nextLine();
						
						String s = line;			
						for (char c : s.toCharArray())
						  {
							  int value = Integer.parseInt(String.valueOf(c));
							  endingPositions[pp] = value;
							  pp++;
							  
							  if(pp==2)
								  break;				  			 
						}
						
						if(pp==2)
							  break;
					}
					
			inputStream.close();

		}
	  

	  //adds at the top of Xstack 
	  public void pushX(int dataX)
	  {
		  if (sizeX == capacityX)
			  reallocateX();
		  
		  topX = topX +1;
		  positionStackX[topX] = dataX;
		  sizeX = sizeX +1;
	  }
	  
	  //adds at the top of Ystack
	  public void pushY(int dataY)
	  {
		  if (sizeY == capacityY)
			  reallocateY();
		  
		  topY = topY +1;
		  positionStackY[topY] = dataY;
		  sizeY = sizeY +1;
	  }
	  
	  
	  //removes from top of X stack
	  public int popX()
	  {
		  if (isEmptyX())
			  throw new NoSuchElementException("Error: stacked tried to pop while empty!");
		  
		  int data = positionStackX[topX]; //remove from top
		  topX = topX-1;
		  sizeX = sizeX -1;
		  return data;
	  }
	  
	  //removes from top of Y stack
	  public int popY()
	  {
		  if (isEmptyY())
			  throw new NoSuchElementException("Error: stacked tried to pop while empty!");
		  
		  int data = positionStackY[topY]; //remove from top
		  topY = topY-1;
		  sizeY = sizeY -1;
		  return data;
	  }
	  
	  //check whether X stack is empty
	  public boolean isEmptyX()
	  {
		  return (sizeX ==0);
	  }
	  
	  //check whether Y stack is empty
	  public boolean isEmptyY()
	  {
		  return (sizeY ==0);
	  }
	 
	  //increase capacity of X stack
	  public void reallocateX()
	  {
		  capacityX = 2*capacityX;
		  int [] newPositionStack = new int [capacityX];
		  
		  for(int i=0; i<sizeX; i++)
			  newPositionStack[i] = positionStackX[i];
		  
		  positionStackX = newPositionStack;
	  }
	  
	  //increase capacity of Y stack
	  public void reallocateY()
	  {
		  capacityY = 2*capacityY;
		  int [] newPositionStack = new int [capacityY];
		  
		  for(int i=0; i<sizeY; i++)
			  newPositionStack[i] = positionStackY[i];
		  
		  positionStackY = newPositionStack;
	  }
	  
	 /************************************************************************/
	  //this is the code that plays the maze game
	  
	  //marks current location as visited
	  public int visited(int x, int y, int [][] maze)
	  {  
		         maze[x][y] = 1;
		          return (maze[x][y] );          
	  }
	  
	  //checks east of current location
	  public int checkEast(int x, int y)
	  {
	  	 int result;
	  	 
	  	if ((y == (cols-1) ) || (maze[x][(y+1)] == 1 ))  //if there is a wall or there is a 1
	  		result =1;
	  	else
	  		result =0;
	      return result;
	  	
	  }
	  
	  //moves x coordinate one value to the east
	  public int eastX(int x) 
	  {  
		  
		  int dx =0;  //disposition of x
		    
	    x= x+dx;
	    return x;
	  }
	  
	  // moves y coordinate one value to the east 
	  public int eastY(int y) 
	  {  
	       int dy=1;  //disposition of Y
	       y= y+dy;
	  	
		  return y;
	  	
	  }
	  
	  //checks south of current location 
	  public int checkSouth(int x, int y)
	  {
	  	 int result;
	  	
	    if((x == (rows-1) ) || (maze[x+1][(y)] == 1 )) //if there is a wall or there is a 1
	  		result =1;
	  	else
	  		result =0;
	      return result;
	  	
	  }
	  
	  //moves X coordinate one value to the south 
	  public int southX(int x) 
	  {   
	         int dx =1;   //disposition of x	
	  	   x = x + dx;
	  	
	  	  return x;
	   }
	  
	  //moves Y coordinate one value to the south
	  public int southY( int y) 
	  {   
	      int dy=0;  //disposition of Y
	      
	  	y = y + dy;
	  	return y;
	   }
	  
	  //checks west of current location
	  public int checkWest(int x, int y)
	  {
	  	 int result;
	  	
	  	if ( (y == 0 ) || (maze[x][y-1] == 1 )) //if there is a wall or a there is a 1
	  		result =1;
	  	else
	  		result =0;
	      return result;	
	  }
	  
	  //moves X coordinate one value to the west
	  public int westX(int x) 
	  {   
	  	int dx=0;     //disposition of X
	  	x = x + dx;
	  	
	  	return x;
	   }
	  
	  //moves Y coordinate one value to the west
	  public int westY(int y) 
	  {  
	  	  int dy=-1; //disposition of Y
	  	   y = y + dy;
	  		
	  	return y;
	   }
	  
	  //checks north of current location
	  public int checkNorth(int x, int y)
	  {
	  	 int result;
	  	  	
	  	if ( (x == 0 ) || (maze[x-1][y] == 1 )) // if there is a wall or there is a one
	  		result =1;
	  	else
	  		result =0;
	      return result;
	  	
	  }
	  
	  //moves X coordinate one value to the north
	  public int northX(int x) 
	  {  
	         int dx =-1; //disposition of X
	         x = x + dx;
	  	
	  	return x; 
	   }
	  
	  //moves Y coordinate one value to the north
	  public int northY(int y) 
	  {   
	      int dy =0;   //disposition of Y
	  	y = y + dy;
	  	
	  	return y; 
	   }
	 

	  /*******************************************************************************/
	//this part of the code solves the maze   
	  
	  public  boolean solveMaze()
	  {
		  boolean answer = false;
		 
		  int currentLocationX;
		  int currentLocationY;
		  //push starting location
		 
		  pushX(startingPositions[0]);  //push starting X coordinate
		  pushY(startingPositions[1]);  //push starting Y coordinate
		  
		  
		  
		  while((isEmptyX() == false) && (isEmptyY() == false))
		  {
			  currentLocationX = popX();  //set current X coordinate
			  currentLocationY = popY();  //set current Y coordinate
			  
			  System.out.println("(" + currentLocationX + " , " + currentLocationY + ")");
			 
			//mark current location as visited
			   visited(currentLocationX, currentLocationY, maze);
			 
			//if we reach the ending positions    
			  if ((currentLocationX == endingPositions[0]) && (currentLocationY == endingPositions[1]))	  
			  {
				  
				  answer = true;
				  break;
			  }
			  
			  else
			  {
			     //push all unvisited OPEN neighbor locations into stack
				//check to see east is unvisited and open
			      if (checkEast(currentLocationX, currentLocationY) == 0) 
			       {
				     pushX(eastX(currentLocationX));       //push east X coordinate onto X stack
			         pushY(eastY(currentLocationY));       // push east Y coordinate onto Y stack
			       }
			  
			       else;
			    //check to see south is unvisited and open
			      if (checkSouth(currentLocationX, currentLocationY)== 0)
			      {
				    pushX(southX(currentLocationX));    //push south X coordinate onto X stack
			        pushY(southY(currentLocationY));    // push north Y coordinate onto Y stack
			      }
			      
			      else;
			    //check to see west is unvisited and open
			      if (checkWest(currentLocationX, currentLocationY)== 0)
			      {
				    pushX(westX(currentLocationX));   //push west X coordinate onto X stack
			        pushY(westY(currentLocationY));  // push north Y coordinate onto Y stack
			      }
			      
			      else;
			    //check to see north is unvisited and open
			      if (checkNorth(currentLocationX, currentLocationY)== 0)
			      {
				    pushX (northX(currentLocationX));  //push north X coordinate onto X stack
			        pushY(northY(currentLocationY));  // push north Y coordinate onto Y stack
			      }
			      else;	  
			    }
		  }
		  
		     
		    	  return answer;     //return results
	  }
	  
	
}
