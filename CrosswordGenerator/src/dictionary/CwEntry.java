package dictionary;

import java.io.Serializable;

/**
* @author Mariusz Nyznar
* @version 1.0
* @see Entry
*/
@SuppressWarnings("serial")
public class CwEntry extends Entry  implements Serializable
{
	private int x;
	private int y ;
	private Direction d;
    
    /**
     * Save parameters to new object.
     * @param entry Content of the entry .
     * @param x Horizontal coordinate.
     * @param y Vertical coordinate.
     * @param d Direction.
     * @see Direction
     * @see Entry
     */	
    public CwEntry(Entry entry, int x, int y, Direction d)
    {
        super(entry.getWord(), entry.getClue());

        this.x = x;
        this.y = y;
        this.d = d;
    }

    /**
     * Get horizontal coordinate of the clue.
     * @return Horizontal coordinate. 
     */
    public int getX()
    {
        return x;
    }
    
    /**
     * Get vertical coordinate of clue.
     * @return Vertical coordinate.
     */
    public int getY()
    {
        return y;
    }
    
    /**
     * Get word direction.
     * @return HORIZ - if horizontally<br />
     *         VERT - if vertically
     */
    public Direction getDir()
    {
        return d;
    } 
    
    public enum Direction
    {
        HORIZ, //horizontal
        VERT;  //vertical
    }
}
