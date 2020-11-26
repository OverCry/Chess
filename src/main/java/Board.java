import Enums.Side;
import Interfaces.IBoard;
import Piece.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board implements IBoard {

    //TODO maybe to enum?
    private final int KING = 0;
    private final int PAWN = 1;
    private final int ROOK = 2;
    private final int KNIGHT = 3;
    private final int BISHOP = 4;
    private final int QUEEN = 5;

    //TODO consider which would look nicer
    private List<List<Piece>> _whitePieceLocations = new ArrayList<List<Piece>>();
    private List<List<Piece>> _blackPieceLocations = new ArrayList<List<Piece>>();
//private Map<String,List<Piece>> _pieceLocations = new HashMap<String,List<Piece>>();


    public Board (){
        //populate mapping lists
        populate();
//        _pieceLocations.put("KING", new List<Piece>());
    }

    private void populate(){
        List<Piece> allPiece = new ArrayList<Piece>();
        allPiece.addAll(generateList(KING));
        allPiece.addAll(generateList(PAWN));
        allPiece.addAll(generateList(ROOK));
        allPiece.addAll(generateList(KNIGHT));
        allPiece.addAll(generateList(BISHOP));
        allPiece.addAll(generateList(QUEEN));
        System.out.println("Populated");
    }

    private List<Piece> generateList(int pieceType){
        List<Piece> pieces = new ArrayList<Piece>();
        switch (pieceType)
        {
            case KING:
                pieces.add(new King(Side.WHITE,1,5));
                pieces.add(new King(Side.BLACK,8,5));
                break;
            case QUEEN:
                pieces.add(new Queen(Side.WHITE,1,4));
                pieces.add(new Queen(Side.BLACK,8,4));
                break;
            case BISHOP:
                for (int i=1;i<=2;i++) {
                    pieces.add(new Bishop(Side.WHITE, 1, i*3));
                    pieces.add(new Bishop(Side.BLACK, 8, i*3));
                }
                break;
            case KNIGHT:
                for (int i=0;i<2;i++) {
                    pieces.add(new Knight(Side.WHITE, 1, 2+(i*5)));
                    pieces.add(new Knight(Side.BLACK, 8, 2+(i*5)));
                }
                break;
            case ROOK:
                for (int i=0;i<2;i++) {
                    pieces.add(new Rook(Side.WHITE, 1, 1+(i*7)));
                    pieces.add(new Rook(Side.BLACK, 8, 1+(i*7)));
                }
                break;
            case PAWN:
                for (int i=1;i<=8;i++) {
                    pieces.add(new Pawn(Side.WHITE, 2, i));
                    pieces.add(new Pawn(Side.BLACK, 7, i));
                }
                break;


        }
        return pieces;
    }
}
