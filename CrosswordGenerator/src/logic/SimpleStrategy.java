package logic;

import java.util.LinkedList;

import board.*;
import dictionary.*;
import dictionary.CwEntry.Direction;


public class SimpleStrategy implements Strategy {

	protected CwEntry firstpass; // first word in crossword
	
	protected Entry findFirstCorrect(Crossword cw)
	{
		cw.isSimpleStrategy = true; 	// set flag
		Entry result = null;		
		InteliCwDB db = cw.getCwDB();	// set database
		
		while(result == null)
		{
			result = cw.getCwDB().getRandom(); // generate new word
			
			if(result.getWord().length() > cw.getBoardCopy().getHeight()){ // if it is too long - find another one
				result = null;
				continue;
			}
			
			for(int i = 1; i < result.getWord().length(); ++i) // check every letter for possibility of generating horiz words
			{
				String currentLetter = Character.toString(result.getWord().charAt(i)); 
				
				// if in main word are letters that cannot start new words (no match in the database)
				LinkedList<Entry> temp = db.findAll(currentLetter + ".*");
				
				if(temp.peekFirst() == temp.peekLast()) {  
					result = null;
					break;
				}
			}
		}	
		return result;
	}
	
	@Override
	/**
	 * Find next entry for the crossword.
	 */
	public CwEntry findEntry(Crossword cw) throws CrosswordGenerateException
	{		
		Entry entry = null;
		CwEntry result = null;
		try
		{
			if(cw.isAnyEntry() == false)		// if first use
			{
				entry = findFirstCorrect(cw);
	
				result = new CwEntry(entry, 0, 0, Direction.VERT);
				firstpass = result;
			}
			else
			{
				Entry e = null;
				
				// looking for letter in main password that is NOT a beginning of new word
				for(int i = 0; i < firstpass.getWord().length(); ++i) 
				{
					if(cw.getBoardCopy().getCell(i, 0).getHorizStartState() == true){ 	// founded empty line
						
						do{
							// find random word with specified first letter
							e = cw.getCwDB().getRandom(cw.getBoardCopy().getCell(i, 0).getContent() + ".*"); 	
							
						}while(e.getWord().length() >= cw.getBoardCopy().getWidth() || cw.contains(e.getWord()) == true); // checking if word isn't longer than board
						
						result = new CwEntry(e, i, 0, Direction.HORIZ);
						break;
					}
				}
			}
		}
		catch(Exception ex)
		{
			throw new CrosswordGenerateException();
		}
		return  result;
	}

	@Override
	public void updateBoard(Board b, CwEntry e) throws CrosswordGenerateException
	{
		BoardCell toAdd = null;
		try
		{
			if(e.getDir() == Direction.VERT)
			{
				for(int i = 0; i < e.getWord().length(); ++i)
				{
					toAdd = new BoardCell();
	
					toAdd.disableVertAll(); 										// disable generating words in vert position in current board cell
					toAdd.setContent(Character.toString(e.getWord().charAt(i))); 	// enter text to cell
					
					b.setCell(e.getX()+i, e.getY(), toAdd);	
				}
			}
			else if(e.getDir() == Direction.HORIZ)
			{
				for(int i = 0; i < e.getWord().length(); ++i)
				{
					toAdd = new BoardCell();
					
					toAdd.disableHorizAll();										// disable generating words in horiz position in current board cell
					toAdd.setContent(Character.toString(e.getWord().charAt(i))); 	// enter text to cell
					
					b.setCell(e.getX(), e.getY()+i, toAdd);	
				}
			
			}
		}
		catch(Exception ex)
		{
			throw new CrosswordGenerateException();
		}
	}
}
