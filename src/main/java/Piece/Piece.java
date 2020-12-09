package Piece;

import Enums.PieceType;
import Enums.Side;
import Interfaces.ICoordinate;
import Interfaces.Piece.IPiece;
import Piece.Support.Coordinate;

public abstract class Piece implements IPiece{

    private PieceType _type = null;
    private final Side _side;
    private ICoordinate _positions;
    private boolean _moved =false;

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

    public boolean getMoved() {
        return _moved;
    }

    public void setMoved() {
        _moved=true;
    }


    public ICoordinate getPosition(){
        return _positions;
    }


    public void setPosition(ICoordinate coordinate){
        _positions =coordinate;
    }


    public PieceType getType(){
        return _type;
    }

    public void PieceType(PieceType type){
        _type = type;
    }
}
