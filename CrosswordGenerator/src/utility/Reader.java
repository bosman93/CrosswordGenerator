package utility;

import java.io.IOException;

import logic.Crossword;

public interface Reader {
     void getAllCrosswords() throws IOException,ClassNotFoundException;
     Crossword getCrossword(String filename) throws IOException, ClassNotFoundException;
}
