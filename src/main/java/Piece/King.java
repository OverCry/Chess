package Piece;

import Enums.Side;
import Interfaces.ICoordinate;

public class King extends Piece{
    private String _type = "K";

    public King(Side side, int row, int column) {
        super(side, row, column);
    }

    public boolean legal(Side[][] locations, ICoordinate endPosition, ICoordinate lastMoveOrigin, ICoordinate lastMoveFinal){
        //TODO if wanted, check if this move will result in a check, otherwise, be happy
        if (Math.abs(endPosition.getRow()-getRow())>1 && Math.abs(endPosition.getColumn()-getColumn())>1 ){
            return false;
        }
        System.out.println("KING");
        return true;
    }

    @Override
    public String getType(){
        return _type;
    }
}
