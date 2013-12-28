package dictionary;

import java.io.Serializable;

/**
* @author Mariusz Nyznar
* @version 1.0
* @see String
*/
@SuppressWarnings("serial")
public class Entry implements Serializable{
    private String word;
    private String clue;
    
    /**
     * Save parameters to new object.
     * @param word Word value for new Entry.
     * @param clue Clue value for new Entry.
     */
    public Entry(String word, String clue)
    {
        this.word = word;
        this.clue = clue;
    }
    
    /**
     * Return word string.
     * @return Value of word string.
     */
    public String getWord()
    {
        return word;
    }
    
    /**
     * Return clue string.
     * @return Value of clue string.
     */
    public String getClue()
    {
        return clue;
    }  
}
