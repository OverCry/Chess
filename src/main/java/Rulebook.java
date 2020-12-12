import Enums.PieceType;
import Enums.Side;
import Interfaces.ICoordinate;
import Interfaces.IRulebook;
import Interfaces.Piece.IPiece;
import Piece.Support.Coordinate;

import java.util.List;
import java.util.Map;

public class Rulebook implements IRulebook {
    private static Rulebook instance = null;
    private boolean _enPassant = false;
    private Coordinate _castle = null;

    public boolean legal(IPiece movingPiece, ICoordinate endPosition, ICoordinate _lastOriginalPosition, ICoordinate _lastFinalPosition, PieceType[][] _representation, Map<Side, List<IPiece>> _pieceLocation) {
        switch (movingPiece.getType()) {
            case KING:
                return king(movingPiece, endPosition, _representation, _pieceLocation);
            case QUEEN:
                return queen(movingPiece, endPosition, _representation);
            case BISHOP:
                return bishop(movingPiece, endPosition, _representation);
            case ROOK:
                return rook(movingPiece, endPosition, _representation);
            case KNIGHT:
                return knight(movingPiece, endPosition);
            default:
                return pawn(movingPiece, endPosition, _lastOriginalPosition, _lastFinalPosition, _representation);
        }
    }

    @Override
    public boolean isEnPassant() {
        if (_enPassant) {
            _enPassant = false;
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
        _castle = null;
    }

    public boolean king(IPiece movingPiece, ICoordinate endPosition, PieceType[][] locations, Map<Side, List<IPiece>> pieces) {
        int originRow = movingPiece.getRow();
        int originColumn = movingPiece.getColumn();

        int bigRow = endPosition.getRow();
        int bigColumn = endPosition.getColumn();

        int rowDiff = Math.abs(originRow - bigRow);
        int colDiff = Math.abs(originColumn - bigColumn);

        // check if castleable
        if (!movingPiece.getMoved()) {
            if (bigRow == 0 || bigRow == 7) {
                //short castle
                if (bigColumn == 6) {

                    //check if rook at that position is moved or not
                    IPiece castleRook = null;
                    for (IPiece piece : pieces.get(movingPiece.getSide())) {
                        if (piece.getRow() == bigRow && piece.getColumn() == 7) {
                            castleRook = piece;
                            break;
                        }
                    }

                    //check if rook is set or moved
                    if (castleRook == null) {
                        return false;
                    }

                    //todo need to double check
                    if (castleRook.getMoved()) {
                        return false;
                    }

                    if (locations[bigRow][5] == null && locations[bigRow][6] == (null)) {
                        _castle = new Coordinate(bigRow, 7);
//                        _castle = true;
                        return true;
                    }
                    //long castle
                } else if (bigColumn == 2) {
                    //check if rook at that position is moved or not
                    IPiece castleRook = null;
                    for (IPiece piece : pieces.get(movingPiece.getSide())) {
                        if (piece.getRow() == bigRow && piece.getColumn() == 0) {
                            castleRook = piece;
                            break;
                        }
                    }

                    //check if rook is set or moved
                    if (castleRook == null) {
                        return false;
                    }

                    if (castleRook.getMoved()) {
                        return false;
                    }


                    if (locations[bigRow][1] == (null) && locations[bigRow][2] == (null) && locations[bigRow][3] == (null)) {
                        _castle = new Coordinate(bigRow, 0);

//                        _castle=true;
                        return true;
                    }
                } else {
                    return false;
                }
            }
            return false;
        }


        if (rowDiff > 1 || colDiff > 1) {
            return false;
        }
        movingPiece.setMoved();
        return true;
    }

    /**
     * logic for queen,
     * to see if it is performing a valid 'rook' or 'bishop' move
     *
     * @param movingPiece
     * @param endPosition
     * @param locations
     * @return
     */
    public boolean queen(IPiece movingPiece, ICoordinate endPosition, PieceType[][] locations) {
        int rowDiff = Math.abs(movingPiece.getRow() - endPosition.getRow());
        int colDiff = Math.abs(movingPiece.getColumn() - endPosition.getColumn());

        //check if 'bishop' or 'rook' move
        //rook
        if (rowDiff == 0 || colDiff == 0) {
            return rook(movingPiece, endPosition, locations);

            //bishop
        } else if (rowDiff == colDiff) {
            return bishop(movingPiece, endPosition, locations);
        }
        //otherwise invalid move
        return false;

    }

    /**
     * logic for bishop movement
     * ensure movement is legal according to bishop capabilities
     *
     * @param movingPiece
     * @param endPosition
     * @param locations
     * @return
     */
    public boolean bishop(IPiece movingPiece, ICoordinate endPosition, PieceType[][] locations) {
        int originRow = movingPiece.getRow();
        int originColumn = movingPiece.getColumn();

        int rowDiff = Math.abs(originRow - endPosition.getRow());
        int colDiff = Math.abs(originColumn - endPosition.getColumn());

        //diagonal direction check
        boolean rowPositive = true;
        boolean colPositive = true;

        //check if the difference is not the same (didnt move in a diagonal. Helps a latter check
        if (rowDiff != colDiff) {
            return false;
        }

        //calculate which direction the piece is moving
        if (colDiff > 0) {
            colPositive = false;
        }
        if (rowDiff > 0) {
            rowPositive = false;
        }

        for (int move = 1; move < rowDiff; move++) {
            if (locations[originRow + (rowPositive ? move : -move)][originColumn + (colPositive ? move : -move)] != null) {
                return false;
            }
        }
        return true;
    }

    //todo remove _piecelocation?
    public boolean pawn(IPiece movingPiece, ICoordinate endPosition, ICoordinate lastMoveOrigin, ICoordinate lastMoveFinal, PieceType[][] locations) {
        int originRow = movingPiece.getRow();
        int originColumn = movingPiece.getColumn();
        int bigRow = endPosition.getRow();
        int bigColumn = endPosition.getColumn();

        int rowDiff = Math.abs(originRow - bigRow);
        int colDiff = Math.abs(originColumn - bigColumn);

        if ((movingPiece.getSide().equals(Side.WHITE) && bigRow - originRow < 0) || (movingPiece.getSide().equals(Side.BLACK) && bigRow - originRow > 0)) {
            return false;
        }

        // double and single
        if (colDiff == 0) {
            if (rowDiff == 2) {
                //check if original location is on 2 or 7
                if (originRow != 1 && originRow != 6) {
                    return false;
                }

                //check space in between
                if (locations[(movingPiece.getSide().equals(Side.WHITE) ? 2 : 5)][originRow] != null) {
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
            if (locations[bigRow][bigColumn] != null) {
                return true;
            }

            //calculate en passant
            // check if pawn previous move
            //TODO DOUBLE CHECK
            if (!((locations[lastMoveFinal.getRow()][lastMoveFinal.getColumn()]) == PieceType.PAWN)) {
                return false;
            }

            //check if pawn moved two squares
            if (Math.abs(lastMoveFinal.getRow() - lastMoveOrigin.getRow()) != 2) {
                return false;
            }

            //check if last piece is next to this piece
            if (lastMoveFinal.getRow() != originRow || lastMoveFinal.getColumn() != bigColumn) {
                return false;
            }

            _enPassant = true;
        } else {
            return false;
        }

        return true;
    }

    /**
     * logic for rook movement
     * may consider doing more lambda expresses
     * @param movingPiece
     * @param endPosition
     * @param locations
     * @return
     */
    public boolean rook(IPiece movingPiece, ICoordinate endPosition, PieceType[][] locations) {
        int originRow = movingPiece.getRow();
        int originColumn = movingPiece.getColumn();
        int finalRow = endPosition.getRow();
        int finalColumn = endPosition.getColumn();

        int rowDiff = (originRow - finalRow);
        int colDiff = (originColumn - finalColumn);


        if (rowDiff == 0) {
            if (colDiff>0) {
                //swap around
                int dummy = finalColumn;
                finalColumn = originColumn;
                originColumn = dummy;
            }

            for (int column = originColumn + 1; column < finalColumn; column++) {
                if (locations[originRow][column] != null) {
                    return false;
                }
            }

        } else if (colDiff == 0) {
            if (rowDiff>0) {
                //swap around
                int dummy = finalRow;
                finalRow = originRow;
                originRow = dummy;
            }

            for (int row = originRow + 1; row < finalRow; row++) {
                if (locations[row][originColumn] != null) {
                    return false;
                }
            }
        }

        System.out.println("ROOK");
        return true;
    }

    /**
     * logic for knight movement
     * @param movingPiece
     * @param endPosition
     * @return
     */
    public boolean knight(IPiece movingPiece, ICoordinate endPosition) {
        //need to check if a legal knight move has taken place
        int rowDiff = Math.abs(movingPiece.getRow() - endPosition.getRow());
        int colDiff = Math.abs(movingPiece.getColumn() - endPosition.getColumn());

        //check if the difference is not the same (didnt move in a diagonal. Helps a latter check
        if (rowDiff == colDiff) {
            return false;
        }

        //check if the knight is moving in a L pattern
        if ((rowDiff != 1 && rowDiff != 2) || (colDiff != 1 && colDiff != 2)) {
            return false;
        }

        System.out.println("knight");
        return true;
    }


    //singleton class to be referenced in the board

    /**
     * Get the instance of the CourseMgr class.
     *
     * @return the singleton instance.
     */
    public static Rulebook getInstance() {
        if (instance == null) {
            instance = new Rulebook();
        }
        return instance;
    }
}
