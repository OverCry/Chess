package Piece;

import Enums.Side;

public class Piece {

    private Side _side;
    private int _row;
    private int _column;

    public Piece(Side side, int row, int column) {
        _side = side;
        _row = row;
        _column = column;
    }
}
