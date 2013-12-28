package logic;

import board.*;
import dictionary.*;

public interface Strategy
{
    public CwEntry findEntry(Crossword cw) throws CrosswordGenerateException;
    public void updateBoard(Board b, CwEntry e) throws CrosswordGenerateException;

}