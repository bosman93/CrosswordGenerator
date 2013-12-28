package utility;

import java.io.*;
import java.util.*;

import logic.Crossword;

public class CwBrowser 
{
	private CwReader cwRead = new CwReader();
	private CwWriter cwWrite = new CwWriter();
	private String dirPath;
	protected LinkedList<Crossword> cws;

	/**
	 * Create new Crossword browser.
	 * @param path Directory path.
	 */
	public CwBrowser(String path) 
	{
		this.dirPath = path;
		cws = new LinkedList<Crossword>();		
	}
		
	/**
	 * Load all Crosswords from selected directory and save in the list.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void loadAll()throws IOException,	ClassNotFoundException
	{
		cwRead.getAllCrosswords();
	}
	/**
	 * Save crossword to file.
	 * @param cw Crossword to save
	 * @throws IOException
	 */
	public void save(Crossword cw) throws IOException 
	{
		cwWrite.write(cw);
	}
	
	/**
	 * Get crossword list's iterator.
	 * @return Crossword's list iterator
	 */
	public ListIterator<Crossword> getCwsIterator(){
		return cws.listIterator();
	}
	
	
	private class CwReader implements Reader
	{
		@Override
		public void getAllCrosswords() throws IOException,	ClassNotFoundException 
		{
			for (String filename : getFilenameList())
				cws.add(getCrossword(filename));
		}

		@Override
		public Crossword getCrossword(String filename) throws IOException, ClassNotFoundException 
		{
			String filepath = new String(dirPath + File.separator + filename);
			Crossword cw = null;
			
			ObjectInputStream in  = null;
			try 
			{
				in = new ObjectInputStream(new FileInputStream(filepath));
				cw =  (Crossword) in.readObject();
			} 
			finally 
			{
				if (in != null)
					in.close();
			}
			
			return cw;
		}
		

		public LinkedList<String> getFilenameList() 
		{
			LinkedList<String> list = new LinkedList<String>();
			File dir = new File(dirPath);
			String[] fileNames = dir.list(); // files in the directory
			
	                
			for (String fileName : fileNames) 
			{
				// our file names contain only numbers
				if (fileName.matches("[0-9]*.crw")){
					list.add(fileName);
				}
			}
			return list;
		}
	}
	
	private class CwWriter implements Writer
	{
		@SuppressWarnings("null")
		@Override
		public void write(Crossword cw) throws IOException 
		{
			if(new File(dirPath).isDirectory() == false)
				throw new IOException();

			File directory = new File(new String(dirPath + File.separator  + Long.toString(cw.getID()) + ".crw"));
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(directory));
			try {
				out.writeObject(cw);
			} 
			finally {
				if (out != null)
					out.close();
			}
		}
		
	}

}

