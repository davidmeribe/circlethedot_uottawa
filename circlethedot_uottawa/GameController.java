package circlethedot_uottawa;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import circlethedot_uottawa.utility.LinkedQueue;
import circlethedot_uottawa.utility.LinkedStack;
import circlethedot_uottawa.utility.Pair;
import circlethedot_uottawa.utility.Queue;
import circlethedot_uottawa.view.BoardView;
import circlethedot_uottawa.view.DotButton;
import circlethedot_uottawa.view.GameView;

public class GameController implements ActionListener{
	
	private GameModel model;
	private GameView view;
	//private BoardView board;
	
	private LinkedStack<GameModel> stack;

	/**
     * Constructor used for initializing the controller. It creates the game's view 
     * and the game's model instances
     * 
     * @param size
     *            the size of the board on which the game will be played
     */
	public GameController(int size){
		model = new GameModel(size);
		view = new GameView(model,this);
		view.update();
		
		stack = new LinkedStack<GameModel>();
	}
	 /**
     * Starts the game
     */
    public void start(){
      //does nothing
    	//game starts from constructor.
    }
    
	  /**
     * resets the game
     */
    public void reset(){
        model.reset();
        view.update();
        //add new game to stack
        stack.push(model);
    }
	
    /**
     * Callback used when the user clicks a button or one of the dots. 
     * Implements the logic of the game
     *
     * @param e
     *            the ActionEvent
     */
    
	@Override
	public void actionPerformed(ActionEvent e) {
		 
		if (e.getSource() instanceof DotButton){
			DotButton clicked = (DotButton) e.getSource();
			//set redo enabled Todo
			
			if (model.getCurrentStatus(clicked.getRow(),clicked.getColumn()) ==
					GameModel.AVAILABLE){
				model.select(clicked.getRow(), clicked.getColumn());
				oneStep();
				saveGame();	
			}
		} else if (e.getSource() instanceof JButton){
			JButton clicked = (JButton) e.getSource();
			
			if (clicked.getText().equals("Quit")){
				System.exit(0);
			}else if(clicked.getText().equals("Reset")){
				reset();
			}else if (clicked.getText().equals("Undo")){
				undo();
			}	
		}
		
	}
	
	/**
     * Computes the next step of the game. If the player has lost, it 
     * shows a dialog offering to replay.
     * If the user has won, it shows a dialog showing the number of 
     * steps that had been required in order to win. 
     * Else, it finds one of the shortest path for the blue dot to 
     * exit the board and moves it one step in that direction.
     */
	
	private void oneStep(){
		Point currentDot = model.getCurrentDot();
		
		if(isOnBorder(currentDot)){
			model.setCurrentDot(-1,-1);
			view.update();
			
			Object[] options = {"Play Again", "Quit"};
			
			int n = JOptionPane.showOptionDialog(view,
					"You Lost! Play again maybe?",
					"Lost",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					options,options[0]);
			if(n == 0){
				reset();
			}else{
				System.exit(0);
			}
		}else{
			Point direction = nextDirection();
			if(direction.getX() == -1){
				view.update();
				Object[] options = {"Play Again", "Quit"};
				
				int n = JOptionPane.showOptionDialog(view,
						"Congrats, you won in"+ model.getNumberOfSteps()
						+" steps!\n Play again?",
						"Won",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null, options, options[0]);
				if(n == 0){
					reset();
				}else{
					System.exit(0);
				}
			}else{
				model.setCurrentDot(direction.getX(), direction.getY());
				view.update();
			} 
		}	
	}
	
	/**
     * Does a ``breadth-first'' search from the current location of the blue dot to find
     * one of the shortest available path to exit the board. 
     *
     * @return the location (as a Point) of the next step for the blue dot toward the exit.
     * If the blue dot is encircled and cannot exit, returns an instance of the class Point 
     * at location (-1,-1)
     */
	private Point nextDirection(){
		boolean[][] blocked = new boolean[model.getSize()][model.getSize()];
		
		for(int i=0; i < model.getSize(); i++){
			for(int j=0; j < model.getSize(); j++){
				blocked[i][j] = !(model.getCurrentStatus(i, j)== GameModel.AVAILABLE);
			}
		} 
		Queue<Pair<Point>> queue = new LinkedQueue<Pair<Point>>();
		
		LinkedList<Point> possibleNeighbours = new LinkedList<Point>();
		
		// start with neighbours of the current dot
        // (note: we know the current dot isn't on the border)
        Point currentDot = model.getCurrentDot();

        possibleNeighbours = findPossibleNeighbours(currentDot, blocked);
		
        //randomize the neighbours
        java.util.Collections.shuffle(possibleNeighbours);
        
        for(int i=0; i< possibleNeighbours.size(); i++){
        	Point p = possibleNeighbours.get(i);
        	if(isOnBorder(p)){
        		return p;
        	}
        	queue.enqueue(new Pair<Point>(p,p));
        	blocked[p.getX()][p.getY()] = true;
        }
        
        //start breadth-first search
        while(!queue.isEmpty()){
        	Pair<Point> pointPair = queue.dequeue();
        	possibleNeighbours = findPossibleNeighbours(pointPair.getFirst(),blocked);
        	
        	for(int i=0; i < possibleNeighbours.size(); i++){
        		Point p = possibleNeighbours.get(i);
        		if(isOnBorder(p)){
        			return pointPair.getSecond();
        		}
        		queue.enqueue(new Pair<Point>(p,pointPair.getSecond()));
        		blocked[p.getX()][p.getY()] = true;
        	}
        }
		
        //could not find a way out. Return an outside direction
        return new Point(-1,-1);
	}
	
	/**
     * Helper method: checks if a point is on the border of the board
     *
     * @param p
     *            the point to check
     *
     * @return true iff p is on the border of the board
     */
     
    private boolean isOnBorder(Point p){
        return (p.getX() == 0 || p.getX() == model.getSize() - 1 ||
                p.getY() == 0 || p.getY() == model.getSize() - 1 );
    }

    /**
     * Helper method: find the list of direct neighbours of a point that are not
     * currently blocked
     *
     * @param point
     *            the point to check
     * @param blocked
     *            a 2 dimensional array of booleans specifying the points that 
     *              are currently blocked
     *
     * @return an instance of a LinkedList class, holding a list of instances of 
     *      the class Points representing the neighbours of parameter point that 
     *      are not currently blocked.
     */
    private LinkedList<Point> findPossibleNeighbours(Point point, 
            boolean[][] blocked){

        LinkedList<Point> list = new LinkedList<Point>();
        int delta = (point.getX() %2 == 0) ? 1 : 0;
        if(!blocked[point.getX()-delta][point.getY()-1]){
            list.add(new Point(point.getX()-delta, point.getY()-1));
        }
        if(!blocked[(point.getX()-delta)+1][point.getY()-1]){
            list.add(new Point((point.getX()-delta)+1, point.getY()-1));
        }
        if(!blocked[point.getX()-1][point.getY()]){
            list.add(new Point(point.getX()-1, point.getY()));
        }
        if(!blocked[point.getX()+1][point.getY()]){
            list.add(new Point(point.getX()+1, point.getY()));
        }
        if(!blocked[point.getX()-delta][point.getY()+1]){
            list.add(new Point(point.getX()-delta, point.getY()+1));
        }
        if(!blocked[(point.getX()-delta)+1][point.getY()+1]){
            list.add(new Point((point.getX()-delta)+1, point.getY()+1));
        }
        return list;
    }
    
    /** Method to undo a move and restore prvious state of th egame
     */ 

   private void undo(){
   
     if (stack.isEmpty()){
        throw new IllegalArgumentException("cant undo");
       
     }
     try{
         stack.pop();
         model= (GameModel) stack.peek();
         view.update();
     }catch(IllegalStateException e1){
         throw new IllegalStateException("Can't Undo right now");    
     } finally{
     view.update();
     }
   }

   /** Method to clone game and set undo button enabled if possible
    */
  @SuppressWarnings("unchecked")
  private void saveGame(){
    try{
       GameModel clone = (GameModel) model.clone();
       stack.push(clone);
  }
    catch(CloneNotSupportedException e){
        throw new Error("cant save game");
    }
    finally{
      view.enableUndo();
    }
 
}
   
}
