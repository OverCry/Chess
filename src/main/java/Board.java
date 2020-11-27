import Enums.PieceType;
import Enums.Side;
import Interfaces.IBoard;
import Interfaces.Piece.IPiece;
import Piece.*;

import java.util.*;

public class Board implements IBoard {

    private Side[][] _locations = new Side[8][8];
    private String[][] _representation = new String[8][8];

    private Map<PieceType, List<Piece>> _whitePieceLocations = new HashMap<>();
    private Map<PieceType, List<Piece>> _blackPieceLocations = new HashMap<>();

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
        printBoard();
        populate();
    }

    public void printBoard() {
        for (int row = 7; row >= 0; row--) {
            for (int column = 0; column < 8; column++) {
                System.out.print("|"+_representation[row][column]);
            }
            System.out.println("|" + (row + 1));
        }
        System.out.println(" A B C D E F G H");
    }

    private void populate() {

        //add to list
        for (PieceType piece : PieceType.values()) {
            _whitePieceLocations.put(piece, generateList(piece, Side.WHITE));
            _blackPieceLocations.put(piece, generateList(piece, Side.BLACK));
        }

        //populate representation
        for (List<Piece> pieceList : _whitePieceLocations.values()) {
            for (IPiece piece : pieceList) {
                addBoard(piece.getType(), piece.getRow(), piece.getColumn());
            }
        }
        for (List<Piece> pieceList : _blackPieceLocations.values()) {
            for (IPiece piece : pieceList) {
                addBoard(piece.getType().toLowerCase(), piece.getRow(), piece.getColumn());
            }
        }
    }

    private void addBoard(String piece, int row, int column) {
        _representation[row - 1][(column * 2) - 1] = piece;
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
            return;
        }

        // check if final destination is free or different side
        // calculate column
        int startRow = Character.getNumericValue(origin.charAt(1));
        int endRow = Character.getNumericValue(end.charAt(1));
        int startColumn = convertAlphaToInt(origin.charAt(0));
        int endColumn = convertAlphaToInt(end.charAt(0));

        Side endpoint = _locations[endRow][endColumn];
        //check to make sure your side isn't on the desired location
        if (endpoint == turn) {
            return;
        }

        // see what piece is being moved
        PieceType type = convertAlphaToType(_representation[startRow][startColumn]);

        //check if move is allowed

        //move the piece
        Map<PieceType, List<Piece>> pieceSet = _whitePieceLocations;
        if (turn.equals(Side.BLACK)) {
            pieceSet = _blackPieceLocations;
        }
        for (IPiece piece : pieceSet.get(type)) {
            //update piece data
            //update representation
            //update location

            if (piece.getRow() == startRow && piece.getColumn() == startColumn) {
                //piece
                piece.setRow(endRow);
                piece.setColumn(endColumn);

                //representation
                _representation[endRow - 1][(endColumn * 2) - 1] = _representation[startRow - 1][(startColumn * 2) - 1];
                _representation[startRow - 1][(startColumn * 2) - 1] = " ";

                //location
                _locations[endRow][endColumn] = _locations[startRow][startColumn];
                _locations[startRow][startColumn] = null;

                //change side's turn
//                turn = Side.BLACK;
                turn = (turn.equals(Side.WHITE) ? Side.BLACK:Side.WHITE);

            }
        }


        System.out.println("HERE");


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
        switch (character) {
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

    private List<Piece> generateList(PieceType pieceType, Side side) {

        int row = 0;
        List<Piece> pieces = new ArrayList<Piece>();
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
                pieces.add(new King(side, row, 5));
                break;
            case QUEEN:
                pieces.add(new Queen(side, row, 4));
                break;
            case BISHOP:
                for (int i = 1; i <= 2; i++) {
                    pieces.add(new Bishop(side, row, i * 3));
                }
                break;
            case KNIGHT:
                for (int i = 0; i < 2; i++) {
                    pieces.add(new Knight(side, row, 2 + (i * 5)));
                }
                break;
            case ROOK:
                for (int i = 0; i < 2; i++) {
                    pieces.add(new Rook(side, row, 1 + (i * 7)));
                }
                break;
            case PAWN:
                for (int i = 1; i <= 8; i++) {
                    pieces.add(new Pawn(side, row, i));
                }
                break;
        }
        return pieces;
    }
}
