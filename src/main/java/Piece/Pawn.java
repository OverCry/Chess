package Piece;

import Enums.Side;
import Interfaces.ICoordinate;

public class Pawn extends Piece {
    private String _type = "P";

    public Pawn(Side side, int row, int column) {
        super(side, row, column);
    }

    //position, if allowed to be moved on, CAN be moved on. check if it is allowed
    public boolean legal(Side[][] locations, ICoordinate endPosition, ICoordinate lastMoveOrigin, ICoordinate lastMoveFinal, String lastType) {
        int smallRow = getRow();
        int smallColumn = getColumn();
        int bigRow = endPosition.getRow();
        int bigColumn = endPosition.getColumn();

        int rowDiff = Math.abs(smallRow - bigRow);
        int colDiff = Math.abs(smallColumn - bigColumn);

        // check if moving in the right direction 'forward'
        if ((getSide().equals(Side.WHITE) && bigRow - smallRow < 0) || (getSide().equals(Side.BLACK) && bigRow - smallRow > 0)) {
            return false;
        }

        // double and single
        if (colDiff == 0) {
            if (rowDiff == 2) {
                //check if original location is on 2 or 7
                if (smallRow != 1 && smallRow != 6) {
                    return false;
                }

                //check space in between
                if (locations[(getSide().equals(Side.WHITE) ? 2 : 5)][getColumn()] != null) {
                    return false;
                }

            } else if (rowDiff == 1) {
                //make sure there isnt a piece on it
                if (locations[bigRow][bigColumn] != null) {
                    return false;
                }
            } else {
                return false;

            }
            //is taking a piece
        } else if (colDiff == 1) {
            //ensure taking on next row
            if (rowDiff != 1) {
                return false;
            }

            // check if pawn previous move
            if (!lastType.equals("P")) {
                return false;
            }

            //check if pawn moved two squares
            if (Math.abs(lastMoveFinal.getRow()-lastMoveOrigin.getRow())!=2){
                return false;
            }

            //check if last piece is next to this piece
            if (lastMoveFinal.getRow()!=getRow() || lastMoveFinal.getColumn()!=bigColumn){
                return false;
            }
        } else {
            return false;
        }


        System.out.println("PAWN");

        return true;
    }

    @Override
    public String getType() {
        return _type;
    }

}
