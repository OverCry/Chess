package Piece;

import Enums.Side;
import Interfaces.ICoordinate;

public class Bishop extends Piece{
    private String _type = "B";

    public Bishop(Side side, int row, int column) {
        super(side, row, column);
    }

    //TODO incomplete
    public boolean legal(Side[][] locations, ICoordinate endPosition, ICoordinate lastMove){
        System.out.println("BISHOP");
        return true;
    }


    @Override
    public String getType(){
        return _type;
    }
}
