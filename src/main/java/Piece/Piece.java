package Piece;

import Enums.Side;
import Interfaces.Piece.IPiece;

import java.io.NotActiveException;

public class Piece implements IPiece {

    private String _type = "";
    private Side _side;
    private int _row;
    private int _column;

    public Piece(Side side, int row, int column) {
        _side = side;
        _row = row;
        _column = column;
    }

    public Side getSide(){
        return _side;
    }

    public int getRow() {
        return _row;
    }

    public int getColumn() {
        return _column;
    }

    public void setRow(int row ) {
        _row = row;
//        System.out.println("Method needs to be overwritten");
    }

    public void setColumn(int column) {
        _column = column;
//        System.out.println("Method needs to be overwritten");
    }

    public String getType(){
        return _type;
    }
}
