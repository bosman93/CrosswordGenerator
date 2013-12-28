package graphics;

import java.awt.*;
import java.util.*;

import javax.swing.JPanel;

import logic.Crossword;
import dictionary.CwEntry;
import dictionary.CwEntry.Direction;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel
{
	private Crossword cw;
	private boolean isSimpleStrategy;
	private boolean drawLetters;
	
	/**
	 * Create JPanel to draw crossword.
	 * @param cw Crossword to draw.
	 * @param drawLetters This flag says if crossword have to be filled by letters.
	 */
	public BoardPanel(Crossword cw, boolean drawLetters) 
	{
		super();
		this.cw = cw;
		this.isSimpleStrategy = cw.getStrategyFlag();
		this.drawLetters = drawLetters;

		setBackground(Color.WHITE);
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		if(cw.isAnyEntry())
			paintBoard(g);

	}


	
	protected void paintBoard(Graphics g)
	{
		Iterator<CwEntry> it = cw.getROEntryIter(); 		// iterator for printing clues
		LinkedList<String> cluesHoriz = new LinkedList<String>();
		LinkedList<String> cluesVert  = new LinkedList<String>();
		
		CwEntry current = null;
		
		int index;
		if(isSimpleStrategy)
			index = 0;
		else
			index = 1;
		
		int x, y;
		
		while(it.hasNext())
		{ 
			current = it.next();
			x = current.getX();
			y = current.getY();
			
			if(current.getDir() == Direction.HORIZ)
			{
				if(index != 0)
					g.drawString( Integer.toString(index) + ".",22 + (y-1)*20, 34 +(x)*20);
				
				for(int i = 0; i < current.getWord().length(); ++i)
				{
					if(isSimpleStrategy && i==0)
						g.setColor(Color.ORANGE);
					else
						g.setColor(new Color(253, 250, 227));    //draw interior
	                g.fillRect(20 + (y+i)*20, 20 + (x)*20, 20, 20);
	                
	                g.setColor(Color.black);                    //draw border
	                g.drawRect(20 + (y+i)*20, 20 + (x)*20, 20, 20);
	                
	                if(drawLetters == true)    	//type text inside if flag is settled
                        g.drawString(Character.toString(current.getWord().charAt(i)), (y+i)*20+25, (x)*20+35);

				}
				cluesHoriz.add(index+ ". " + current.getClue());
			}
			else
			{
				if(index != 0)
					g.drawString( Integer.toString(index) + ".", 22 + (y)*20, 34 +(x-1)*20);
				
				for(int i = 0; i < current.getWord().length(); ++i)
				{
					g.setColor(new Color(253, 250, 227));  
	                g.fillRect(20 + (y)*20, 20 + (x+i)*20, 20, 20); //draw interior
	                
	                g.setColor(Color.black);                    //draw border
	                g.drawRect(20 + (y)*20, 20 + (x+i)*20, 20, 20);
	                
	                if(drawLetters == true)    	//type text inside if flag is settled
                        g.drawString(Character.toString(current.getWord().charAt(i)), (y)*20+25, (x+i)*20+35);
				}
				cluesVert.add(index+ ". " + current.getClue());
				
			}
			index++;
		}
		// writing clues
		it = cw.getROEntryIter();
		int dist = cw.getBoardCopy().getWidth() * 20 + 60; // distance from left side
		int i = 1;
		
		g.drawString("Poziomo:", dist, 30);
		for(String s : cluesHoriz)
		{
			g.drawString(s, dist, 30 + (i*15));
			++i;
		}
		if(isSimpleStrategy == false)
		{
			g.drawString("Pionowo:", dist, 40+(i*15));
			for(String s : cluesVert)
			{
				++i;
				g.drawString(s, dist, 40 + (i*15));
	
			}
		}
	}
}
