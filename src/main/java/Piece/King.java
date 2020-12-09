package Piece;

import Enums.PieceType;
import Enums.Side;
import Interfaces.ICoordinate;

public class King extends Piece{
    private PieceType _type = PieceType.KING;

    public King(Side side, int row, int column) {
        super(side, row, column);
    }

    @Override
    public PieceType getType(){
        return _type;
    }
}
