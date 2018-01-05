/*
 * David Meribe 
 * 1Cor 2:16; Col 2:3 
 */

package circlethedot_uottawa;
import java.util.Random;

import  circlethedot_uottawa.Point;

public class GameModel implements Cloneable{

public static final int AVAILABLE = 0;
public static final int SELECTED = 1;
public static final int DOT = 2;
	
private int num_steps; //number of steps taken so far
private int size = 9; // size of the board, default size is 9

private int [][] status; // status array to keep track of the the status of each dot
private Point blueDot; // current position of blue dot
private static final int INITIAL_PROBA = 10; //probably of dot being initially selected (10%)

private Random generator;

/**
 * Constructor to initialize the model to a given size of board.
 * 
 * @param size
 *            the size of the board
 */
public GameModel(int size){
	this.size = size;
	generator = new Random();
	blueDot = null;
	//reset
	reset();
}

/**
 * Resets the model to (re)start a game. The previous game (if there is one)
 * is cleared up . The blue dot is positioned as per instructions, and each 
 * dot of the board is either AVAILABLE, or SELECTED (with
 * a probability 1/INITIAL_PROBA). The number of steps is reset.
 */
public void reset(){
    int row, col; //temp. for random column, row generation
    
	status = new int[size][size];
	//initialize all to available
	for(int i = 0; i < size; i++){
		for(int j = 0; j< size; j++){
			status[i][j] = AVAILABLE;

		}
	}
	//generate blue dot initial position
	if (size%2 ==0){
		row = size/2 - generator.nextInt(2);
		col = size/2 - generator.nextInt(2);
		
		blueDot = new Point(row,col); 
		
	}else{
		row = (size/2 + 1) - generator.nextInt(3);
		col = (size/2 + 1) - generator.nextInt(3);
		
		blueDot = new Point(row,col); 
		
	}
	status[blueDot.getX()][blueDot.getY()] = DOT;	
	System.out.println(blueDot.getX()+ " " +blueDot.getY());
	  for(int i = 0; i < size; i++){
          for(int j = 0; j < size; j++){
              if( status[i][j] != DOT){
                  if(generator.nextInt(INITIAL_PROBA) == 0){
                      status[i][j] = SELECTED;
                  }
              }
          }
      }
	  num_steps = 0;
	
}

/**
 * Getter <b>class</b> method for the size of the game
 * 
 * @return the value of the attribute sizeOfGame
 */  
public  int getSize(){                          
return size;
}

/**
 * returns the current status (AVAILABLE, SELECTED or DOT) of a given dot in the game
 * 
 * @param i
 *            the x coordinate of the dot
 * @param j
 *            the y coordinate of the dot
 * @return the status of the dot at location (i,j)
 */   
public int getCurrentStatus(int i, int j){
  return status[i][j];          // it's selected or available or bluedot       
}

/**
 * Sets the status of the dot at coordinate (i,j) to SELECTED, and 
 * increases the number of steps by one
 * 
 * @param i
 *            the x coordinate of the dot
 * @param j
 *            the y coordinate of the dot
 */ 

public void select(int i, int j){
    status[i][j]= SELECTED;
    num_steps++; 
}

/**
 * Puts the blue dot at coordinate (i,j). Clears the previous location 
 * of the blue dot. If the i coordinate is "-1", it means that the blue 
 * dot exits the board (the player lost)
 *
 * @param i
 *            the new x coordinate of the blue dot
 * @param j
 *            the new y coordinate of the blue dot
 */   

public void setCurrentDot(int i, int j){
 status[blueDot.getX()][blueDot.getY()] = AVAILABLE;         
// pass on "-1" to remove current dot at end  of the game 
 if(i != -1){
   status[i][j]= DOT;
   blueDot.reset(i, j);
 }
}

/**
 * Getter method for the current blue dot
 * 
 * @return the location of the current blue dot
 */

public Point getCurrentDot(){                    
 return blueDot;
}

/**
 * Getter method for the current number of steps
 * 
 * @return the current number of steps
 */   

public int getNumberOfSteps(){
     return num_steps;                
}

/**
 * Getter method for the ``model'' array
 * 
 * @return the model array
 */   
public int[][] getModel(){
    return status;
}

/* 
 * Clone method to enable game saving
 */
public Object clone() throws CloneNotSupportedException{  
	   // super.clone();     
	     GameModel cloned = (GameModel)super.clone();

	        cloned.blueDot = new Point(getCurrentDot());
	       
	        cloned.status = (int[][]) getModel().clone();
	       
	        return cloned;  
}

}
