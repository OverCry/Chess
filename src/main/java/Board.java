import Enums.Colours;
import Enums.PieceType;
import Enums.Side;
import Interfaces.IBoard;
import Interfaces.ICoordinate;
import Interfaces.Piece.IPiece;
import Piece.*;
import Piece.Support.Coordinate;

import java.util.*;
import java.util.regex.Pattern;

public class Board implements IBoard {

    //to quickly indicate which side each piece is on
    private Side[][] _locations = new Side[8][8];
    //for printing out the output
    private String[][] _representation = new String[8][8];
    //indicate what was the last move
    private  ICoordinate _lastOriginalPosition =new Coordinate(-1,-1);
    private ICoordinate _lastFinalPosition = new Coordinate(-1, -1);

    //collection of all pieces
    private Map<PieceType, List<Piece>> _pieceLocation = new HashMap<>();

    //current side's turn
    private Side turn = Side.WHITE;

    private static Scanner scanner = new java.util.Scanner(System.in);

    public Board() {

        //belonging
        for (int i = 0; i < 8; i++) {
            _locations[7][i] = Side.BLACK;
            _locations[6][i] = Side.BLACK;
            _locations[1][i] = Side.WHITE;
            _locations[0][i] = Side.WHITE;
        }

        //initial state
        for (int column = 0; column < 8; column++) {
            for (int row = 0; row < 8; row++) {
                //adding bars
                _representation[row][column] = " ";
            }
        }

        //populate mapping lists
        populate();
    }

    public void printBoard() {
        System.out.println();
        for (int row = 7; row >= 0; row--) {
            for (int column = 0; column < 8; column++) {
//                System.out.print(Enums.Colours.WHITE + "|"  + Enums.Colours.RESET);

                //font colour
                if (_locations[row][column] != null) {
                    //bold last move

                    if (_lastFinalPosition.getRow() == row && _lastFinalPosition.getColumn() == column) {
                        System.out.print((_locations[row][column].equals(Side.WHITE) ? Colours.RED_BOLD.getRepresentation() : Colours.BLACK_BOLD.getRepresentation()));
                    } else {
                        System.out.print((_locations[row][column].equals(Side.WHITE) ? Colours.RED.getRepresentation() : Colours.BLACK.getRepresentation()));
                    }
                }

                //background colour
                //TODO CHANGE BACKGROUND COLOUR TO NOT LOOK UGLY CURRENTLY JUST FOR CONTRAST
                System.out.print(((row + column) % 2 == 0 ? Colours.YELLOW_BACKGROUND.getRepresentation() : Colours.PURPLE_BACKGROUND.getRepresentation()) + " " + _representation[row][column] + " " + Colours.RESET.getRepresentation());
            }
            System.out.println(Colours.WHITE.getRepresentation() + " " + (row + 1) + Colours.RESET.getRepresentation());
        }
        System.out.println(Colours.WHITE.getRepresentation() + " A  B  C  D  E  F  G  H" + Colours.RESET.getRepresentation());
    }

    private void populate() {

        //add to list
        for (PieceType piece : PieceType.values()) {
            _pieceLocation.put(piece, generateList(piece));
        }

        //populate representation
        for (List<Piece> pieceList : _pieceLocation.values()) {
            for (IPiece piece : pieceList) {
                addBoard(piece.getType(), piece.getPosition());
            }
        }
    }

    private void addBoard(String piece, ICoordinate position) {
        _representation[position.getRow()][position.getColumn()] = piece;
    }

    public void play() {
        while (true) {
            printBoard();
            System.out.println("\n" + turn.toString() + "'s move");
            System.out.print("Move Piece: ");
            String original = scanner.nextLine();
            System.out.print("To: ");
            String end = scanner.nextLine();
            move(original.toLowerCase(), end.toLowerCase());
            if (checkmate()) {
                break;
            }
        }
    }

    private void move(String origin, String end) {

        //check if valid inputs
        String inputRegex = "[abcdefgh]{1}[12345678]{1}";
        if (!Pattern.matches(inputRegex, origin) || !Pattern.matches(inputRegex, end)) {
            System.out.println("Invalid Inputs. Please try again");
            return;
        }

        // check if final destination is free or different side
        // calculate positions
        ICoordinate startPosition = new Coordinate(Character.getNumericValue(origin.charAt(1)) - 1,
                convertAlphaToInt(origin.charAt(0)));
        ICoordinate endPosition = new Coordinate(Character.getNumericValue(end.charAt(1)) - 1,
                convertAlphaToInt(end.charAt(0)));

        Side startPoint = _locations[startPosition.getRow()][startPosition.getColumn()];

        //check to make sure the move piece exist
        if (startPoint==null){
            System.out.println("You have not selected a piece. Please try again");
            return;
        }

        //check to make sure you are moving your own piece
        if (!startPoint.equals(turn)){
            System.out.println("You cannot move the opposite side. Please try again");
            return;
        }


        Side endpoint = _locations[endPosition.getRow()][endPosition.getColumn()];
        //check to make sure your side isn't on the end location
        if (endpoint == turn) {
            System.out.println("You cannot try to take your own piece. Please try again");
            return;
        }

        // see what piece is being moved
        PieceType type = convertAlphaToType(_representation[startPosition.getRow()][startPosition.getColumn()]);

        IPiece movingPiece = null;
        //find piece
        for (IPiece piece : _pieceLocation.get(type)) {
            //update piece data, update representation, update location
            if (piece.getRow()==startPosition.getRow() && piece.getColumn()==startPosition.getColumn()) {
                movingPiece = piece;
                break;
            }
        }

        if (movingPiece == null) {
            System.out.println("Something is very wrong for this to happen");
            return;
        }

        //check if move is allowed

        //give last move type
        String lastMove = (_lastFinalPosition.getRow()==-1 ? "": _representation[_lastFinalPosition.getRow()][_lastFinalPosition.getColumn()]);

        //TODO insert logic to check if move is allowed
        if (movingPiece.legal(_locations,endPosition, _lastOriginalPosition, _lastFinalPosition,lastMove)){
            changePiece(movingPiece, startPosition, endPosition);
        }
    }

    /**
     * method to change the state of helpful information, when a move is determined to be legal
     * @param piece
     * @param startPosition
     * @param endPosition
     */
    private void changePiece(IPiece piece, ICoordinate startPosition, ICoordinate endPosition) {

        //todo moved store latest up to make it cohesive
        _lastOriginalPosition = piece.getPosition();
        //store latest move
        _lastFinalPosition = endPosition;

        piece.setPosition(endPosition);

        //representation
        addBoard(_representation[startPosition.getRow()][startPosition.getColumn()], endPosition);
        addBoard(" ", startPosition);

        //location
        _locations[endPosition.getRow()][endPosition.getColumn()] = _locations[startPosition.getRow()][startPosition.getColumn()];
        _locations[startPosition.getRow()][startPosition.getColumn()] = null;

        //change side's turn
        turn = (turn.equals(Side.WHITE) ? Side.BLACK : Side.WHITE);



    }

    /**
     * method to determine if a side is in checkmate
     * @return
     */
    private boolean checkmate() {
        //find position of other side king
        IPiece kingPiece = null;
        for (Piece piece : _pieceLocation.get(PieceType.KING)) {
            if (piece.getSide() == turn) {
                kingPiece = piece;
                break;
            }
        }

        //get a collection of movable spots
        List<ICoordinate> freeLocations = new ArrayList<>();
        //find 'free' spots
        for (int row = kingPiece.getRow() - 1; row < kingPiece.getRow() + 2; row++) {
            for (int column = kingPiece.getColumn() - 1; column < kingPiece.getColumn() + 2; column++) {
                //check if on board
                if (row >= 0 && row < 8 && column >= 0 && column < 8) {
                    //check if position is NOT same side
//                    if (!_locations[row][column].equals(turn)){
                    if (!turn.equals(_locations[row][column])) {
                        // store locations
                        freeLocations.add(new Coordinate(row, column));
                    }
                }
            }
        }
        //add its own position
        freeLocations.add(kingPiece.getPosition());
        //compare with 'attack' squares
        for (PieceType type : PieceType.values()) {
            for (Piece piece : _pieceLocation.get(type)) {
                //todo reuse check method
            }
        }

        //check if the piece is moveable or not
        if (freeLocations.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * helper method to convert column value to integer
     * @param character
     * @return
     */
    private int convertAlphaToInt(char character) {
        int column = 0;
        switch (character) {
            case 'h':
                column++;
            case 'g':
                column++;
            case 'f':
                column++;
            case 'e':
                column++;
            case 'd':
                column++;
            case 'c':
                column++;
            case 'b':
                column++;
            case 'a':
//                column++;
        }
        return column;
    }

    /**
     * helper method to determine what the moving piece type is
     * @param character
     * @return
     */
    private PieceType convertAlphaToType(String character) {
        switch (character.toLowerCase()) {
            case "k":
                return PieceType.KING;
            case "q":
                return PieceType.QUEEN;
            case "b":
                return PieceType.BISHOP;
            case "r":
                return PieceType.ROOK;
            case "n":
                return PieceType.KNIGHT;
            default:
                return PieceType.PAWN;
        }
    }

    /**
     * helper method to generate to initial board state
     * @param pieceType
     * @return
     */
    private List<Piece> generateList(PieceType pieceType) {

        int row = 0;
        List<Piece> pieces = new ArrayList<Piece>();
        for (Side side : Side.values()) {

            if (pieceType != PieceType.PAWN) {
                if (side.equals(Side.BLACK)) {
                    row = 7;
                }
            } else {
                if (side.equals(Side.BLACK)) {
                    row = 6;
                } else {
                    row = 1;
                }
            }
            switch (pieceType) {
                case KING:
                    pieces.add(new King(side, row, 4));
                    break;
                case QUEEN:
                    pieces.add(new Queen(side, row, 3));
                    break;
                case BISHOP:
                    for (int i = 0; i < 2; i++) {
                        pieces.add(new Bishop(side, row, 2 + (i * 3)));
                    }
                    break;
                case KNIGHT:
                    for (int i = 0; i < 2; i++) {
                        pieces.add(new Knight(side, row, 1 + (i * 5)));
                    }
                    break;
                case ROOK:
                    for (int i = 0; i < 2; i++) {
                        pieces.add(new Rook(side, row, (i * 7)));
                    }
                    break;
                case PAWN:
                    //0 to 7
                    for (int i = 0; i < 8; i++) {
                        pieces.add(new Pawn(side, row, i));
                    }
                    break;
            }
        }
        return pieces;
    }
}

