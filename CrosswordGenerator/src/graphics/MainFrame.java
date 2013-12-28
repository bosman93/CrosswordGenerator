package graphics;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ListIterator;

import javax.naming.SizeLimitExceededException;
import javax.swing.*;
import javax.xml.bind.ValidationException;

import logic.*;
import utility.*;
import dictionary.InteliCwDB;


/**
 * @author Mariusz
 *
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	
	protected InteliCwDB db;
    protected Crossword cw;
    protected Strategy s;
    protected boolean drawLetterState;
    protected boolean isSimpleStrategy;
	protected BoardPanel panel;
	protected CwBrowser cwBrowser;
	protected ListIterator<Crossword> it;
	protected boolean isMovingRight;
        
	protected JTextField marksField[] = new JTextField[2];

    public MainFrame() {
    	super("Crossword Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(1024, 768);
        
		drawLetterState = false;
		isSimpleStrategy = false;
		it = null;
		
		cwBrowser = null;
		panel = null;
		isMovingRight = true;
		
		createGUI();
    }
    
    
    private JPanel getPanel()
    {
    	 marksField[0] = new JTextField();
    	 marksField[1] = new JTextField();

    	
        JPanel basePanel = new JPanel();
       // basePanel.setOpaque(true);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 2, 3, 3));
        centerPanel.setBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5));
        centerPanel.setOpaque(true);

        JLabel mLabel1 = new JLabel("Board's height : ");
        JLabel mLabel2 = new JLabel("Board's width : ");

        centerPanel.add(mLabel1);
        centerPanel.add(marksField[0]);
        centerPanel.add(mLabel2);
        centerPanel.add(marksField[1]);

        basePanel.add(centerPanel);

        return basePanel;
    }
    
    /**
     * Action for Generate Crossword button
     * @param evt
     */
    private void generateActionEvent(ActionEvent evt) // do if generate button was pressed
    {
    	try
    	{
	    	int selection = JOptionPane.YES_OPTION;	
	    	selection = JOptionPane.showConfirmDialog( null, 
								getPanel(), 
								"Generate parameters",
		                        JOptionPane.OK_CANCEL_OPTION,
		                        JOptionPane.PLAIN_MESSAGE);
    	
	    	if(selection == JOptionPane.OK_OPTION) 
	    	{
	    		if(s == null || db == null || db.getSize() == 0) 
	    			throw new InitialParameterException();
	    		else 
	    		{
	    			String str1 = marksField[0].getText();
	    			String str2 = marksField[1].getText();
	    			
	    			if(isNumeric(str1) == false || isNumeric(str2) == false || str1.isEmpty() == true || str2.isEmpty() == true) 
	    			{
	    				throw new ValidationException("Not numerical or empty");
	    			}
	    			else 
	    			{
    					int h = Integer.parseInt(str1);
    					int w = Integer.parseInt(str2);
    					    					
    					if(h < 5 || w < 5 || h > 30 || w > 30)
    						throw new SizeLimitExceededException();
    					else 
    					{	
	    					cw = new Crossword(h,w,isSimpleStrategy);
	    					cw.setCwDB(db);
    						cw.generate(s);
    						
	    					if(panel != null)
		        				remove(panel);
	    					
	        				panel = new BoardPanel(cw, drawLetterState);
	        				add(panel);
	        				revalidate();
    					}
    				}
	    		}
	    			
	    	}
    	}
    	catch(InitialParameterException ex) 
    	{
    		JOptionPane.showMessageDialog(null,
                    "No Strategy or Database selected!", "Error",
                    JOptionPane.ERROR_MESSAGE);
    	} 
    	catch (ValidationException e) 
    	{
    		JOptionPane.showMessageDialog(null,
                    "Invalid values detected!", "Error",
                    JOptionPane.ERROR_MESSAGE);
		} 
    	catch (CrosswordGenerateException e) 
    	{
    		JOptionPane.showMessageDialog(null,
                    "Crossword generating error detected!", "Error",
                    JOptionPane.ERROR_MESSAGE);
		} 
    	catch (SizeLimitExceededException e) 
    	{
    		JOptionPane.showMessageDialog(null,
                    "Values exceeded the available range! Please enter values between 5 and 30", "Error",
                    JOptionPane.ERROR_MESSAGE);
		}
    }
    
    /**
     * Action for Open Database button
     * @param evt
     */
	private void opendbActionEvent(ActionEvent evt) 
	{
        JFileChooser chooser = new JFileChooser();
        
    	int respond = chooser.showOpenDialog(null);
		chooser.setDialogTitle("Upload database");
	
    	if (respond == JFileChooser.APPROVE_OPTION) 
    	{
    		String filepath = chooser.getSelectedFile().getPath();

    		try 
    		{
    			db = new InteliCwDB(filepath);
    			JOptionPane.showMessageDialog(null, "Database upload success.");
    		}
    		catch(IOException ex) 
    		{
    			JOptionPane.showMessageDialog(null, 
		    					"File exception detected. Select correct file.", "Error", 
								JOptionPane.ERROR_MESSAGE);
    		}
    	}
	            
	}
	
	/**
	 * Action for Save Crossword button
	 * @param evt
	 */
	private void saveActionEvent(ActionEvent evt)  
	{
		JFileChooser chooser = new JFileChooser();
		
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setApproveButtonText("Save");
		chooser.setDialogTitle("Save crossword");
		
		int respond = chooser.showOpenDialog(null);
	
    	if (respond == JFileChooser.APPROVE_OPTION) 
    	{
    		String filepath = chooser.getSelectedFile().getPath();
    		cwBrowser = new CwBrowser(filepath);
    		try 
    		{
    			cwBrowser.save(cw);
    		}
    		catch(IOException ex) 
    		{
    			JOptionPane.showMessageDialog(null, 
		    					"Crossword saving error!","Error", 
								JOptionPane.ERROR_MESSAGE);
    		}
    		catch(NullPointerException ex) 
    		{
    			JOptionPane.showMessageDialog(null, 
		    					"There is no crossword to save!","Error", 
								JOptionPane.ERROR_MESSAGE);
    		}
    	}   
	}
	
	/**
	 * Action for Load Crosswords button
	 * @param evt
	 */
	private void loadActionEvent(ActionEvent evt) 
	{
		JFileChooser chooser = new JFileChooser();
		
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setApproveButtonText("Choose directory");
		chooser.setDialogTitle("Load crosswords");
		
		int respond = chooser.showOpenDialog(null);
	
    	if (respond == JFileChooser.APPROVE_OPTION) 
    	{
    		String filepath = chooser.getSelectedFile().getPath();
    		cwBrowser = new CwBrowser(filepath);
    		try 
    		{
    			cwBrowser.loadAll();
    			it = cwBrowser.getCwsIterator();
    			
    			JPanel buttonPanel = new JPanel();
    			JButton prevButton = new JButton("Prev");
    			JButton nextButton = new JButton("Next");
    			
    			buttonPanel.add(prevButton);
    			buttonPanel.add(nextButton);
    			
    			add(buttonPanel, BorderLayout.NORTH);
    			revalidate();
    			
    			prevButton.addActionListener(new ActionListener() {
    	            public void actionPerformed(java.awt.event.ActionEvent evt) 
    	            {
    	            	prevButtonActionEvent(evt);
    	            }
    	        });
    			
    			nextButton.addActionListener(new ActionListener() {
    	            public void actionPerformed(java.awt.event.ActionEvent evt) 
    	            {
    	            	nextButtonActionEvent(evt);
    	            }
    	        });
    		}
    		catch(InvalidClassException ex) 
    		{
    			JOptionPane.showMessageDialog(null, 
    					"Crossword loading error. Incompatible object detected.", "Error", 
						JOptionPane.ERROR_MESSAGE);
    		}
    		catch(Exception ex)
    		{
    			JOptionPane.showMessageDialog(null, 
    					"Crossword loading error.", "Error", 
						JOptionPane.ERROR_MESSAGE);
    		}
    	}
	}
	
	/**
	 * Action for PREV button
	 * @param evt
	 */
	private void prevButtonActionEvent(ActionEvent evt) 
	{
		if(it.hasPrevious() == true) 
		{
			if(isMovingRight == true) 
			{
				cw = it.previous();
				isMovingRight = false;
			}
			
			if(it.hasPrevious() == true)
				cw = it.previous();
			
			if(panel != null)
				remove(panel);

			panel = new BoardPanel(cw, drawLetterState);
			add(panel);
			revalidate();
		}
		else 
		{
			JOptionPane.showMessageDialog(null, "You have reached the first element.");
		}
	}

	/**
	 * Action for NEXT button
	 * @param evt
	 */
	private void nextButtonActionEvent(ActionEvent evt) 
	{
		if(it.hasNext() == true) 
		{
			if(isMovingRight == false) 
			{
				cw = it.next();
				isMovingRight = true;
			}
			
			if(it.hasNext() == true)
				cw = it.next();

			if(panel != null)
				remove(panel);

			panel = new BoardPanel(cw, drawLetterState);
			add(panel);
			revalidate();
		}
		else 
		{
			JOptionPane.showMessageDialog(null, "You have reached the last element.");
		}
		
	}

	/**
	 * Action for Print button
	 * @param evt
	 */
	private void printActionEvent(ActionEvent evt)
	{
		int selection = JOptionPane.YES_OPTION;
		
		if(cw != null) 
		{
			if(drawLetterState == true)
				selection = JOptionPane.showConfirmDialog((Component) null, 
												"All letters are diplayed.\nDo you want to continue?", "Exit", 
										        JOptionPane.YES_NO_OPTION);
			
			if(selection == JOptionPane.YES_OPTION)
				PrintUtilities.printComponent(panel);
		}
		else 
		{
			JOptionPane.showMessageDialog(null, 
					"There is no crossword to print!","Error", 
					JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	/**
	 * Action for Exit button 
	 * @param evt
	 */
	private void exitActionEvent(ActionEvent evt) 
	{
		int selection = JOptionPane.showConfirmDialog((Component) null, 
										"Are you sure?", "Exit", 
								        JOptionPane.YES_NO_OPTION);
		
		if(selection == JOptionPane.YES_OPTION)
			this.dispose();  
	}
	
	/**
	 * Action for Unhide Passwords button
	 * @param evt
	 */
	private void checkboxActionEvent(ActionEvent evt)
	{
		drawLetterState = !drawLetterState;
		
		if(cw != null && panel != null)  // if there is crossword painted - refresh panel
		{ 
			remove(panel);
			panel = new BoardPanel(cw, drawLetterState);
			add(panel);
			revalidate();
		}
	}
	
	/**
	 * Action for Simple Strategy button
	 * @param evt
	 */
	private void radioB1ActionEvent(ActionEvent evt) 
	{
		s = new SimpleStrategy();
		isSimpleStrategy = true;
	}
	
	/**
	 * Action for Advanced Strategy button
	 * @param evt
	 */
	private void radioB2ActionEvent(ActionEvent evt) 
	{
		s = new AdvancedStrategy();
		isSimpleStrategy = false;
	}
		
	/**
	 * Action for About button
	 * @param evt
	 */
	private void aboutActionEvent(ActionEvent evt) 
	{
		JOptionPane.showMessageDialog(null, "CROSSWORD GENERATOR PROJECT\n\nAUTHOR: MARIUSZ NYZNAR\n\n");
	}
	
	/**
	 * Check string if all character of it are numerical
	 * @param str
	 * @return true if string contains only digits, if not - false
	 */
	protected boolean isNumeric(String str)
	{
	    for (char c : str.toCharArray())
	        if (!Character.isDigit(c)) 
	        	return false;
	    
	    return true;
	}
	
	/**
	 * Add all components to GUI and create listeners for them
	 * @param fr
	 * @return JMenuBar object with all components added.
	 */
	protected JMenuBar createGUI()
    {
    	 // Creates a menubar for a JFrame
        JMenuBar menuBar = new JMenuBar();
        
        // Add the menubar to the frame
        setJMenuBar(menuBar);
        
        // Define and add three drop down menu to the menubar
        JMenu fileMenu 		= new JMenu("File");
        JMenu settingsMenu	= new JMenu("Settings");
        JMenu aboutMenu 	= new JMenu("About");
        
        menuBar.add(fileMenu);
        menuBar.add(settingsMenu);
        menuBar.add(aboutMenu);
        
        //buttons for FILE
        JMenuItem generateAction 	= new JMenuItem("Generate crossword");
        JMenuItem opendbAction 		= new JMenuItem("Open database");
        JMenuItem saveAction 		= new JMenuItem("Save crossword");
        JMenuItem loadAction 		= new JMenuItem("Load directory");
        JMenuItem printAction 		= new JMenuItem("Print");
        JMenuItem exitAction 		= new JMenuItem("Exit");
        
        //buttons for SETTINGS
        JCheckBoxMenuItem checkboxAction 	= new JCheckBoxMenuItem("Unhide passwords");
        JRadioButtonMenuItem radioAction1 	= new JRadioButtonMenuItem("Simple Strategy");
        JRadioButtonMenuItem radioAction2 	= new JRadioButtonMenuItem("Advanced Strategy");
              
        //buttons for ABOUT
        JMenuItem aboutAction = new JMenuItem("About");
        
        // relation between both radioButtons
        ButtonGroup bg = new ButtonGroup();
        bg.add(radioAction1);
        bg.add(radioAction2);
        
        // add all buttons to menubar
        fileMenu.add(generateAction);
        fileMenu.add(opendbAction);
        fileMenu.addSeparator();;
        fileMenu.add(saveAction);
        fileMenu.add(loadAction);
        fileMenu.add(printAction);
        fileMenu.addSeparator();
        fileMenu.add(exitAction);
        
        settingsMenu.add(checkboxAction);
        settingsMenu.addSeparator();
        settingsMenu.add(radioAction1);
        settingsMenu.add(radioAction2);
        
        aboutMenu.add(aboutAction);
        
        // define actions ----------------------------------------------------
        
        generateAction.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
            	generateActionEvent(evt);
            }
        });
        
       opendbAction.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
            	opendbActionEvent(evt);
            }      
         });
        
        
        saveAction.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
                saveActionEvent(evt);
            }
        });
        loadAction.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
            	loadActionEvent(evt);
            }
        });
        
        printAction.addActionListener(new ActionListener(){
        	public void actionPerformed(java.awt.event.ActionEvent evt)
        	{
        		printActionEvent(evt);
        	}
        });
        
        exitAction.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
                exitActionEvent(evt);
            }
        });
        
        radioAction1.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
                radioB1ActionEvent(evt);
            }
        });
        
        radioAction2.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
            	radioB2ActionEvent(evt);
            }
        });
                
        aboutAction.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
                aboutActionEvent(evt);
            }
        });
        
        checkboxAction.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
            	checkboxActionEvent(evt);
            }
        });
        // action's definition end ---------------------------------------------------
        
        return menuBar;
        
    }    
}
