package Piece;

import Enums.Side;

public class Bishop extends Piece{
    private String _type = "B";

    public Bishop(Side side, int row, int column) {
        super(side, row, column);
    }

    @Override
    public String getType(){
        return _type;
    }
}
