package Piece.Support;

import Enums.PieceType;
import Enums.Side;
import Interfaces.ICoordinate;
import Interfaces.IRulebook;
import Interfaces.Piece.IPiece;
import Piece.Piece;

import java.util.List;
import java.util.Map;

public class Rulebook implements IRulebook {
    private static Rulebook instance = null;

    public boolean legal(IPiece movingPiece, ICoordinate endPosition, ICoordinate _lastOriginalPosition, ICoordinate _lastFinalPosition, String[][] _representation, Map<PieceType, List<Piece>> _pieceLocation){
        System.out.println(movingPiece.getType());
        switch (movingPiece.getType().toLowerCase()){
            case "k":
                return king(movingPiece,endPosition);
            case "q":
                return queen(movingPiece,endPosition,_representation);
            case "b":
                return bishop(movingPiece,endPosition,_representation);
            case "r":
                return rook(movingPiece,endPosition, _representation);
            case "n":
                return knight(movingPiece,endPosition);
            default:
                return pawn(movingPiece,endPosition, _lastOriginalPosition, _lastFinalPosition, _representation, _pieceLocation);
        }
    }

    public boolean king(IPiece movingPiece, ICoordinate endPosition){
        int smallRow=getRow(movingPiece);
        int smallColumn=getColumn(movingPiece);
        int bigRow=endPosition.getRow();
        int bigColumn= endPosition.getColumn();

        int rowDiff = Math.abs(smallRow-bigRow);
        int colDiff = Math.abs(smallColumn-bigColumn);

        // check if castleable
//        if (_moved){
//            if (bigRow==0 || bigRow==7){
//                //short castle
//                if (bigColumn==6){
//                    if (locations[bigRow][5]!=null || locations[bigRow][6]!=null  ){
//                        return false;
//                    }
//                    //long castle
//                } else if (bigColumn==2){
//
//                } else {
//                    return false;
//                }
//            }
//        }


        if (rowDiff>1 || colDiff>1 ){
            return false;
        }
        movingPiece.setMoved();
        return true;
    }

    public boolean queen(IPiece movingPiece, ICoordinate endPosition, String[][] locations){
        int smallRow = getRow(movingPiece);
        int smallColumn = getColumn(movingPiece);
        int bigRow = endPosition.getRow();
        int bigColumn = endPosition.getColumn();

        int rowDiff = Math.abs(smallRow - bigRow);
        int colDiff = Math.abs(smallColumn - bigColumn);

        //check if 'bishop' or 'rook' move
        //rook
        if (smallRow==bigRow || smallColumn==bigColumn){
            return rook(movingPiece,endPosition,locations);

            //bishop
        } else if (rowDiff==colDiff){
            return bishop(movingPiece,endPosition,locations);

        }
        return false;

    }

    public boolean bishop(IPiece movingPiece, ICoordinate endPosition, String[][] locations){
        int smallRow=getRow(movingPiece);
        int smallColumn=getColumn(movingPiece);
        int bigRow=endPosition.getRow();
        int bigColumn= endPosition.getColumn();

        int rowDiff = (smallRow-bigRow);
        int colDiff = (smallColumn-bigColumn);

        //diagonal direction check
        boolean rowPositive = true;
        boolean colPositive = true;

        //check if the difference is not the same (didnt move in a diagonal. Helps a latter check
        if (Math.abs(rowDiff)!=Math.abs(colDiff)){
            return false;
        }

        //same row
        //check which column is larger
        if (colDiff>0) {
            // set small to start
            colPositive = false;
        }

        if (rowDiff>0) {
            // set small to start
            rowPositive=false;
        }

        for (int move = 1; move < Math.abs(rowDiff); move++) {
            if (locations[smallRow+(rowPositive ? move : - move)][smallColumn+ (colPositive ? move : - move)] != " ") {
                return false;
            }
        }
        return true;
    }

    public boolean pawn(IPiece movingPiece, ICoordinate endPosition, ICoordinate lastMoveOrigin, ICoordinate lastMoveFinal, String[][] locations, Map<PieceType, List<Piece>> _pieceLocation){
        int smallRow=getRow(movingPiece);
        int smallColumn=getColumn(movingPiece);
        int bigRow=endPosition.getRow();
        int bigColumn= endPosition.getColumn();

        int rowDiff = Math.abs(smallRow-bigRow);
        int colDiff = Math.abs(smallColumn-bigColumn);

        if ((movingPiece.getSide().equals(Side.WHITE) && bigRow - smallRow < 0) || (movingPiece.getSide().equals(Side.BLACK) && bigRow - smallRow > 0)) {
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
                if (locations[(movingPiece.getSide().equals(Side.WHITE) ? 2 : 5)][smallColumn] != " ") {
                    return false;
                }

            } else if (rowDiff == 1) {
                //make sure there isnt a piece on it
                if (locations[bigRow][bigColumn] != " ") {
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

            //check if end location is a piece
            if (locations[bigRow][bigColumn]!=" "){
                return true;
            }

            //calculate en passant
            // check if pawn previous move
            if (!(locations[lastMoveFinal.getRow()][lastMoveFinal.getColumn()]).equals("P")) {
                return false;
            }

            //check if pawn moved two squares
            if (Math.abs(lastMoveFinal.getRow()-lastMoveOrigin.getRow())!=2){
                return false;
            }

            //check if last piece is next to this piece
            if (lastMoveFinal.getRow()!=smallRow|| lastMoveFinal.getColumn()!=bigColumn){
                return false;
            }
        } else {
            return false;
        }

        return true;
    }

    public boolean rook(IPiece movingPiece, ICoordinate endPosition, String[][] locations){
        int smallRow=getRow(movingPiece);
        int smallColumn=getColumn(movingPiece);
        int bigRow=endPosition.getRow();
        int bigColumn= endPosition.getColumn();

        int rowDiff = Math.abs(smallRow-bigRow);
        int colDiff = Math.abs(smallColumn-bigColumn);

        if (rowDiff==0){
            if (bigColumn<smallColumn){
                //swap around
                int dummy = bigColumn;
                bigColumn = smallColumn;
                smallColumn = dummy;
            }

            for (int column = smallColumn +1 ; column<bigColumn;column++){
                if (locations[smallRow][column]!=" "){
                    return false;
                }
            }

        } else if (colDiff==0){
            if (bigRow<smallRow){
                //swap around
                int dummy = bigRow;
                bigRow = smallRow;
                smallRow = dummy;
            }

            for (int row = smallRow +1 ; row<bigRow;row++){
                if (locations[row][smallColumn]!=" "){
                    return false;
                }
            }
        }

        System.out.println("ROOK");
        return true;
    }

    public boolean knight(IPiece movingPiece, ICoordinate endPosition){
        //need to check if a legal knight move has taken place
        int rowDiff = Math.abs(movingPiece.getRow()-endPosition.getRow());
        int colDiff = Math.abs(movingPiece.getColumn()-endPosition.getColumn());

        //check if the difference is not the same (didnt move in a diagonal. Helps a latter check
        if (rowDiff==colDiff){
            return false;
        }

        //check if the knight is moving in a L pattern
        if ((rowDiff != 1 && rowDiff != 2) || (colDiff != 1 && colDiff !=2 )) {
            return false;
        }

        System.out.println("knight");
        return true;
    }








    private int getRow(IPiece piece){
        return piece.getRow();
    }

    private int getColumn(IPiece piece){
        return piece.getColumn();
    }

    //singleton class to be referenced in the board
    /**
     * Get the instance of the CourseMgr class.
     * @return the singleton instance.
     */
    public static Rulebook getInstance() {
        if (instance == null) {
            instance = new Rulebook();
        }
        return instance;
    }
}
