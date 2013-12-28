package board;

import java.io.Serializable;

/**
* 
* @author Mariusz Nyznar
*/
@SuppressWarnings("serial")
public class BoardCell implements Serializable
{
	protected String content;
	
	/*
	 * first row for horizontal states, second for vertical
	 * first column - true if it is possible to start here a new word
	 * second column - true if it can be placed a word with this character inside
	 * third column - true if it can be ended with this character.
	 */
	protected boolean state[][];
	
	public BoardCell()
	{
		state = new boolean[2][3];
		
		content = new String("");
		enableHorizAll();
		enableVertAll();
	}
	
		/**
		 * Set character.
		 */
    public void setContent(String content)
    {
    	if(content == null) 
    		this.content = new String("");
    	
    	this.content = content;
    }
    
	    /**
		 * Get character content.
		 */
    public String getContent()
    {
        return this.content;
    }
    
		/**
		 * Set 'true' value of all Horizontal State parameter.
		 */
    public void enableHorizAll()
    {
    	for(int i = 0; i < 3; ++i)
    		state[0][i] = true;
    }
		/**
		 * Set 'false' value of Horizontal Start State parameter.
		 */
    public void disableHorizAll()
    {
    	for(int i = 0; i < 3; ++i)
    		state[0][i] = false; 	
    }
	    /**
		 * Set 'true' value of all Vertical State parameter.
		 */
    public void enableVertAll()
    {
    	for(int i = 0; i < 3; ++i)
    		state[1][i] = true;	
    }
		/**
		 * Set 'false' value of Vertical Start State parameter.
		 */
    public void disableVertAll()
    {
    	for(int i = 0; i < 3; ++i)
    		state[1][i] = false; 	
    }
    
		/**
		 * Set 'true' value of Horizontal Start State parameter.
		 */
	public void enableHorizStartState()
	{
		state[0][0] = true;;
	}
		/**
		 * Set 'true' value of Horizontal Inner State parameter.
		 */
	public void enableHorizInnerState()
	{
		state[0][1] = true;		
	}
		/**
		 * Set 'true' value of Horizontal End State parameter.
		 */
	public void enableHorizEndState()
	{
		state[0][2] = true;		
	}
		/**
		 * Set 'true' value of Vertical Start State parameter.
		 */
	public void enableVertStartState()
	{
		state[1][0] = true;		
	}
		/**
		 * Set 'true' value of Vertical Inner State parameter.
		 */
	public void enableVertInnertate()
	{
		state[1][1] = true;		
	}
		/**
		 * Set 'true' value of Vertical End State parameter.
		 */
	public void enableVertEndState()
	{
		state[1][2] = true;		
	}
	
	
	
	
		/**
		 * Set 'false' value of Horizontal Start State parameter.
		 */
	public void disableHorizStartState()
	{
		state[0][0] = false;
	}
		/**
		 * Set 'false' value of Horizontal Inner State parameter.
		 */
	public void disableHorizInnerState()
	{
		state[0][1] = false;		
	}
		/**
		 * Set 'false' value of Horizontal End State parameter.
		 */
	public void disableHorizEndState()
	{
		state[0][2] = false;		
	}
		/**
		 * Set 'false' value of Vertical Start State parameter.
		 */
	public void disableVertStartState()
	{
		state[1][0] = false;		
	}
		/**
		 * Set 'false' value of Vertical Inner State parameter.
		 */
	public void disableVertInnerState()
	{
		state[1][1] = false;		
	}
		/**
		 * Set 'false' value of Vertical End State parameter.
		 */
	public void disableVertEndState()
	{
		state[1][2] = false;		
	}
	
		/**
		 * Get value of Horizontal Start State parameter.
		 */
	public boolean getHorizStartState()
	{
		return state[0][0];
	}
		/**
		 *  Get value of Horizontal Inner State parameter.
		 */
	public boolean getHorizInnerState()
	{
		return state[0][1];
	}
		/**
		 * Get value of Horizontal End State parameter.
		 */
	public boolean getHorizEndState()
	{
		return state[0][2];	
	}
		/**
		 * Get value of Vertical Start State parameter.
		 */
	public boolean getVertStartState()
	{
		return state[1][0];		
	}
		/**
		 * Get value of Vertical Inner State parameter.
		 */
	public boolean getVertInnerState()
	{
		return state[1][1];		
	}
		/**
		 * Get value of Vertical End State parameter.
		 */
	public boolean getVertEndState()
	{
		return state[1][2];	
	}

}
