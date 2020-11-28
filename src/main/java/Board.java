import Enums.PieceType;
import Enums.Side;
import Interfaces.IBoard;
import Interfaces.Piece.IPiece;
import Piece.*;

import java.util.*;

public class Board implements IBoard {

    //to quickly indicate which side each piece is on
    private Side[][] _locations = new Side[8][8];
    //for printing out the output
    private String[][] _representation = new String[8][8];
    //indicate what was the last move
    private int lastRow = -1;
    private int lastColumn = -1;

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
//                System.out.print(Colours.WHITE + "|"  + Colours.RESET);

                //font colour
                if (_locations[row][column]!=null){
                    //bold last move
                    if (lastRow == row && lastColumn == column){
                        System.out.print((_locations[row][column].equals(Side.WHITE) ? Colours.RED_BOLD : Colours.BLACK_BOLD));
                    } else {
                        System.out.print((_locations[row][column].equals(Side.WHITE) ? Colours.RED : Colours.BLACK));
                    }
                }

                //background colour
                //TODO CHANGE BACKGROUND COLOUR TO NOT LOOK UGLY CURRENTLY JUST FOR CONTRAST
                System.out.print(((row+column)%2==0 ? Colours.YELLOW_BACKGROUND : Colours.PURPLE_BACKGROUND )+ " "+_representation[row][column]+ " " + Colours.RESET);
            }
            System.out.println(Colours.WHITE + "|" + (row + 1) + Colours.RESET);
        }
        System.out.println(Colours.WHITE +" A  B  C  D  E  F  G  H"+ Colours.RESET);
    }

    private void populate() {

        //add to list
        for (PieceType piece : PieceType.values()) {
            _pieceLocation.put(piece, generateList(piece));
        }

        //populate representation
        for (List<Piece> pieceList : _pieceLocation.values()) {
            for (IPiece piece : pieceList) {
                addBoard(piece.getType(), piece.getRow(), piece.getColumn());
            }
        }
    }

    private void addBoard(String piece, int row, int column) {
        _representation[row][column] = piece;
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
        }
    }

        private void move(String origin, String end) {

        PieceType movingPiece = null;

        // check if lengths are the same
        if (origin.length() != end.length() || origin.equals(end)) {
            System.out.println("Invalid Inputs. Please try again");
            return;
        }

        // check if final destination is free or different side
        // calculate column
        int startRow = Character.getNumericValue(origin.charAt(1))-1;
        int endRow = Character.getNumericValue(end.charAt(1))-1;
        int startColumn = convertAlphaToInt(origin.charAt(0));
        int endColumn = convertAlphaToInt(end.charAt(0));

        Side endpoint = _locations[endRow][endColumn];
        //check to make sure your side isn't on the desired location
        if (endpoint == turn) {
            System.out.println("Please move a piece. Please try again");
            return;
        }

        // see what piece is being moved
        PieceType type = convertAlphaToType(_representation[startRow][startColumn]);

        //check if move is allowed
        //TODO insert logic to check if move is allowed


        //move the piece
        for (IPiece piece : _pieceLocation.get(type)) {

            //update piece data, update representation, update location
            if (piece.getRow() == startRow && piece.getColumn() == startColumn) {
                //piece
                piece.setRow(endRow);
                piece.setColumn(endColumn);

                //representation
                addBoard(_representation[startRow][startColumn],endRow,endColumn);
                addBoard(" ",startRow,startColumn);

                //location
                _locations[endRow][endColumn] = _locations[startRow][startColumn];
                _locations[startRow][startColumn] = null;

                //change side's turn
                turn = (turn.equals(Side.WHITE) ? Side.BLACK:Side.WHITE);
                break;
            }
        }

        System.out.println("Invalid Move. Please try again");
    }

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