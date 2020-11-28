package Piece;

import Enums.Side;
import Interfaces.ICoordinate;

public class Pawn extends Piece  {
    private String _type = "P";

    public Pawn(Side side, int row, int column) {
        super(side, row, column);
    }

    //TODO incomplete
    //position, if allowed to be moved on, CAN be moved on. check if it is allowed
    public boolean legal(Side[][] locations, ICoordinate endPosition, ICoordinate lastMoveOrigin, ICoordinate lastMoveFinal){
        // check if moving forward
        // double and single

        // check if taking
        //check if empasant

        System.out.println("PAWN");

        return true;
    }

    @Override
    public String getType(){
        return _type;
    }

}
