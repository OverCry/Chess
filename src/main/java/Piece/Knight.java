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
        //need to check if a legal knight move has taken place
//        int smallRow=getRow();
//        int smallColumn=getColumn();
//        int bigRow=endPosition.getRow();
//        int bigColumn= endPosition.getColumn();
        int rowDiff = Math.abs(getRow()-endPosition.getRow());
        int colDiff = Math.abs(getColumn()-endPosition.getColumn());

        //check if the difference is not the same (didnt move in a diagonal. Helps a latter check
        if (rowDiff==colDiff){
            return false;
        }

        //check if the knight is moving in a L pattern
        if ((rowDiff != 1 && rowDiff != 2) || (colDiff != 1 && colDiff !=2 )) {
            return false;
        }


        System.out.println("KNIGHT");

        return true;
    }

    @Override
    public String getType(){
        return _type;
    }
}
