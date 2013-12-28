package dictionary;

import java.io.*;
import java.util.*;
import java.nio.charset.Charset;
import java.nio.file.*;

/**
* @author Mariusz Nyznar
* @version 1.0
* @see InteliCwDB
* @see Entry
*/
@SuppressWarnings("serial")
public class CwDB implements Serializable{
	
	/**
     * Container for a crossword's database.
     * @see LinkedList
     * @see Entry
     */
	protected LinkedList<Entry> dict;

    /**
     * Load a file and create a database.
     * 
     * @param filename File path of a database source. All entries have to be
     *                 saved line by line like in this example:
     * <blockquote> [[CWDATABASE]]<br/>Word 1 <br />Definition 1 <br /> Word 2 <br />Definition 2<br />etc.</blockquote> 
     * File have to be coded in UTF-8.
     */
    public CwDB(String filename) throws IOException
    {
        dict = new LinkedList<>();
        createDB(filename);
    }

    /**
     * Add a new entry to database, if it was not in a database before (checking by 'word' key).
     * <p>
     * New element will be added to the list.
     * 
     * @param word Word value for new Entry.
     * @param clue Clue value for new Entry.
     */
    public void add(String word, String clue)
    {
        Entry temp = new Entry(word,clue);
        
        if(dict.contains(temp) == false)
        {
            dict.addLast(temp);
        }
    }
    
    /**
     * Return an element which contain the same word value as specified parameter.
     * 
     * @param word Search Key.
     * @return Entry if it is found.
     *         null if it is not.
     */
    public Entry get(String word)
    {
        for(Entry a :dict)
        {
            if(word.compareTo(a.getWord()) == 0)
            return a;
        }
        return null;
    }
    
    /**
     * Remove an element from the list, if it contains the same word value as a parameter. 
     * 
     * @param word Search Key.
     */
    public void remove(String word)
    {
        for(Entry a :dict)
            if(word.compareTo(a.getWord())== 0)
                dict.remove(a);
    }
    
    /**
     * Save current state of database in file described by filename parameter.
     * <p>
     * If file exist, it will be cleared before taking any action. 
     * Elsewhere new file will be created.
     * 
     * @param filename FilePath, or FileName if file is in the same folder. 
     */
    public void saveDB(String filename) throws IOException
    {
        Path file = Paths.get(filename);
        BufferedWriter writer = null;
        try
        {
            writer = Files.newBufferedWriter(file, Charset.forName("UTF-8"));
            if(dict.isEmpty()) return;
            for(Entry a :dict)
            {
                writer.write(a.getWord() + "\n");
                writer.write(a.getClue() + "\n");
            }      
        }
        finally
        {
            if(writer != null)
            {
            	writer.close();      
            }
        }
    }
 
    /**
     * Return a number of elements in dictionary.
     * 
     * @return Size of database
     */
    public int getSize()
    {
        return dict.size();
    }
    
    /**
     * Load information about entries from file. File MUST have got first line defined as "[[CWDATABASE]]" phrase!
     * 
     * @param filename FilePath, or FileName if file is in the same folder. 
     */
    protected void createDB(String filename) throws IOException
    {     
        Path file = Paths.get(filename);
        BufferedReader reader = null;
        try
        {
            reader = Files.newBufferedReader(file, Charset.forName("UTF-8"));
            String line, line2;
            
            if( (line = reader.readLine()) != null)
            {
            	if(!line.equalsIgnoreCase("[[CWDATABASE]]")) //selected file must have got this token at the beginning 
	            	throw new IOException();
	            	
	            while ( (line = reader.readLine()) != null) 
	            {
	                if((line2 = reader.readLine()) != null) 
	                add(line.toUpperCase(),line2);
	            }
            }
        } 
        finally
        {
            if(reader != null)
            	reader.close();
        }
    }
}
