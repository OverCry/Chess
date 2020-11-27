package Piece;

import Enums.Side;

public class Rook extends Piece  {
    private String _type = "R";

    public Rook(Side side, int row, int column) {
        super(side, row, column);
    }

    @Override
    public String getType(){
        return _type;
    }
}