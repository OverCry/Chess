package Piece;

import Enums.Side;
import Interfaces.ICoordinate;

public class Queen extends Piece {
    private String _type = "Q";

    public Queen(Side side, int row, int column) {
        super(side, row, column);
    }

    //TODO incomplete
    public boolean legal(Side[][] locations, ICoordinate endPosition, ICoordinate lastMoveOrigin, ICoordinate lastMoveFinal, String lastType) {
        int smallRow = getRow();
        int smallColumn = getColumn();
        int bigRow = endPosition.getRow();
        int bigColumn = endPosition.getColumn();

        int rowDiff = Math.abs(smallRow - bigRow);
        int colDiff = Math.abs(smallColumn - bigColumn);
        //check if 'bishop' or 'rook' move
        //bishop
        if (rowDiff == colDiff) {
            boolean rowPositive = true;
            boolean colPositive = true;

            if (endPosition.getColumn() < getColumn()) {
                // set small to start
                colPositive = false;
            }

            if (endPosition.getRow() < getRow()) {
                // set small to start
                rowPositive = false;
            }

            for (int move = 1; move < rowDiff; move++) {
                if (locations[smallRow + (rowPositive ? move : -move)][smallColumn + (colPositive ? move : -move)] != null) {
                    return false;
                }
            }

            //rook
        } else if (rowDiff == 0) {
            if ((bigColumn<smallColumn )){
                smallColumn = bigColumn;
            }

            for (int column = smallColumn +1 ; column<smallColumn+colDiff;column++){
                if (locations[getRow()][column]!=null){
                    return false;
                }
            }

        } else if (colDiff == 0) {
            if (bigRow<smallRow){
                smallRow = bigRow;
            }

            for (int row = smallRow +1 ; row<smallRow+rowDiff;row++){
                if (locations[row][getColumn()]!=null){
                    return false;
                }
            }
        } else {
            return false;
        }

        System.out.println("QUEEN");

        return true;
    }

    @Override
    public String getType() {
        return _type;
    }
}
