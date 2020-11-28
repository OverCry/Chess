package Piece;

import Enums.Side;
import Interfaces.ICoordinate;
import Interfaces.Piece.IPiece;
import Piece.Support.Coordinate;

public abstract class Piece implements IPiece{

    private String _type = "";
    private final Side _side;
    private ICoordinate _positions;

    public Piece(Side side, int row, int column) {
        _side = side;
        _positions = new Coordinate(row, column);
    }

    public Side getSide(){
        return _side;
    }

    public int getRow() {
        return _positions.getRow();
    }

    public int getColumn() {
        return _positions.getColumn();
    }

    public void setRow(int row ) {
        _positions.setRow(row);
    }

    public void setColumn(int column) {
        _positions.setColumn(column);
    }

    public ICoordinate getPosition(){
        return _positions;
    }


    public void setPosition(ICoordinate coordinate){
        _positions =coordinate;
    }


    public String getType(){
        return _type;
    }

    public void setType(String type){
        _type = type;
    }
}
