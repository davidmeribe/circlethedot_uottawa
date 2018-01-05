package circlethedot_uottawa.view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;

import circlethedot_uottawa.GameModel;
/**
 * In the application <b>Circle the dot</b>, a <b>DotButton</b> is a specialized type of
 * <b>JButton</b> that represents a dot in the game. It uses different icons to
 * visually reflect its state: a blue icon if the blue dot is currently on this location
 * an orange icon is the dot has been selected and a grey icon otherwise.
 * 
 * The icon images are stored in a subdirectory ``data''. They are:
 * data/ball-0.png => grey icon       //available
 * data/ball-1.png => orange icon     //selected
 * data/ball-2.png => blue icon      //dot
 *
 * @author David Meribe , University of Ottawa
 */

public class DotButton extends JButton{

	private static final long serialVersionUID = -7890616280702831209L;
	private int row;
	private int col;
	private int type; //button type, available, bluedot or selected
	
	public static final int NUM_COLORS = 3;
	
	 /**
     * An array is used to cache all the images. Since the images are not
     * modified, all the cells that display the same image reuse the same
     * <b>ImageIcon</b> object. Notice the use of the keyword <b>static</b>.
     */

    private static final ImageIcon[] icons = new ImageIcon[NUM_COLORS];
    
    /**
     * Constructor used for initializing a cell of a specified type.
     * 
     * @param row
     *            the row of this Cell
     * @param column
     *            the column of this Cell
     * @param type
     *            specifies the type of this cell
     */

    public DotButton(int row, int col, int type){
    	this.row = row;
    	this.col = col;
    	this.type = type;
    	
    	setBackground(Color.WHITE);
    	setIcon(getImageIcon());
    	Border emptyBorder = BorderFactory.createEmptyBorder(0,0,0,0);
    	setBorder(emptyBorder);
    	setBorderPainted(false);
    }
    /**
     * Changes the cell type of this cell. The image is updated accordingly.
     * 
     * @param type
     *            the type to set
     */

    public void setType(int type) {
      this.type= type;                      // call this method from board view
      setIcon(getImageIcon());
    }
    
    /**
     * Getter method for the attribute row.
     * 
     * @return the value of the attribute row
     */

    public int getRow() {
      return row;
    }

    /**
     * Getter method for the attribute column.
     * 
     * @return the value of the attribute column
     */

    public int getColumn() {
      return col;
    }
    
    /**
     * Determine the image to use based on the cell type. Implements a caching mechanism.
     * caching mechanism didnt work
     * 
     * @return the image to be displayed by the button
     */

   /* private ImageIcon getImageIcon() {
      if(icons[type] != null){
    	  String strId = Integer.toString(type);
    	  icons[type] = new ImageIcon("data/ball-"+ strId + ".png");
      } 
      return icons[type];
    }*/
    
     private ImageIcon getImageIcon(){
              
      ImageIcon icon =new ImageIcon();      
  
      if( type == GameModel.AVAILABLE){
           icon= new ImageIcon(getClass().getResource("data/ball-0.png"));                   
        }
       else if(type == GameModel.SELECTED){
           
        icon= new ImageIcon(getClass().getResource("data/ball-1.png")); 
      } 
      else if (type == GameModel.DOT){
         icon= new ImageIcon(getClass().getResource("data/ball-2.png")); 
    } 
      return icon;
}
    
}
