package dictionary;


import java.io.*;
import java.util.*;

/**
* @author Mariusz Nyznar
* @version 1.0
* @see CwDB
* @see Entry
*/
@SuppressWarnings("serial")
public class InteliCwDB extends CwDB implements Serializable{
	
	/**
    * Load a database from the file.
    * 
    * @param filename Filepath of database source. All entries have to be
    *                 saved line by line like in this example:
    * <blockquote> [[CWDATABASE]]<br/> Word 1 <br />Definition 1 <br /> Word 2 <br />Definition 2<br />etc.</blockquote> 
    * File have to be coded in UTF-8.
    */	
   public InteliCwDB(String filename) throws IOException
   {			
       super(filename);
   }
   
   /**
    * Create sublist which contain all of elements matching the pattern.
    * 
    * @param pattern Regular expression as search pattern.
    * @return List of elements matching the pattern.
    */
   public LinkedList<Entry> findAll(String pattern)
   {
       LinkedList<Entry> resultList = new LinkedList<>();

       if(dict.isEmpty() == false)
       {
           for(Entry currentEntry :dict)
           {
               if(currentEntry.getWord().matches(pattern)) // if matches - add to the list
               resultList.add(currentEntry);
           }
       }
       return resultList;
    }
      
   /**
    * Return random dict database's entry.
    * 
    * @return Entry
    */
   public Entry getRandom()
   {
       if(dict.isEmpty()) return null;
       
       Random generator = new Random();
       int index = generator.nextInt(dict.size()-1);
       
       return dict.get(index);   
   }
   
   /**
    * Return random dict database's entry from elements which are matching the pattern.
    * 
    * @param length Word's length as search pattern.
    * @return Entry
    */
   public Entry getRandom(int length)
   {
       if(dict.isEmpty()) return null;
       LinkedList<Entry> temp = new LinkedList<>();
       
       for(Entry a :dict)   // find all words with that length
       {
           if(a.getWord().length() == length)
               temp.add(a);
       }
       
       if(temp.isEmpty()) return null;
       
       Random generator = new Random();
       int index = generator.nextInt(temp.size()-1); 
       
       return temp.get(index);  // return random item
   }
   
   /**
    * Return random dict database's entry from elements which are matching the pattern.
    * 
    * @param pattern Regular expression as search pattern.
    * @return Entry
    */
   public Entry getRandom(String pattern)
   {
       if(dict.isEmpty()) return null;
       LinkedList<Entry> temp = findAll(pattern); // find all that matches
              
       if(temp.isEmpty()) return null;
       
       Random generator = new Random(); 
       int index = generator.nextInt(temp.size()); 
       
       return temp.get(index);  // get random item
   }
   
   /**
    * Add new entry to database with alphabetical order (sorted by 'word').
    * 
    * @param word Word value for new Entry.
    * @param clue Clue value for new Entry.
    */
   @Override
   public void add(String word, String clue)
   {
       if(!dict.isEmpty())
       {
           for(Entry a :dict)
           {
               if(a.getWord().compareTo(word) > 0)
               {
                   dict.add(dict.indexOf(a), new Entry(word,clue));
                   return;
               }
           }
       }
       dict.add(new Entry(word, clue)); // if correct place is not found add at the end
   }
   
}
