package board;

import java.io.Serializable;
import java.util.*;

/**
*
* @author Mariusz Nyznar
*/
@SuppressWarnings("serial")
public class Board implements Serializable
{

	private  final long ID;
	private BoardCell[][] board;
    
    /**
     * Generate empty board.
     * @param i number of rows
     * @param j number of columns
     */
    public Board(int i, int j)
    {
		Date d = new Date();
		ID = d.getTime();
    	
    	board = new BoardCell[i][j];
    	
    	for(int a = 0; a < i; ++a)
    		for(int b = 0; b < j; ++b)
    			board[a][b] = new BoardCell();
    }
    
    /**
     * @param b Board object.
     */
    public Board(Board b)
    {
    	int tempI = b.getHeight();
    	int tempJ = b.getWidth();
    	
		Date d = new Date();
		ID = d.getTime();
    	
    	board = new BoardCell[tempI][tempJ];
    	    	
    	for(int i = 0; i < tempI; ++i)
    		for(int j = 0; j < tempJ; ++j)
    			board[i][j] = b.getCell(i, j);
    }
   
    /**
     * Return Board's ID
     * @return Long type ID value.
     */
    public long getID()
    {
    	return ID;
    }
    
    /**
     * Get a board's width.
     * @return Number of columns.
     */
    public int getWidth()
    {
    	return board[0].length;
    }
    
    /**
     * Get a board's height.
     * @return Number of rows.
     */
    public int getHeight()
    {
    	return board.length;
    }
    
    /**
     * Get cell placed on selected position.
     * @param i Vertical index
     * @param j Horizontal index
     * @return Board cell placed at this position.
     */
    public BoardCell getCell(int i, int j)
    {
    	return board[i][j];
    }
    
    /**
     * Set a new cell.
     * @param i Vertical index.
     * @param j Horizontal index.
     * @param c New cell.
     */
    public void setCell(int i, int j, BoardCell c)
    {
    	board[i][j] = c;
    }
    
    /**
     * Get a list of cells which are able to start new word.
     * @return List of cells.
     */
    public LinkedList<BoardCell> getStartCells()
    {
    	LinkedList<BoardCell> result = new LinkedList<>();
    	
    	for(int i = 0; i < board.length; ++i)
    		for(int j = 0; j < board[0].length; ++j)
    		{
    			if((board[i][j].getHorizStartState() == true) || (board[i][j].getVertStartState() == true))
    				result.add(board[i][j]);
    		}	
    	return result;
    }
    
    /**
     * Create a regular expression for the line between selected positions.
     * @param fromI Start point's vertical position.
     * @param fromJ Start point's horizontal position.
     * @param toI End point's vertical position.
     * @param toJ End point's horizontal position.
     * @return Searched pattern.
     * @throws DimensionException Throw if the line is not horizontal and not vertical.
     */
    public String createPattern(int fromI, int fromJ, int toI, int toJ) throws DimensionException
    {
    	if((fromI != toI) && (fromJ != toJ)) throw new DimensionException(); // throw if there is no horiz or vert line between points
    	
    	String result = "";
    	
    	if(fromI == toI) // if horizontal
    	{
    		for(int j = fromJ; j < toJ-1; ++j)
    		{
    			if(!board[fromI][j].getHorizInnerState())
    				break;
    			if(board[fromI][j].content.equalsIgnoreCase("")) 
    				result +=".";
    			else  
    				result += board[fromI][j].getContent();
    		}
    	}
    	else if(fromJ == toJ) // if vertical 
    	{
    		for(int i = fromI; i < toI-1; ++i)
    		{
    			if(!board[i][fromJ].getVertInnerState())
    				break;
    			if(board[i][fromJ].content.equalsIgnoreCase("")) 
    				result +=".";
    			else
    				result += board[i][fromJ].content;
    		}    		
    	}    	
    	return result;
    }
}
