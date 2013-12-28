package utility;

import java.io.IOException;

import logic.Crossword;

public interface Writer {
    void write(Crossword cw)throws IOException ;
}
