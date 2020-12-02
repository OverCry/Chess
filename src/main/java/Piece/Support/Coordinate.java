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

//    @Override
//    public boolean equals(Object o) {
//
//        // If the object is compared with itself then return true
//        if (o == this) {
//            return true;
//        }
//
//        if (!(o instanceof Coordinate)) {
//            return false;
//        }
//
//        // typecast o to Complex so that we can compare data members
//        Coordinate position = (Coordinate) o;
//
//        if (position._column == _column && position._row==_column){
//            return true;
//        }
//        return false;
//
//    }

}
