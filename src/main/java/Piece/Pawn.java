package Piece;

import Enums.Side;

public class Pawn extends Piece  {
    private String _type = "P";

    public Pawn(Side side, int row, int column) {
        super(side, row, column);
    }

    @Override
    public String getType(){
        return _type;
    }

//    @Override
//    public void setRow(int row){
//        _row = row;
//    }
//
//    @Override
//    public void setColumn(){
//
//    }
}
