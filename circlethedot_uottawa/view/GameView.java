package circlethedot_uottawa.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import circlethedot_uottawa.GameController;
import circlethedot_uottawa.GameModel;

/**
 * The class <b>GameView</b> provides the current view of the entire Game. It extends
 * <b>JFrame</b> and lays out an instance of  <b>BoardView</b> (the actual game) and 
 * two instances of JButton(for reset and quit). The action listener for the buttons is the controller.
 *
 * @author David Meribe, University of Ottawa
 */

public class GameView extends JFrame{

	// private static int FRAME_SIZE= 50;
	private static final long serialVersionUID = 1L;
	
	private GameModel model;
	//private GameController controller;
	private BoardView board;
	
	private JPanel control;
	
	 /**
     * Constructor used for initializing the Frame
     * 
     * @param model
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */
   
	public GameView(GameModel model, GameController controller){
		super("Circle the Dot");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		
		this.model = model;
		board = new BoardView(model, controller);
		
		add(board, BorderLayout.CENTER);
		
		JButton buttonReset = new JButton("Reset");
		buttonReset.setFocusPainted(false);
		buttonReset.addActionListener(controller);
		
		JButton buttonExit = new JButton("Quit");
		buttonExit.setFocusPainted(false);
		buttonExit.addActionListener(controller);
		//undo button
		JButton undoButton = new JButton("Undo");
		undoButton.setEnabled(false);
		undoButton.addActionListener(controller);
		
		control = new JPanel();
		control.setBackground(Color.WHITE);
		control.add(buttonReset);
		control.add(buttonExit);
		control.add(undoButton);
		//add control panel
		add(control, BorderLayout.SOUTH);
		
		
		setResizable(false);
		pack();
		setVisible(true);
		
	}
	
	/*
	 * Update the board
	 */
	 public void update(){
	        board.update();  
	    }
	 
	 /** helper method the elements in the stack and set Undo enabled.
     */
   public void enableUndo(){   
      if( control.getName().equals("Undo")){
        if (model.getNumberOfSteps() < 1 ){
             control.setEnabled(false);
         }
        else{
         control.setEnabled(true);     
          }
  }
     
}
}
