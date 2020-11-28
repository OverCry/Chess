package Piece;

import Enums.Side;
import Interfaces.ICoordinate;

public class Pawn extends Piece  {
    private String _type = "P";

    public Pawn(Side side, int row, int column) {
        super(side, row, column);
    }

    //TODO incomplete
    public boolean legal(Side[][] locations, ICoordinate endPosition){
        return true;
    }

    @Override
    public String getType(){
        return _type;
    }

}
