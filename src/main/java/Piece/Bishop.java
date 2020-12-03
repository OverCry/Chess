package Piece;

import Enums.Side;
import Interfaces.ICoordinate;

public class Bishop extends Piece{
    private String _type = "B";

    public Bishop(Side side, int row, int column) {
        super(side, row, column);
    }

    //TODO incomplete
    public boolean legal(Side[][] locations, ICoordinate endPosition, ICoordinate lastMoveOrigin, ICoordinate lastMoveFinal, String lastType){
        //need to check if a legal bishop move has taken place
        int smallRow=getRow();
        int smallColumn=getColumn();
        int bigRow=endPosition.getRow();
        int bigColumn= endPosition.getColumn();

        int rowDiff = Math.abs(smallRow-bigRow);
        int colDiff = Math.abs(smallColumn-bigColumn);

        //diagonal direction check
        boolean rowPositive = true;
        boolean colPositive = true;

        //check if the difference is not the same (didnt move in a diagonal. Helps a latter check
        if (rowDiff!=colDiff){
            return false;
        }


        //same row
        //check which column is larger
        if (endPosition.getColumn() < getColumn()) {
            // set small to start
            colPositive = false;
        }

        if (endPosition.getRow() < getRow()) {
            // set small to start
            rowPositive=false;
        }

        for (int move = 1; move < rowDiff; move++) {
            if (locations[smallRow+(rowPositive ? move : - move)][smallColumn+ (colPositive ? move : - move)] != null) {
                return false;
            }
        }


        System.out.println("BISHOP");
        return true;
    }


    @Override
    public String getType(){
        return _type;
    }
}
