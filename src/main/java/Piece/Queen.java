package Piece;

import Enums.Side;

public class Queen extends Piece  {
    private String _type = "Q";

    public Queen(Side side, int row, int column) {
        super(side, row, column);
    }

    @Override
    public String getType(){
        return _type;
    }
}
