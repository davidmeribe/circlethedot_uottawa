package circlethedot_uottawa.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import circlethedot_uottawa.GameController;
import circlethedot_uottawa.GameModel;
/**
 * The class <b>BoardView</b> provides the current view of the board. It extends
 * <b>JPanel</b> and lays out a two dimensional array of <b>DotButton</b> instances.
 *
 * @author David Meribe, University of Ottawa
 */
public class BoardView extends JPanel{

	private static final long serialVersionUID = 1L;

	private GameModel model;
	private GameController controller;
	private DotButton [][] dots;
	private int row, col;
	 
	  
	 /**
	     * Constructor used for initializing the board. The action listener for
	     * the board's DotButton is the game controller
	     * 
	     * @param gameModel
	     *            the model of the game (already initialized)
	     * @param gameController
	     *            the controller
	     */

	    public BoardView(GameModel model, GameController controller) {
	        this.controller = controller;
	        this.model = model;
	         col = row = model.getSize();
	         
	         setBackground(Color.WHITE);
	         setLayout(new GridLayout(row,1));
	         setBorder(BorderFactory.createEmptyBorder(20,20,0,20));
    
	         dots = new DotButton[row][col];    
	         
	       
	        // fillBoardView();
	         for (int r = 0; r < model.getSize(); r++){
		    		JPanel panelRow = new JPanel();
		    		if(r%2 == 0){
		    			panelRow.setBorder(BorderFactory.createEmptyBorder(0,0,0,20));
		    			panelRow.setLayout(new FlowLayout(FlowLayout.LEADING,2,2));
		    		}else{
		    			panelRow.setBorder(BorderFactory.createEmptyBorder(0,20,0,0));
		    			panelRow.setLayout(new FlowLayout(FlowLayout.TRAILING,2,2));
		    		}
		    		panelRow.setBackground(Color.WHITE);
		    		for(int c = 0; c < model.getSize(); c++){
		    			dots[r][c] = new DotButton(r,c,model.getCurrentStatus(r, c));
		    			//add listener for controller
		    			dots[r][c].addActionListener(controller);
		    			//add to panel row
		    			panelRow.add(dots[r][c]);
		    		}
		    		add(panelRow);
		    	}
	        setVisible(true);
	    }
	    
	    	
	    	/**
	    	 * update the status of the board's DotButton instances based on the current game model
	    	 */

	      public void update(){
	        	for(int i = 0; i < model.getSize(); i++){
	    		   	for(int j = 0; j < model.getSize(); j++){
	    		   		dots[i][j].setType(model.getCurrentStatus(i,j));
	    		   	}
	    		}
	    		repaint();
	        }
    	    
}
