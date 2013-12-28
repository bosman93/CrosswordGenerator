package logic;

import java.io.Serializable;
import java.util.*;

import board.Board;
import dictionary.*;

@SuppressWarnings("serial")
/**
 * Crossword container class.
 * @author Mariusz Nyznar
 * @version 1.0
 */
public class Crossword implements Serializable{

	 private LinkedList<CwEntry> entries;
	 private Board b;
	 private InteliCwDB cwdb;
	 private final long ID;
	 protected boolean isSimpleStrategy = false;
	 
	 
	 /**
	  * Generate empty crossword container with selected size.
	  * @param i Board's height.
	  * @param j Board's width.
	  * @param state isSimpleStrategy flag.
	  */
	public Crossword(int i, int j, boolean state)
	{
		ID = new Date().getTime();
		
		isSimpleStrategy = state; // set flag
		
		b = new Board(i, j);
		 
		entries = new LinkedList<CwEntry>();
	}

	/**
	 * Get crossword's unique ID number.
	 * @return Crossword's ID.
	 */
	 public long getID()
	 {
		 return ID;
	 }
	 
	 /**
	  * If true - crossword generated with simple strategy.
	  * @return isSimpleStrategy flag.
	  */
	 public boolean getStrategyFlag()
	 {
		 return isSimpleStrategy;
	 }

	 /**
	  * Return unmodifiable iterator to entries list.
	  * @return Collections.unmodifiableList(entries).iterator()
	  */
	 public Iterator<CwEntry> getROEntryIter()
	 {
		 return Collections.unmodifiableList(entries).iterator();
	 }
	 
	 /**
	  * Return Board's copy.
	  * @return Crossword board's copy.
	  */
	 public Board getBoardCopy()
	 {
		 return new Board(this.b);
	 }
	 
	 /**
	  * Return crossword's database.
	  * @return InteliCwDB object
	  */
	 public InteliCwDB getCwDB()
	 {
		return cwdb; 
	 }
	 
	 /**
	  * Set crossword's database.
	  * @param cwdb New database to set.
	  */
	 public void setCwDB(InteliCwDB cwdb)
	 {
		this.cwdb = cwdb; 
	 }
	 
	 /**
	  * Check if crossword already contains entry with typed word.
	  * @param word Word to check.
	  * @return True - if  contains, false - if not.
	  */
	 public boolean contains(String word)
	 {
		 for(CwEntry e: entries)
		 {
			 if(e.getWord().compareTo(word) == 0) return true;
		 }
		 return false;
	 }
	 
	 /**
	  * Return true if entries list is empty, false if there are entries in this list.
	  * @return Entries list state.
	  */
	 public boolean isAnyEntry()
	 {
		 if(entries.isEmpty() == false) return true;
		 return false;
	 }
	 
	 /**
	  * Add new entry to list and update board.
	  * @param cwe Entry to add.
	  * @param s Adding strategy.
	  */
	 public final void addCwEntry(CwEntry cwe, Strategy s) throws CrosswordGenerateException
	 {
		  entries.add(cwe);
		  s.updateBoard(b,cwe);
	 }
	 
	 /**
	  * Generate new crossword.
	  * @param s Strategy of generation.
	  */
	 public final void generate(Strategy s) throws CrosswordGenerateException
	 {
		  CwEntry e = null;
		  while((e = s.findEntry(this)) != null)
		    addCwEntry(e,s);  
	 }
}
