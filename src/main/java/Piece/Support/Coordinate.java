package Piece.Support;

import Interfaces.ICoordinate;

public class Coordinate implements ICoordinate {

    private int _row;
    private int _column;

    public Coordinate(int row, int column) {
        _row = row;
        _column = column;
    }

    public int getRow() {
        return _row;
    }

    public int getColumn() {
        return _column;
    }

    public void setRow(int row) {
        _row = row;
    }

    public void setColumn(int column) {
        _column = column;
    }
}
