package Piece;

import Enums.Side;
import Interfaces.ICoordinate;
import Interfaces.Piece.IPiece;
import Piece.Support.Coordinate;

public abstract class Piece implements IPiece{

    private String _type = "";
    private Side _side;
    private ICoordinate positions;

    public Piece(Side side, int row, int column) {
        _side = side;
        positions = new Coordinate(row, column);
    }

    public Side getSide(){
        return _side;
    }

    public int getRow() {
        return positions.getRow();
    }

    public int getColumn() {
        return positions.getColumn();
    }

    public void setRow(int row ) {
        positions.setRow(row);
    }

    public void setColumn(int column) {
        positions.setColumn(column);
    }

    public String getType(){
        return _type;
    }

    public void setType(String type){
        _type = type;
    }
}
