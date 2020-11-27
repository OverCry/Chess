package Piece;

import Enums.Side;
import Interfaces.Piece.IPiece;

public class Piece implements IPiece {

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
}
