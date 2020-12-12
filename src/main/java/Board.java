import Enums.Colours;
import Enums.PieceType;
import Enums.Side;
import Interfaces.IBoard;
import Interfaces.ICoordinate;
import Interfaces.IRulebook;
import Interfaces.Piece.IPiece;
import Piece.*;
import Piece.Support.Coordinate;
import Piece.Support.Rulebook;

import java.util.*;
import java.util.regex.Pattern;

public class Board implements IBoard {

    private IRulebook ruleCheck;
    //to quickly indicate which side each piece is on
    private Side[][] _locations = new Side[8][8];
    //for printing out the output
    private PieceType[][] _representation = new PieceType[8][8];
    //indicate what was the last move
    private ICoordinate _lastOriginalPosition = new Coordinate(-1, -1);
    private ICoordinate _lastFinalPosition = new Coordinate(-1, -1);

    //collection of all pieces
    private Map<Side, List<IPiece>> _pieceLocation = new HashMap<>();
    //current side's turn
    private Side turn = Side.WHITE;

    private static Scanner scanner = new java.util.Scanner(System.in);

    public Board() {
        ruleCheck = new Rulebook();

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
                _representation[row][column] = null;
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
                System.out.print(((row + column) % 2 == 0 ? Colours.YELLOW_BACKGROUND.getRepresentation() : Colours.PURPLE_BACKGROUND.getRepresentation()) + " "
                        + (_representation[row][column] != null ? _representation[row][column].getRepresentation() : " ")
                        + " " + Colours.RESET.getRepresentation());
            }
            System.out.println(Colours.WHITE.getRepresentation() + " " + (row + 1) + Colours.RESET.getRepresentation());
        }
        System.out.println(Colours.WHITE.getRepresentation() + " A  B  C  D  E  F  G  H" + Colours.RESET.getRepresentation());
    }

    public void play() {

        while (true) {
            printBoard();
            String original = "-h";
            System.out.println("\n" + turn.toString() + "'s move");
            System.out.println("(type \"-h\" for help) \n");

            while (original.equals("-h")) {
                System.out.print("Move Piece: ");
                original = scanner.nextLine();
                if (original.equals("-h")){
                    printHelp();
                }
            }
            //for testing
            //todo comment out when finished
            if (original.equals("P")) {
                printAll();
            }
            System.out.print("To: ");
            String end = scanner.nextLine();
            move(original.toLowerCase(), end.toLowerCase());
            if(kingAttack(end)){
                break;
            }
        }
    }

    private void move(String origin, String end) {

        //check if valid inputs
        String inputRegex = "[abcdefgh]{1}[12345678]{1}[+#]?";
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
        if (startPoint == null) {
            System.out.println("You have not selected a piece. Please try again");
            return;
        }

        //check to make sure you are moving your own piece
        if (!startPoint.equals(turn)) {
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
//        PieceType type = _representation[startPosition.getRow()][startPosition.getColumn()];

        IPiece movingPiece = null;
        //find piece
        for (IPiece piece : _pieceLocation.get(turn)) {
            //update piece data, update representation, update location
            if (piece.getRow() == startPosition.getRow() && piece.getColumn() == startPosition.getColumn()) {
                movingPiece = piece;
                break;
            }
        }

        if (movingPiece == null) {
            System.out.println("Something is very wrong for this to happen");
            return;
        }

        //check if move is allowed
        if (ruleCheck.legal(movingPiece, endPosition, _lastOriginalPosition, _lastFinalPosition, _representation, _pieceLocation)) {
            //perform move
            changePiece(movingPiece, startPosition, endPosition);
        }
    }

    /**
     * help method to indicate the end of the game
     * ends the game at checkmate
     * @param end
     * @return
     */
    private boolean kingAttack(String end){
        if (end.length()==3){
            String special = String.valueOf(end.charAt(2));
            if (special.equals("#")){
                System.out.println((turn.equals(Side.WHITE) ? Side.BLACK : Side.WHITE) + " wins");
                return true;
            } else if (special.equals("+")){
                System.out.println("\nCheck!");

            }
        }
        return false;
    }

    /**
     * method to change the state of helpful information, when a move is determined to be legal
     *
     * @param piece
     * @param startPosition
     * @param endPosition
     */
    private void changePiece(IPiece piece, ICoordinate startPosition, ICoordinate endPosition) {

        //check if en passant
        if (ruleCheck.isEnPassant()) {
            //remove the pawn
            for (IPiece pawn : _pieceLocation.get((turn.equals(Side.WHITE) ? Side.BLACK : Side.WHITE))) {
                if (pawn.getRow() == _lastFinalPosition.getRow() && pawn.getColumn() == _lastFinalPosition.getColumn()) {
                    _pieceLocation.get((turn.equals(Side.WHITE) ? Side.BLACK : Side.WHITE)).remove(pawn);
                    break;
                }
            }
            modifyDisplay(null, _lastFinalPosition);
            _locations[_lastFinalPosition.getRow()][_lastFinalPosition.getColumn()] = null;
        }

        //check if castled
        Coordinate rookPos = ruleCheck.isCastle();
        if (rookPos!=null){
            // move the rook
            IPiece castleRook = null;
            for (IPiece pieces : _pieceLocation.get(turn)){
                if (pieces.getRow() == rookPos.getRow() && pieces.getColumn() == rookPos.getColumn()){
                    Coordinate newPosition = new Coordinate(rookPos.getRow(), (rookPos.getColumn()==0 ? 3 : 5));
                    pieces.setPosition(newPosition);

                    //representation
                    modifyDisplay(_representation[rookPos.getRow()][rookPos.getColumn()], newPosition);
                    modifyDisplay(null, rookPos);

                    //location
                    _locations[newPosition.getRow()][newPosition.getColumn()] = _locations[rookPos.getRow()][rookPos.getColumn()];
                    _locations[rookPos.getRow()][rookPos.getColumn()] = null;
                    break;
                }
            }
            ruleCheck.resetCastle();
        }


        _lastOriginalPosition = piece.getPosition();
        _lastFinalPosition = endPosition;

        piece.setPosition(endPosition);

        //check if a piece is being taken
        if (_representation[endPosition.getRow()][endPosition.getColumn()] != null) {
            List<IPiece> pieces = _pieceLocation.get((turn.equals(Side.WHITE) ? Side.BLACK : Side.WHITE));
            for (IPiece find : pieces) {
                if (find.getRow() == endPosition.getRow() && find.getColumn() == endPosition.getColumn()) {
                    pieces.remove(find);
                    break;
                }
            }
        }

        //representation
        modifyDisplay(_representation[startPosition.getRow()][startPosition.getColumn()], endPosition);
        modifyDisplay(null, startPosition);

        //location
        _locations[endPosition.getRow()][endPosition.getColumn()] = _locations[startPosition.getRow()][startPosition.getColumn()];
        _locations[startPosition.getRow()][startPosition.getColumn()] = null;

        //check if promotion
        if (piece.getType().equals(PieceType.PAWN)){
            //check if row at the end
            if (endPosition.getRow()==0 ||endPosition.getRow()==7 ){
                System.out.println("Promote pawn to ?: ");
                String promo = "";
                String inputRegex = "[rnbq]{1}";

                if (!Pattern.matches(inputRegex, promo)) {
                    System.out.println("Choose from: (R,N,B,Q)");
                    promo = scanner.nextLine().toLowerCase();
                }

                promote(promo, endPosition);
            }
        }

        //change side's turn
        turn = (turn.equals(Side.WHITE) ? Side.BLACK : Side.WHITE);
    }

    private void populate() {
        //add to list
        for (Side side : Side.values()) {
            for (PieceType piece : PieceType.values()) {
                _pieceLocation.put(side, generateList(side));
            }
        }

        //populate representation
        for (List<IPiece> pieceList : _pieceLocation.values()) {
            for (IPiece piece : pieceList) {
                modifyDisplay(piece.getType(), piece.getPosition());
            }
        }
    }

    private void modifyDisplay(PieceType piece, ICoordinate position) {
        _representation[position.getRow()][position.getColumn()] = piece;
    }

    /**
     * helper method to convert column value to integer
     *
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

    private List<IPiece> generateList(Side side) {

        List<IPiece> pieces = new ArrayList<IPiece>();

        for (PieceType pieceType : PieceType.values()) {
            int row = 0;
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

    private void promote(String promo, ICoordinate position){
        IPiece promoted =null;
        int row = position.getRow();
        int column = position.getColumn();
        switch (promo){
            case "r":
                promoted = new Rook(turn, row, column);
                break;
            case "n":
                promoted = new Knight(turn, row, column);
                break;
            case "b":
                promoted = new Bishop(turn, row, column);
                break;
            case "q":
                promoted = new Queen(turn, row, column);
                break;
        }

        //remove pawn that is being promoted
        for(IPiece piece: _pieceLocation.get(turn)){
            if (piece.getRow()==row && piece.getColumn()==column){
                _pieceLocation.get(turn).remove(piece);
                break;
            }
        }
        //add piece to list
        _pieceLocation.get(turn).add(promoted);

        //add representation
        _representation[row][column]=promoted.getType();

    }

    private void printHelp(){
        System.out.println("\nHelp Menu: \n");

        System.out.println("1. To play, type the co-ordinates of the piece you would like to move");
        System.out.println("\t Start with the column (alphabets) first before the row (numbers)");
        System.out.println("\t For example, a move may be \"d2\"");
        System.out.println("2. When prompted, type the co-ordinates of where you want to place your piece");
        System.out.println("\t Again, start with the column, then the row");
        System.out.println("3. If your move will lead to a 'check' on the enemy king, please include a '+' symbol");
        System.out.println("\t For checkmate, use a '#' symbol instead");
        System.out.println("4. When castling, you will provide the location of where the king would be after castle");
        System.out.println("\t For example, white king castling short would end at 'G1', so you would type 'g1'");
        System.out.println("\n Good luck and have PHUN! \n\n");
    }

    //just for testing purposes
    private void printAll() {
        int count = 0;
        for (Side side : Side.values()) {
            for (IPiece piece : _pieceLocation.get(side)) {
                System.out.print(piece.getSide() + " ");
                System.out.print(piece.getType() + " ");
                System.out.print(piece.getRow() + " ");
                System.out.println(piece.getColumn() + " ");
                count++;
            }
        }
        System.out.println(count);
    }
}