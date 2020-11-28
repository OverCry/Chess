package Piece;

import Enums.Side;
import Interfaces.ICoordinate;

public class Queen extends Piece  {
    private String _type = "Q";

    public Queen(Side side, int row, int column) {
        super(side, row, column);
    }

    //TODO incomplete
    public boolean legal(Side[][] locations, ICoordinate endPosition, ICoordinate lastMove){
        System.out.println("QUEEN");

        return true;
    }

    @Override
    public String getType(){
        return _type;
    }
}
