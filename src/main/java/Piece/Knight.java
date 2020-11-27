package Piece;

import Enums.Side;

public class Knight extends Piece  {
    private String _type = "N";

    public Knight(Side side, int row, int column) {
        super(side, row, column);
    }

    @Override
    public String getType(){
        return _type;
    }
}
