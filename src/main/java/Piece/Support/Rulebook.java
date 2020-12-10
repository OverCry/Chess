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
    private boolean _EnPassant = false;
//    private boolean _castle = false;
    private Coordinate _castle = null;

    public boolean legal(IPiece movingPiece, ICoordinate endPosition, ICoordinate _lastOriginalPosition, ICoordinate _lastFinalPosition, PieceType[][] _representation, Map<Side, List<Piece>> _pieceLocation){
        switch (movingPiece.getType()){
            case KING:
                return king(movingPiece,endPosition,_representation,_pieceLocation);
            case QUEEN:
                return queen(movingPiece,endPosition,_representation);
            case BISHOP:
                return bishop(movingPiece,endPosition,_representation);
            case ROOK:
                return rook(movingPiece,endPosition, _representation);
            case KNIGHT:
                return knight(movingPiece,endPosition);
            default:
                return pawn(movingPiece,endPosition, _lastOriginalPosition, _lastFinalPosition, _representation, _pieceLocation);
        }
    }

    @Override
    public boolean isEnPassant() {
        if (_EnPassant){
            _EnPassant =false;
            return true;
        }
        return false;
    }

    @Override
    public Coordinate isCastle() {
        return _castle;
    }

    @Override
    public void resetCastle() {
        _castle=null;
    }

    public boolean king(IPiece movingPiece, ICoordinate endPosition,PieceType[][] locations, Map<Side,List<Piece>> pieces){
        int smallRow=getRow(movingPiece);
        int smallColumn=getColumn(movingPiece);
        int bigRow=endPosition.getRow();
        int bigColumn= endPosition.getColumn();

        int rowDiff = Math.abs(smallRow-bigRow);
        int colDiff = Math.abs(smallColumn-bigColumn);

        // check if castleable
        if (!movingPiece.getMoved()){
            if (bigRow==0 || bigRow==7){
                //short castle
                if (bigColumn==6){

                    //check if rook at that position is moved or not
                    IPiece castleRook = null;
                    for (IPiece piece: pieces.get(movingPiece.getSide())){
                        if (piece.getRow()==bigRow && piece.getColumn()==7){
                            castleRook=piece;
                            break;
                        }
                    }

                    //check if rook is set or moved
                    if (castleRook==null){
                        return false;
                    }

                    //todo need to double check
                    if (castleRook.getMoved()){
                        return false;
                    }

                    if (locations[bigRow][5]==null && locations[bigRow][6]==(null)){
                        _castle = new Coordinate(bigRow,7);
//                        _castle = true;
                        return true;
                    }
                    //long castle
                } else if (bigColumn==2){
                    //check if rook at that position is moved or not
                    IPiece castleRook = null;
                    for (IPiece piece: pieces.get(movingPiece.getSide())){
                        if (piece.getRow()==bigRow && piece.getColumn()==0){
                            castleRook=piece;
                            break;
                        }
                    }

                    //check if rook is set or moved
                    if (castleRook==null){
                        return false;
                    }

                    if (castleRook.getMoved()){
                        return false;
                    }



                    if (locations[bigRow][1]==(null) && locations[bigRow][2]==(null) && locations[bigRow][3]==(null)){
                        _castle = new Coordinate(bigRow,0);

//                        _castle=true;
                        return true;
                    }
                } else {
                    return false;
                }
            }
            return false;
        }


        if (rowDiff>1 || colDiff>1 ){
            return false;
        }
        movingPiece.setMoved();
        return true;
    }

    /**
     * logic for queen,
     * to see if it is performing a valid 'rook' or 'bishop' move
     * @param movingPiece
     * @param endPosition
     * @param locations
     * @return
     */
    public boolean queen(IPiece movingPiece, ICoordinate endPosition, PieceType[][] locations){
        int rowDiff = Math.abs(movingPiece.getRow() - endPosition.getRow());
        int colDiff = Math.abs(movingPiece.getColumn() - endPosition.getColumn());

        //check if 'bishop' or 'rook' move
        //rook
        if (rowDiff==0 || colDiff==0){
            return rook(movingPiece,endPosition,locations);

            //bishop
        } else if (rowDiff==colDiff){
            return bishop(movingPiece,endPosition,locations);
        }
        //otherwise invalid move
        return false;

    }

    /**
     * logic for bishop movement
     * ensure movement is legal according to bishop capabilities
     * @param movingPiece
     * @param endPosition
     * @param locations
     * @return
     */
    public boolean bishop(IPiece movingPiece, ICoordinate endPosition, PieceType[][] locations){
        int originRow = movingPiece.getRow();
        int originColumn = movingPiece.getColumn();

        int rowDiff = Math.abs(originRow - endPosition.getRow());
        int colDiff = Math.abs(originColumn - endPosition.getColumn());

        //diagonal direction check
        boolean rowPositive = true;
        boolean colPositive = true;

        //check if the difference is not the same (didnt move in a diagonal. Helps a latter check
        if (rowDiff!=colDiff){
            return false;
        }

        //calculate which direction the piece is moving
        if (colDiff>0) {
            colPositive = false;
        }
        if (rowDiff>0) {
            rowPositive=false;
        }

        for (int move = 1; move < rowDiff; move++) {
            if (locations[originRow+(rowPositive ? move : - move)][originColumn+ (colPositive ? move : - move)] != null) {
                return false;
            }
        }
        return true;
    }

    public boolean pawn(IPiece movingPiece, ICoordinate endPosition, ICoordinate lastMoveOrigin, ICoordinate lastMoveFinal, PieceType[][] locations, Map<Side, List<Piece>> _pieceLocation){
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
                if (locations[(movingPiece.getSide().equals(Side.WHITE) ? 2 : 5)][smallColumn] != null) {
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

            //check if end location is a piece
            if (locations[bigRow][bigColumn]!=null){
                return true;
            }

            //calculate en passant
            // check if pawn previous move
            //TODO DOUBLE CHECK
            if (!((locations[lastMoveFinal.getRow()][lastMoveFinal.getColumn()]) ==PieceType.PAWN)) {
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

            _EnPassant=true;
        } else {
            return false;
        }

        return true;
    }

    public boolean rook(IPiece movingPiece, ICoordinate endPosition, PieceType[][] locations){
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
                if (locations[smallRow][column]!=null){
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
                if (locations[row][smallColumn]!=null){
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
