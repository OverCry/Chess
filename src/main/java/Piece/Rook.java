package Piece;

import Enums.PieceType;
import Enums.Side;
import Interfaces.ICoordinate;

public class Rook extends Piece  {
    private PieceType _type = PieceType.ROOK;

    public Rook(Side side, int row, int column) {
        super(side, row, column);
    }

    @Override
    public PieceType getType(){
        return _type;
    }
}
