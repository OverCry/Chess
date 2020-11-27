package Piece;

import Enums.Side;

public class King extends Piece{
    private String _type = "K";

    public King(Side side, int row, int column) {
        super(side, row, column);
    }

    @Override
    public String getType(){
        return _type;
    }
}
