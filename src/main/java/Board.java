import Enums.PieceType;
import Enums.Side;
import Interfaces.IBoard;
import Interfaces.Piece.IPiece;
import Piece.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board implements IBoard {

    private Side[][] locations = new Side[8][8];
//    private Map<Integer, Map<Integer, Piece>> _pieceLocations = new HashMap<>();
    private Map<PieceType, List<Piece>> _whitePieceLocations = new HashMap<>();
    private Map<PieceType, List<Piece>> _blackPieceLocations = new HashMap<>();


    public Board() {

//        _pieceLocations.put(1,new HashMap<Integer, Piece>(Map.of()));
        for (int i=0;i<8;i++){
            locations[7][i] = Side.BLACK;
            locations[6][i] = Side.BLACK;
            locations[1][i] = Side.WHITE;
            locations[0][i] = Side.WHITE;
        }

        //populate mapping lists
        populate();
    }

    public void printBoard(){

    }

    private void populate() {
        List<Piece> allPiece = new ArrayList<Piece>();
        for (PieceType piece: PieceType.values()) {
            _whitePieceLocations.put(piece, generateList(piece, Side.WHITE));
            _blackPieceLocations.put(piece, generateList(piece, Side.BLACK));
        }
        System.out.println("Populated");
    }

    private List<Piece> generateList(PieceType pieceType, Side side) {

        int row=1;
        List<Piece> pieces = new ArrayList<Piece>();
        if (pieceType!=PieceType.PAWN){
            if (side.equals(Side.BLACK)){
                row=8;
            }
        } else {
            if (side.equals(Side.BLACK)){
                row=7;
            } else {
                row=2;
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
                    pieces.add(new Bishop(side, row, i*3));
                }
                break;
            case KNIGHT:
                for (int i = 0; i < 2; i++) {
                    pieces.add(new Knight(side, row,  2 + (i * 5)));
                }
                break;
            case ROOK:
                for (int i = 0; i < 2; i++) {
                    pieces.add(new Rook(side, row,  1 + (i * 7)));
                }
                break;
            case PAWN:
                for (int i = 1; i <= 8; i++) {
                    pieces.add(new Pawn(side, row,  i));
                }
                break;
        }
        return pieces;
    }
}
