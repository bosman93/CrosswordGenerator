package utility;

import java.awt.*;
import java.awt.print.*;

import javax.swing.JOptionPane;

public class PrintUtilities implements Printable 
{
	  private Component componentToBePrinted;

	  /**
	   * Print component
	   * @param c Component to print.
	   */
	  public static void printComponent(Component c) 
	  {
		  new PrintUtilities(c).print();
	  }
	  
	  public PrintUtilities(Component componentToBePrinted) 
	  {
		  this.componentToBePrinted = componentToBePrinted;
	  }
	 
	  protected void print() 
	  {
		  PrinterJob printJob = PrinterJob.getPrinterJob(); 
		  printJob.setJobName("New crossword");
		  printJob.setPrintable(this);
		  
		  if (printJob.printDialog()) {
			  try {
				  printJob.print();
			  } 
			  catch(PrinterException pe) {
				  JOptionPane.showMessageDialog(null, 
												"Printing error!" + pe, 
												"Error", 
												JOptionPane.ERROR_MESSAGE);
			  }
		  }
	  }
	  
	  @Override
	  public int print(Graphics g, PageFormat pageFormat, int pageIndex) 
	  {
		  if (pageIndex > 0) {
			  return(NO_SUCH_PAGE);  // returned if page does not exist
		  } 
		  else {					// paint if page exist
			  Graphics2D g2d = (Graphics2D)g;
			  g2d.setBackground(Color.WHITE);
			  pageFormat.setOrientation(PageFormat.LANDSCAPE);
			  g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY()); // set translate position to left upper point of pa
			  componentToBePrinted.paint(g2d);
	      
			  return(PAGE_EXISTS);
		  }
	  }

}
