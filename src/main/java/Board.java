import Interfaces.IBoard;
import Piece.Piece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board implements IBoard {

    //TODO consider which would look niver
//    private List<List<Piece>> _pieceLocations = new ArrayList<List<Piece>>();
private Map<String,List<Piece>> _pieceLocations = new HashMap<String,List<Piece>>();


    public Board (){
        //populate the board
    }
}
