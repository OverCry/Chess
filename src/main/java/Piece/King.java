package Piece;

import Enums.Side;
import Interfaces.ICoordinate;

public class King extends Piece{
    private String _type = "K";

    public King(Side side, int row, int column) {
        super(side, row, column);
    }

    //TODO incomplete
    public boolean legal(Side[][] locations, ICoordinate endPosition, ICoordinate lastMove){
        System.out.println("KING");

        return true;
    }

    @Override
    public String getType(){
        return _type;
    }
}
