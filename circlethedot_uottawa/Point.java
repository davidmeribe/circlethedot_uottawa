package circlethedot_uottawa;


/**
 * The class <b>Point</b> is a simple helper class that stares a 2 dimentional element on a grid
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 * @author David Meribe, 
 */

public class Point implements Cloneable{

   private int  xCoord=0;
   private int yCoord=0;                  //  INSTANCE VARIABLES x coordinate and y cordinate

    /**
     * Constructor 
     * 
     * @param x
     *            the x coordinate
     * @param y
     *            the y coordinate
     */
    public Point(int x, int y){
          xCoord= x;
          yCoord= y;   // REPLACE THE BODY OF THIS METHOD WITH YOUR OWN IMPLEMENTATION
    }
    
    /**
     * Constructor 
     * 
     * @param p
     *            the Point to clone
     */
    public Point(Point p){
        reset(p.getX(),p.getY());
    }
    
    /**
     * Getter method for the attribute x.
     * 
     * @return the value of the attribute x
     */
    public int getX(){
          return xCoord;       
    }
    
    /**
     * Getter method for the attribute y.
     * 
     * @return the value of the attribute y
     */
    public int getY(){
         return yCoord;            
    }
    
    /**
     * Setter for x and y.
     * @param x
     *            the x coordinate
     * @param y
     *            the y coordinate
     */
    public void reset(int x, int y){
         xCoord= x;
         yCoord= y;                    
  }
    
    public Object clone() throws CloneNotSupportedException{	 
        return super.clone(); 
      }

 }