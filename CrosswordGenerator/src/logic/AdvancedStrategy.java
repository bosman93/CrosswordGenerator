package logic;

import java.util.Random;

import board.*;

import dictionary.CwEntry.Direction;
import dictionary.*;

public class AdvancedStrategy implements Strategy 
{
	
	protected CwEntry findFirst(Crossword cw)
	{
		Entry entry = null;
		CwEntry result = null;
		InteliCwDB db = cw.getCwDB();	// set database
		Random rand = new Random();

			Board b = cw.getBoardCopy();

			Direction dim;
			if(rand.nextBoolean())
				dim = Direction.HORIZ;
			else
				dim = Direction.VERT;
			
			int x = 0;
			int y = 0;
			
			while( entry == null)
			{				
				if(dim == Direction.HORIZ)
					entry = db.getRandom(".{0," + String.valueOf(b.getWidth() - y - 1) + "}");
				else
					entry = db.getRandom(".{0," + String.valueOf(b.getWidth() - x - 1) + "}");
			}
			
			result = new CwEntry(entry, x, y, dim);

		return result;
	}
	
	
	@SuppressWarnings("null")
	@Override
	public CwEntry findEntry(Crossword cw) throws CrosswordGenerateException
	{
		Entry entry  = null;
		CwEntry result = null;
		InteliCwDB db = cw.getCwDB();	// set database
		String pattern = "";
		
		try {
			Board b = cw.getBoardCopy();;
			BoardCell cell = null;
			
			if(cw.isAnyEntry() == false) 
				result = findFirst(cw);
			else 
			{
				OUT:
				for(int i = 0; i < b.getHeight(); ++i) 
				{
					for(int j = 0; j < b.getWidth(); ++j) 
					{
						cell = b.getCell(i, j);
						if(cell.getContent().equalsIgnoreCase("") == false) 
						{
							if(cell.getHorizStartState() || cell.getHorizEndState()) 
							{
								pattern = b.createPattern(i, j, i, b.getWidth());
								while(pattern.length() > 1 && entry == null)
								{ 
									entry = db.getRandom(pattern); 
									
									if(b.getCell(i, j + pattern.length() - 1).getContent().equalsIgnoreCase(" "))									
										pattern = pattern.substring(0, pattern.length() - 1); 				// reducing pattern's length if no entry found
									else
										pattern = pattern.substring(0, pattern.length() - 2);
									
									if(entry != null)
									{
										if(cw.contains(entry.getWord()))
											entry = null;
										else
										{
											result = new CwEntry(entry, i, j, Direction.HORIZ);
											break OUT;
										}
									}
								}
								
							}
							else if(cell.getVertStartState() || cell.getVertEndState()) 
							{
								pattern = b.createPattern(i, j, b.getHeight(), j);
						//		System.out.println(pattern);
								while(pattern.length() > 1 && entry == null)
								{ 
									entry = db.getRandom(pattern); 
									
									if(b.getCell(i + pattern.length() - 1, j).getContent().equalsIgnoreCase(" "))									
										pattern = pattern.substring(0, pattern.length() - 1); 				// reducing pattern's length if no entry found
									else
										pattern = pattern.substring(0, pattern.length() - 2);
									
									if(entry != null)
									{
										if(cw.contains(entry.getWord()))
											entry = null;
										else
										{
											result = new CwEntry(entry, i, j, Direction.VERT);
											break OUT;
										}
									}
								} 
							}
						}
					}
				}		
			}
		}
		catch(Exception ex)
		{
			throw new CrosswordGenerateException();
		}
		return result;
	}

	@Override
	public void updateBoard(Board b, CwEntry e) throws CrosswordGenerateException 
	{
		BoardCell toAdd = null;
		BoardCell temp;

		try
		{
			if(e.getDir() == Direction.VERT)
			{
				// block fields before and after inserted word -----------------
				if(e.getX() != 0) 
				{
					temp = b.getCell(e.getX()-1, e.getY());
					temp.disableHorizAll();
					temp.disableVertAll();
					
					b.setCell(e.getX() - 1, e.getY(), temp);
				}
				if(e.getX() + e.getWord().length() != b.getHeight()) 
				{
					temp = b.getCell(e.getX() + e.getWord().length(), e.getY());
					temp.disableHorizAll();
					temp.disableVertAll();
					
					b.setCell(e.getX() + e.getWord().length() , e.getY(), temp);
				}
				// ----------------------------------------------------------------
				for(int i = 0; i < e.getWord().length(); ++i)
				{
					toAdd = b.getCell(e.getX()+i, e.getY());
							
						toAdd.disableVertAll(); 										// disable generating words in vert position in current board cell
						toAdd.setContent(Character.toString(e.getWord().charAt(i))); 	// enter text to cell
						b.setCell(e.getX()+i, e.getY(), toAdd);	
						
						 // disable fields on the both sides
						if(e.getY() != 0 ) 
						{
							temp = b.getCell(e.getX()+i, e.getY() - 1);
							temp.disableVertAll();
							temp.disableHorizEndState();
							b.setCell(e.getX()+i, e.getY() - 1, temp);
						}
						if(e.getY() != b.getWidth()-1) 
						{
							temp = b.getCell(e.getX()+i, e.getY() + 1);
							temp.disableVertAll();
							temp.disableHorizStartState();
							b.setCell(e.getX()+i, e.getY() + 1, temp);
						}
	
				}
			}
			else if(e.getDir() == Direction.HORIZ)
			{
				// block fields before and after inserted word -----------------
				if(e.getY() != 0) 
				{
					temp = b.getCell(e.getX(), e.getY() - 1);
					temp.disableHorizAll();
					temp.disableVertAll();
					
					b.setCell(e.getX(), e.getY() - 1, temp);
				}
				if(e.getY() + e.getWord().length() != b.getWidth()) 
				{
					temp = b.getCell(e.getX(), e.getY() + e.getWord().length());
					temp.disableHorizAll();
					temp.disableVertAll();
					
					b.setCell(e.getX(), e.getY() + e.getWord().length(), temp);
				}
				// ----------------------------------------------------------------
				for(int i = 0; i < e.getWord().length(); ++i)
				{
					
					toAdd = b.getCell(e.getX(), e.getY()+i);
					toAdd.disableHorizAll();										// disable generating words in horiz position in current board cell
					toAdd.setContent(Character.toString(e.getWord().charAt(i))); 	// enter text to cell
					
					b.setCell(e.getX(), e.getY()+i, toAdd);	
					
					 // disable fields on the both sides
					if(e.getX() - 1 >= 0 ) 
					{
						temp = b.getCell(e.getX() - 1, e.getY() +i);
						temp.disableHorizAll();
						temp.disableVertEndState();
						
						b.setCell(e.getX() - 1, e.getY() +i, temp);
					}
					if(e.getX() + 1 < b.getHeight()) 
					{
						temp = b.getCell(e.getX() + 1, e.getY() +i);
						temp.disableHorizAll();
						temp.disableVertStartState();
						
						b.setCell(e.getX() + 1, e.getY() +i, temp);
					}
				}
	
			}
		}
		catch(Exception ex)
		{
			throw new CrosswordGenerateException();
		}

	}
}
