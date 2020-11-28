package Piece;

import Enums.Side;
import Interfaces.ICoordinate;

public class Knight extends Piece  {
    private String _type = "N";

    public Knight(Side side, int row, int column) {
        super(side, row, column);
    }

    //TODO incomplete
    //position, if allowed to be moved on, CAN be moved on. check if it is allowed
    public boolean legal(Side[][] locations, ICoordinate endPosition, ICoordinate lastMoveOrigin, ICoordinate lastMoveFinal){
        System.out.println("KNIGHT");

        return true;
    }

    @Override
    public String getType(){
        return _type;
    }
}
